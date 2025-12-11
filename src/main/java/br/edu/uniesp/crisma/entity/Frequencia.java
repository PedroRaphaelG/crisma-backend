package br.edu.uniesp.crisma.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "frequencia", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"crismando_id", "evento_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Crismando é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crismando_id", nullable = false)
    private Crismando crismando;

    @NotNull(message = "Evento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro = LocalDate.now();

    @NotNull(message = "Presença é obrigatória")
    @Column(nullable = false)
    private Boolean presente;

    @Column(columnDefinition = "TEXT")
    private String justificativa;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
