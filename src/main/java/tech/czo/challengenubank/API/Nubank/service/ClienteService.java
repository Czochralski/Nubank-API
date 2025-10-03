package tech.czo.challengenubank.API.Nubank.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ClienteMapper;
import tech.czo.challengenubank.API.Nubank.dto.ClienteRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.exceptions.ClienteNotFoundException;
import tech.czo.challengenubank.API.Nubank.exceptions.ContatoEmptyException;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;
import tech.czo.challengenubank.API.Nubank.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO dto){
        Cliente cliente = clienteMapper.toEntity(dto);
        cliente.setNome(dto.nome());
        if(cliente.getContatos() != null &&  cliente.getContatos().size() > 0){
            List<Contato> contatos = cliente.getContatos().stream().map(c -> {
                Contato contato = new Contato();
                contato.setEmail(c.getEmail());
                contato.setTelefone(c.getTelefone());
                contato.setCliente(cliente);
                if(c.getEmail().isBlank() || c.getTelefone().isBlank()){
                     throw new ContatoEmptyException();
                }
                return contato;
            }).collect(Collectors.toList());
            cliente.setContatos(contatos);
        }
         clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    public List<ClienteResponseDTO> listarTodos(){
       return clienteRepository.findAll().stream().map(clienteMapper::toDto).collect(Collectors.toList());
    }

    public List<ClienteResponseDTO> ListarContatoId(UUID id){
        Optional<Cliente> lista = clienteRepository.findById(id);
        if(lista.isEmpty()){
            throw new ClienteNotFoundException();
        }
        List<ClienteResponseDTO> listaDto = lista.stream().map(clienteMapper::toDto
        ).collect(Collectors.toList());
        return listaDto;
    }

/*    public ClienteResponseDTO buscarPorId(UUID id){
        Cliente cliente =clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
        return clienteMapper.toDto(cliente);
    }*/

    public void deletarCliente(UUID id){
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isEmpty()){
            throw new ClienteNotFoundException();
        }
        clienteRepository.deleteById(id);

    }
}
