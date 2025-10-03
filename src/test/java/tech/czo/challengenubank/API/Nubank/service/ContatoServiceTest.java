package tech.czo.challengenubank.API.Nubank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ClienteRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ContatoRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ContatoResponseDTO;
import tech.czo.challengenubank.API.Nubank.exceptions.ClienteNotFoundException;
import tech.czo.challengenubank.API.Nubank.exceptions.ContatoEmptyException;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;
import tech.czo.challengenubank.API.Nubank.repository.ClienteRepository;
import tech.czo.challengenubank.API.Nubank.repository.ContatoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ContatoRepository contatoRepository;

    @InjectMocks
    ContatoService contatoService;

    @Mock
    ContatoMapper contatoMapper;

    @Mock
    Cliente cliente;
    @Mock
    Contato contato;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("João");

        contato = new Contato();
        contato.setId(UUID.randomUUID());
        contato.setEmail("Joao@gmail.com");
        contato.setTelefone("9999-0000");
        contato.setCliente(cliente);
    }

    @Nested
    class saveContact{
        @Test
        @DisplayName("Should give error when try find an client with ID incorrect")
        void errorClientNotFound() {
            cliente.setContatos(java.util.List.of());

            when(clienteRepository.findById(any())).thenReturn(Optional.empty());

            ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(
                     contato.getEmail(), contato.getTelefone(), contato.getCliente().getId()
            );

            var erro = catchThrowable(()-> contatoService.salvarContato(contatoRequestDTO));

            assertThat(erro).isInstanceOf(ClienteNotFoundException.class);

            verify(contatoRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should save an contact with success")
        void saveContactSuccess(){
            cliente.setContatos(List.of());

            when(clienteRepository.findById(any())).thenReturn(Optional.ofNullable(cliente));
            when(contatoMapper.toEntity(any())).thenReturn(contato);

            ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(
                    contato.getEmail(), contato.getTelefone(), contato.getCliente().getId()
            );

            when(contatoRepository.save(any())).thenReturn(contato);

            var clientSave = contatoService.salvarContato(contatoRequestDTO);
            assertNotNull(clientSave);
            verify(contatoRepository).save(contato);

        }
    }

}

