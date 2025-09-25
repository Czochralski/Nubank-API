package tech.czo.challengenubank.API.Nubank.dto;

import jakarta.validation.constraints.NotBlank;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        @NotBlank(message = "Campo Obrigatório")
        String nome,
        List<Contato> contatos
) {
}
