package tech.czo.challengenubank.API.Nubank.controller.mapper;

import org.mapstruct.Mapper;
import tech.czo.challengenubank.API.Nubank.dto.ContatoRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ContatoResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Contato;

@Mapper(componentModel = "spring")
public interface ContatoMapper {

    Contato toEntity(ContatoRequestDTO dto);
    ContatoResponseDTO toDto(Contato contato);
}
