package br.edu.uniesp.crisma.entity;

import br.edu.uniesp.crisma.enums.TipoPadrinho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "crismando_padrinho", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"crismando_id", "tipo_padrinho"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrismandoPadrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Crismando é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crismando_id", nullable = false)
    private Crismando crismando;

    @NotNull(message = "Padrinho é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padrinho_id", nullable = false)
    private Padrinho padrinho;

    @NotNull(message = "Tipo de padrinho é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_padrinho", nullable = false, length = 20)
    private TipoPadrinho tipoPadrinho;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
