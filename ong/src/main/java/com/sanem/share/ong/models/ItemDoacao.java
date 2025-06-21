package com.sanem.share.ong.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TB_ItemDoacao")
@EqualsAndHashCode(of = "id")
@Data
public class ItemDoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Doacao_ID", nullable = false)
    private Doacao doacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Item_ID", nullable = false)
    private Item item;

    @Column(name = "DT_Doacao", nullable = false)
    private LocalDate dataDoacao;

    @Column(name = "Quantidade", nullable = false)
    private Integer quantidade;
}