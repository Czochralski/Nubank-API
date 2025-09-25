package tech.czo.challengenubank.API.Nubank.controller.mapper;

import org.mapstruct.Mapper;
import tech.czo.challengenubank.API.Nubank.dto.ClienteRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteRequestDTO dto);
    ClienteResponseDTO toDto(Cliente cliente);
}
