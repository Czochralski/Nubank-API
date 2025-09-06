package tech.czo.challengenubank.API.Nubank.controller.mapper;

import org.mapstruct.Mapper;
import tech.czo.challengenubank.API.Nubank.dto.ContatoDTO;
import tech.czo.challengenubank.API.Nubank.model.Contato;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    Contato toEntity(ContatoDTO dto);
}
