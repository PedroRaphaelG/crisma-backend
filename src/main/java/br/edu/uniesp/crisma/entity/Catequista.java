package br.edu.uniesp.crisma.entity;

import br.edu.uniesp.crisma.enums.StatusCatequista;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "catequista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catequista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Pessoa é obrigatória")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    @Column(name = "setor_trabalho", length = 100)
    private String setorTrabalho;

    @NotNull(message = "Ano de crisma é obrigatório")
    @Column(name = "ano_crisma", nullable = false)
    private Integer anoCrisma;

    @Column(name = "experiencia_anos")
    private Integer experienciaAnos = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCatequista status = StatusCatequista.ATIVO;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
