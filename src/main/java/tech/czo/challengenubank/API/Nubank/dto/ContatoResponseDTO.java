package tech.czo.challengenubank.API.Nubank.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ContatoResponseDTO(
        UUID id,
        @NotBlank(message = "Campo Obrigatório")
        String email,
        @NotBlank(message = "Campo Obrigatório")
        String telefone,
        @NotBlank(message = "Campo Obrigatório")
        UUID idCliente
) {
}
