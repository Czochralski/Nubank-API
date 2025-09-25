package tech.czo.challengenubank.API.Nubank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.util.List;


@Schema(name = "Cliente")
public record ClienteRequestDTO(
        @NotBlank(message = "Campo Obrigatório")
        String nome,
        List<Contato> contatos
){

}

