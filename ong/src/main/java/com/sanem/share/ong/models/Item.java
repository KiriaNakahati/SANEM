package com.sanem.share.ong.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_Item")
@EqualsAndHashCode(of = "id")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_Funcionario_ID", nullable = false)
    private User funcionario;

    @Column(name = "Categoria", nullable = false, length = 45)
    @NotBlank
    private String categoria;

    @Column(name = "Tamanho", nullable = false, length = 45)
    @NotBlank
    private String tamanho;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemDoacao> itensDoacao;
}