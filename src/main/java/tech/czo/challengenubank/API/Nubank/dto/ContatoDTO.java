package tech.czo.challengenubank.API.Nubank.dto;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.util.UUID;

public record ContatoDTO (
        @NotBlank(message = "Campo Obrigatório")
        String email,
        @NotBlank(message = "Campo Obrigatório")
        String telefone,
        @NotBlank(message = "Campo Obrigatório")
        UUID idCliente

){
}
