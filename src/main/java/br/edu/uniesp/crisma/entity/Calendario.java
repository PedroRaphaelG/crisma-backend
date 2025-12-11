package br.edu.uniesp.crisma.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calendario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"grupo_id", "semana"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Grupo é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @NotNull(message = "Semana é obrigatória")
    @Column(nullable = false)
    private Integer semana;

    @NotBlank(message = "Tema é obrigatório")
    @Column(nullable = false, length = 200)
    private String tema;

    @NotNull(message = "Data do encontro é obrigatória")
    @Column(name = "data_encontro", nullable = false)
    private LocalDate dataEncontro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formacao_id")
    private Formacao formacao;

    @Column(name = "conteudo_programatico", columnDefinition = "TEXT")
    private String conteudoProgramatico;

    @Column(name = "material_necessario", columnDefinition = "TEXT")
    private String materialNecessario;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
