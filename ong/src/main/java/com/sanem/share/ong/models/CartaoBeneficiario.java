package com.sanem.share.ong.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TB_CartaoBeneficiario")
@EqualsAndHashCode(of = "id")
@Data
public class CartaoBeneficiario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Beneficiario_ID", nullable = false)
    private Beneficiario beneficiario;

    @Column(name = "DT_Emissao", nullable = false)
    private LocalDate dataEmissao;
}