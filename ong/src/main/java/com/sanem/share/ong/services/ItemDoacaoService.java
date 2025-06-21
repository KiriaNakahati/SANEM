package com.sanem.share.ong.services;


import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoRequestDTO;
import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoResponseDTO;
import com.sanem.share.ong.dtos.item_doacao.ItemDoacaoUpdateDTO;
import com.sanem.share.ong.models.Doacao;
import com.sanem.share.ong.models.Item;
import com.sanem.share.ong.models.ItemDoacao;
import com.sanem.share.ong.repositories.DoacaoRepository;
import com.sanem.share.ong.repositories.ItemDoacaoRepository;
import com.sanem.share.ong.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemDoacaoService {

    private final ItemDoacaoRepository repo;
    private final DoacaoRepository doRepo;
    private final ItemRepository itemRepo;

    public ItemDoacaoService(ItemDoacaoRepository repo,
                             DoacaoRepository doRepo,
                             ItemRepository itemRepo) {
        this.repo = repo;
        this.doRepo = doRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public ItemDoacaoResponseDTO create(ItemDoacaoRequestDTO dto) throws Exception {
        Doacao d = doRepo.findById(dto.doacaoId())
                .orElseThrow(() -> new Exception("Doação não encontrada"));
        Item it = itemRepo.findById(dto.itemId())
                .orElseThrow(() -> new Exception("Item não encontrado"));
        ItemDoacao id = new ItemDoacao();
        id.setDoacao(d);
        id.setItem(it);
        id.setDataDoacao(dto.dataDoacao());
        id.setQuantidade(dto.quantidade());
        return new ItemDoacaoResponseDTO(repo.save(id));
    }

    public Page<ItemDoacaoResponseDTO> findAll(Pageable pg) {
        return repo.findAll(pg).map(ItemDoacaoResponseDTO::new);
    }

    public List<ItemDoacaoResponseDTO> findAll() {
        return repo.findAll().stream().map(ItemDoacaoResponseDTO::new).collect(Collectors.toList());
    }

    public ItemDoacaoResponseDTO findById(UUID id) throws Exception {
        return repo.findById(id)
                .map(ItemDoacaoResponseDTO::new)
                .orElseThrow(() -> new Exception("ItemDoação não encontrado"));
    }

    public List<ItemDoacaoResponseDTO> findByDoacaoId(UUID doId) {
        return repo.findByDoacaoId(doId)
                .stream()
                .map(ItemDoacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDoacaoResponseDTO update(ItemDoacaoUpdateDTO dto) throws Exception {
        ItemDoacao i = repo.findById(dto.id())
                .orElseThrow(() -> new Exception("ItemDoação não encontrado"));
        Doacao d = doRepo.findById(dto.doacaoId())
                .orElseThrow(() -> new Exception("Doação não encontrada"));
        Item it = itemRepo.findById(dto.itemId())
                .orElseThrow(() -> new Exception("Item não encontrado"));
        i.setDoacao(d);
        i.setItem(it);
        i.setDataDoacao(dto.dataDoacao());
        i.setQuantidade(dto.quantidade());
        return new ItemDoacaoResponseDTO(repo.save(i));
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!repo.existsById(id)) throw new Exception("ItemDoação não encontrado");
        repo.deleteById(id);
    }
}
