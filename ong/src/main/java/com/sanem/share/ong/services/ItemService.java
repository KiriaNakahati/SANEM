package com.sanem.share.ong.services;


import com.sanem.share.ong.dtos.item.ItemRequestDTO;
import com.sanem.share.ong.dtos.item.ItemResponseDTO;
import com.sanem.share.ong.dtos.item.ItemUpdateDTO;
import com.sanem.share.ong.models.Item;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.repositories.ItemRepository;
import com.sanem.share.ong.repositories.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository repo;
    private final UserRepository userRepo;

    public ItemService(ItemRepository repo,UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ItemResponseDTO create(ItemRequestDTO dto, User funcionario) throws Exception {
        Item item = new Item();
        item.setFuncionario(funcionario);
        item.setCategoria(dto.categoria());
        item.setTamanho(dto.tamanho());
        return new ItemResponseDTO(repo.save(item));
    }

    public Page<ItemResponseDTO> findAll(Pageable pg) {
        return repo.findAll(pg).map(ItemResponseDTO::new);
    }

    public List<ItemResponseDTO> findAll() {
        return repo.findAll().stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public ItemResponseDTO findById(UUID id) throws Exception {
        return repo.findById(id)
                .map(ItemResponseDTO::new)
                .orElseThrow(() -> new Exception("Item não encontrado"));
    }

    public List<ItemResponseDTO> findByFuncionarioId(UUID funcId) {
        return repo.findByFuncionarioId(funcId)
                .stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponseDTO update(ItemUpdateDTO dto) throws Exception {
        Item item = repo.findById(dto.id())
                .orElseThrow(() -> new Exception("Item não encontrado"));
        User user = userRepo.findById(dto.funcionarioId())
                .orElseThrow(() -> new Exception("Funcionário não encontrado"));
        item.setFuncionario(user);
        item.setCategoria(dto.categoria());
        item.setTamanho(dto.tamanho());
        return new ItemResponseDTO(repo.save(item));
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!repo.existsById(id)) throw new Exception("Item não encontrado");
        repo.deleteById(id);
    }
}
