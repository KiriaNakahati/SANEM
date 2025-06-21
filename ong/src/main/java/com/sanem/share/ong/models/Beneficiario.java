package com.sanem.share.ong.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;
//Benef
@Entity
@Table(name = "TB_Beneficiario")
@EqualsAndHashCode(of = "id")
@Data
public class Beneficiario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 45)
    @NotBlank
    private String telefone;

    @Column(nullable = false, length = 45)
    @NotBlank
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Funcionario_ID", nullable = false)
    private User funcionario;

    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL)
    private List<CartaoBeneficiario> cartoes;
}