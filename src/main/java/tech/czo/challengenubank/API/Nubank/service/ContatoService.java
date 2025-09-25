package tech.czo.challengenubank.API.Nubank.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ContatoRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ContatoResponseDTO;
import tech.czo.challengenubank.API.Nubank.exceptions.ClienteNotFoundException;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;
import tech.czo.challengenubank.API.Nubank.repository.ClienteRepository;
import tech.czo.challengenubank.API.Nubank.repository.ContatoRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final ClienteRepository clienteRepository;
    private final ContatoMapper contatoMapper;

    public ContatoResponseDTO salvarContato(ContatoRequestDTO dto){
        Cliente cliente = clienteRepository.findById(dto.idCliente()).orElseThrow(EntityNotFoundException::new);
        if(cliente.getNome().isEmpty()){
            throw new ClienteNotFoundException();
        }
        Contato contato = contatoMapper.toEntity(dto);
        contato.setEmail(dto.email());
        contato.setTelefone(dto.telefone());
        contato.setCliente(cliente);
        return toResponse(contatoRepository.save(contato));
    }

    public ContatoResponseDTO toResponse(Contato contato){
        return new ContatoResponseDTO(
                contato.getId(),
                contato.getEmail(),
                contato.getTelefone(),
                contato.getCliente().getId()
        );
    }
}
