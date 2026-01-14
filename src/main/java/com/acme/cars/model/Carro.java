package com.acme.cars.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carro")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    @Min(value = 1886, message = "Ano inválido")
    @Max(value = 2100, message = "Ano inválido")
    private int ano;

    @NotBlank(message = "Cor é obrigatória")
    private String cor;

    @Positive(message = "Cavalos de potência deve ser maior que 0")
    private int cavalosDePotencia;

    @NotBlank(message = "Fabricante é obrigatório")
    private String fabricante;

    @NotBlank(message = "País é obrigatório")
    private String pais;

}
