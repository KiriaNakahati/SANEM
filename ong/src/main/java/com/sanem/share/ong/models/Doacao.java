package com.sanem.share.ong.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_Doacao")
@EqualsAndHashCode(of = "id")
@Data
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "DT_Doacao", nullable = false)
    private LocalDate dataDoacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Funcionario_ID", nullable = false)
    private User funcionario;

    @Column(name = "FT_SituacaoGeral", nullable = false, length = 10)
    private Integer situacaoGeral;

    @OneToMany(mappedBy = "doacao", cascade = CascadeType.ALL)
    private List<ItemDoacao> itensDoacao;
}
