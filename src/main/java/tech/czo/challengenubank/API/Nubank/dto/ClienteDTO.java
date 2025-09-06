package tech.czo.challengenubank.API.Nubank.dto;

import jakarta.validation.constraints.NotBlank;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.util.List;


public record ClienteDTO(
        @NotBlank(message = "Campo Obrigatório")
        String nome,
        List<Contato> contatos
) {

}
