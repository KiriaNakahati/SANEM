package com.sanem.share.ong.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TB_Relatorio")
@EqualsAndHashCode(of = "id")
@Data
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Funcionario_ID", nullable = false)
    private User funcionario;

    @Column(name = "DT_Emissao", nullable = false)
    private LocalDate dataEmissao;
}