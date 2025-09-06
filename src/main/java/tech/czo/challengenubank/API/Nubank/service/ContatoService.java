package tech.czo.challengenubank.API.Nubank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ContatoDTO;
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

    public Contato salvarContato(ContatoDTO dto){
        Optional<Cliente> clienteOptional = clienteRepository.findById(dto.idCliente());
        if(clienteOptional.isEmpty()){
            throw new ClienteNotFoundException();
        }
        Contato contato = contatoMapper.toEntity(dto);
        contato.setEmail(dto.email());
        contato.setTelefone(dto.telefone());
        contato.setCliente(clienteOptional.get());
        return contatoRepository.save(contato);
    }
}
