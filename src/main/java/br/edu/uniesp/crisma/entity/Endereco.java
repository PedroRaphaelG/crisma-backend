package br.edu.uniesp.crisma.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    @Column(nullable = false, length = 8)
    private String cep;

    @NotBlank(message = "Estado é obrigatório")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado deve ser sigla de 2 letras")
    @Column(nullable = false, length = 2)
    private String estado;

    @NotBlank(message = "Município é obrigatório")
    @Column(nullable = false, length = 100)
    private String municipio;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false, length = 200)
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false, length = 10)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
