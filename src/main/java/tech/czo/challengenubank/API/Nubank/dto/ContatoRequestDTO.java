package tech.czo.challengenubank.API.Nubank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import tech.czo.challengenubank.API.Nubank.model.Cliente;

import java.util.UUID;

@Schema(name = "Contatos")
public record ContatoRequestDTO (
        @NotBlank(message = "Campo Obrigatório")
        String email,
        @NotBlank(message = "Campo Obrigatório")
        String telefone,
        @NotBlank(message = "Campo Obrigatório")
        UUID idCliente

){
}
