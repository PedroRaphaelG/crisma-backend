package br.edu.uniesp.crisma.entity;

import br.edu.uniesp.crisma.enums.StatusCrismando;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crismando")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crismando {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Pessoa é obrigatória")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    @NotNull(message = "Grupo é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao = LocalDate.now();

    @NotBlank(message = "Primeiro responsável é obrigatório")
    @Column(name = "primeiro_responsavel", nullable = false, length = 100)
    private String primeiroResponsavel;

    @Column(name = "segundo_responsavel", length = 100)
    private String segundoResponsavel;

    @NotBlank(message = "Telefone do responsável é obrigatório")
    @Column(name = "telefone_responsavel", nullable = false, length = 15)
    private String telefoneResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCrismando status = StatusCrismando.ATIVO;

    @Column(name = "data_crisma")
    private LocalDate dataCrisma;

    @OneToMany(mappedBy = "crismando", cascade = CascadeType.ALL)
    private List<Frequencia> frequencias = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Calcula o percentual de presença do crismando.
     */
    @Transient
    public double getPercentualPresenca() {
        if (frequencias.isEmpty()) {
            return 0.0;
        }
        long presencas = frequencias.stream().filter(Frequencia::getPresente).count();
        return (presencas * 100.0) / frequencias.size();
    }

    /**
     * Verifica se o crismando está apto a receber o sacramento (máximo 25% de faltas).
     */
    @Transient
    public boolean isAptoParaCrisma() {
        return getPercentualPresenca() >= 75.0;
    }
}
