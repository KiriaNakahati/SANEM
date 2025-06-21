package com.sanem.share.ong.services;

import com.sanem.share.ong.dtos.doacao.DoacaoItemDTO;
import com.sanem.share.ong.dtos.doacao.DoacaoRequestDTO;
import com.sanem.share.ong.dtos.doacao.DoacaoResponseDTO;
import com.sanem.share.ong.dtos.doacao.DoacaoUpdateDTO;
import com.sanem.share.ong.models.Doacao;
import com.sanem.share.ong.models.Item;
import com.sanem.share.ong.models.ItemDoacao;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.repositories.DoacaoRepository;
import com.sanem.share.ong.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoacaoService {

    private final DoacaoRepository doacaoRepo;
    private final ItemRepository itemRepo;

    public DoacaoService(DoacaoRepository doacaoRepo,
                         ItemRepository itemRepo) {
        this.doacaoRepo = doacaoRepo;
        this.itemRepo   = itemRepo;
    }

    @Transactional
    public DoacaoResponseDTO create(DoacaoRequestDTO dto) throws Exception {
        User func = (User)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        Doacao doacao = new Doacao();
        doacao.setDataDoacao(dto.dataDoacao());
        doacao.setSituacaoGeral(dto.situacaoGeral());
        doacao.setFuncionario(func);

        List<ItemDoacao> detalhes = new ArrayList<>();
        for (DoacaoItemDTO itemDto : dto.itens()) {
            Item item = itemRepo.findById(itemDto.itemId())
                    .orElseThrow(() -> new Exception("Item não encontrado: " + itemDto.itemId()));
            ItemDoacao det = new ItemDoacao();
            det.setDoacao(doacao);
            det.setItem(item);
            det.setDataDoacao(dto.dataDoacao());
            det.setQuantidade(itemDto.quantidade());
            detalhes.add(det);
        }
        doacao.setItensDoacao(detalhes);

        // 4) Salva tudo de uma vez (cascade ALL cuidará de ItemDoacao)
        Doacao saved = doacaoRepo.save(doacao);
        return new DoacaoResponseDTO(saved);
    }

    public Page<DoacaoResponseDTO> findAll(Pageable pg) {
        return doacaoRepo.findAll(pg).map(DoacaoResponseDTO::new);
    }

    public List<DoacaoResponseDTO> findAll() {
        return doacaoRepo.findAll()
                .stream()
                .map(DoacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public DoacaoResponseDTO findById(UUID id) throws Exception {
        return doacaoRepo.findById(id)
                .map(DoacaoResponseDTO::new)
                .orElseThrow(() -> new Exception("Doação não encontrada"));
    }

    @Transactional
    public DoacaoResponseDTO update(DoacaoUpdateDTO dto) throws Exception {
        Doacao d = doacaoRepo.findById(dto.id())
                .orElseThrow(() -> new Exception("Doação não encontrada"));
        d.setDataDoacao(dto.dataDoacao());
        d.setSituacaoGeral(dto.situacaoGeral());
        return new DoacaoResponseDTO(doacaoRepo.save(d));
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        if (!doacaoRepo.existsById(id)) throw new Exception("Doação não encontrada");
        doacaoRepo.deleteById(id);
    }
}
