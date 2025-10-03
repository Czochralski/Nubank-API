package tech.czo.challengenubank.API.Nubank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteMapper clienteMapper;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    Cliente cliente;
    @Mock
    Cliente cliente2;
    @Mock
    Contato contato;
    @Mock
    Contato contato2;
    @Mock
    Contato contato3;

    @BeforeEach
    void setUp(){
        cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("João");

        contato = new Contato();
        contato.setId(UUID.randomUUID());
        contato.setEmail("Joao@gmail.com");
        contato.setTelefone("9999-0000");
        contato.setCliente(cliente);

        contato2 = new Contato();
        contato2.setId(UUID.randomUUID());
        contato2.setEmail("Joao2@gmail.com");
        contato2.setTelefone("8888-1111");
        contato2.setCliente(cliente);

        contato3 = new Contato();
        contato3.setId(UUID.randomUUID());
        contato3.setEmail("Joao3@gmail.com");
        contato3.setTelefone("");
        contato3.setCliente(cliente);

    }

    @Nested
    class saveClient {

        @Test
        @DisplayName("Should create a client with contacts success")
        void saveClientWithContacts() throws Exception {

            cliente.setContatos(java.util.List.of(contato, contato2));

            when(clienteMapper.toEntity(any())).thenReturn(cliente);

            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(cliente.getNome(), cliente.getContatos());

            ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getContatos());

            when(clienteMapper.toDto(any())).thenReturn(clienteResponseDTO);

            when(clienteRepository.save(any())).thenReturn(cliente);

            var clientSave = clienteService.salvarCliente(clienteRequestDTO);
            assertNotNull(clientSave);
            verify(clienteRepository).save(any());

        }

        @Test
        @DisplayName("Should create a client without success")
        void saveClientWithoutContacts() throws Exception {

            cliente.setContatos(java.util.List.of());

            when(clienteMapper.toEntity(any())).thenReturn(cliente);

            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(cliente.getNome(), cliente.getContatos());

            ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getContatos());

            when(clienteMapper.toDto(any())).thenReturn(clienteResponseDTO);

            when(clienteRepository.save(any())).thenReturn(cliente);

            var clientSave = clienteService.salvarCliente(clienteRequestDTO);
            assertNotNull(clientSave);
            verify(clienteRepository).save(any());

        }

        @Test
        @DisplayName("Should give error when saving an client with incomplete contact")
        void errorSaveClientWithIncompleteContact(){

            cliente.setContatos(java.util.List.of(contato3));

            when(clienteMapper.toEntity(any())).thenReturn(cliente);

            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(cliente.getNome(), cliente.getContatos());

            var erro = catchThrowable(()-> clienteService.salvarCliente(clienteRequestDTO));

            assertThat(erro).isInstanceOf(ContatoEmptyException.class);

            verify(clienteRepository, never()).save(any());
        }
    }
    @Nested
    class deleteClient {

        @Test
        @DisplayName("Should give error when try delete an client with ID incorrect")
        void errorDeleteClientWithIdIncorrect() {
            cliente.setContatos(java.util.List.of(contato));

            when(clienteRepository.findById(any())).thenReturn(Optional.empty());

            var erro = catchThrowable(() -> clienteService.deletarCliente(cliente.getId()));

            assertThat(erro).isInstanceOf(ClienteNotFoundException.class);

            verify(clienteRepository, never()).delete(any());
        }

        @Test
        @DisplayName("Should delete client by id with success")
        void deleteClienteByIdSuccess() {
            cliente.setContatos(java.util.List.of(contato));

            when(clienteRepository.findById(any())).thenReturn(Optional.ofNullable(cliente));

            clienteService.deletarCliente(cliente.getId());

            verify(clienteRepository, times(1)).deleteById(cliente.getId());

        }
    }

    @Nested
    class findClient {
        @Test
        @DisplayName("Should give error when try find an client with ID incorrect")
        void errorFindClientWithIdIncorrect() {
            cliente.setContatos(java.util.List.of(contato));

            when(clienteRepository.findById(any())).thenReturn(Optional.empty());

            var erro = catchThrowable(() -> clienteService.ListarContatoId(cliente.getId()));

            assertThat(erro).isInstanceOf(ClienteNotFoundException.class);

            verify(clienteRepository, never()).delete(any());
        }

        @Test
        @DisplayName("Should find client by id with success")
        void findClienteByIdSuccess() {
            cliente.setContatos(java.util.List.of(contato));

            when(clienteRepository.findById(any())).thenReturn(Optional.ofNullable(cliente));

            clienteService.ListarContatoId(cliente.getId());

            verify(clienteRepository, times(1)).findById(cliente.getId());

        }

        @Test
        @DisplayName("Should list all")
        void listClient(){
            cliente.setContatos(java.util.List.of(contato));

            cliente2 = new Cliente();
            cliente2.setId(UUID.randomUUID());
            cliente2.setNome("Julia");

            contato2 = new Contato();
            contato2.setId(UUID.randomUUID());
            contato2.setEmail("Julia@gmail.com");
            contato2.setTelefone("8888-1111");
            contato2.setCliente(cliente2);

            cliente2.setContatos(java.util.List.of(contato2));

            var list = List.of(cliente,cliente2);

            when(clienteRepository.findAll()).thenReturn(list);

            List<ClienteResponseDTO> clienteResponseDTOS = clienteService.listarTodos();

            assertThat(clienteResponseDTOS).hasSize(2);

            verify(clienteRepository, times(1)).findAll();
            verifyNoMoreInteractions(clienteRepository);

        }
    }
}
