package br.edu.uniesp.crisma.entity;

import br.edu.uniesp.crisma.enums.StatusGrupo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do grupo é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "Ano de formação é obrigatório")
    @Column(name = "ano_formacao", nullable = false)
    private Integer anoFormacao;

    @NotNull(message = "Faixa etária mínima é obrigatória")
    @Column(name = "faixa_etaria_minima", nullable = false)
    private Integer faixaEtariaMinima;

    @NotNull(message = "Faixa etária máxima é obrigatória")
    @Column(name = "faixa_etaria_maxima", nullable = false)
    private Integer faixaEtariaMaxima;

    @NotNull(message = "Igreja é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igreja_id", nullable = false)
    private Igreja igreja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusGrupo status = StatusGrupo.ATIVO;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Crismando> crismandos = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    public int getTotalCrismandos() {
        return (int) crismandos.stream()
                .filter(c -> c.getStatus() == br.edu.uniesp.crisma.enums.StatusCrismando.ATIVO)
                .count();
    }
}
