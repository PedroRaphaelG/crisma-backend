package br.edu.uniesp.crisma.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caixinha", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"grupo_id", "mes_referencia", "ano_referencia"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caixinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Grupo é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @NotNull(message = "Mês de referência é obrigatório")
    @Column(name = "mes_referencia", nullable = false)
    private Integer mesReferencia;

    @NotNull(message = "Ano de referência é obrigatório")
    @Column(name = "ano_referencia", nullable = false)
    private Integer anoReferencia;

    @NotNull(message = "Valor sugerido é obrigatório")
    @Column(name = "valor_sugerido", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorSugerido;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
