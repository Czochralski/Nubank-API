package tech.czo.challengenubank.API.Nubank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ClienteMapper;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import tech.czo.challengenubank.API.Nubank.repository.ClienteRepository;
import tech.czo.challengenubank.API.Nubank.service.ClienteService;


@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    Cliente cliente;
    Contato contato;
    Contato contato2;
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
        @DisplayName("Should create a client with success")
        void saveClientWithContacts() throws Exception {

            cliente.setContatos(java.util.List.of(contato, contato2));

            ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getContatos());

            when(clienteService.salvarCliente(any())).thenReturn(clienteResponseDTO);

            MvcResult result = mockMvc.perform(post("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteResponseDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cliente.getId().toString()))
                    .andExpect(jsonPath("$.contatos").isArray())
                    .andExpect(jsonPath("$.contatos").isNotEmpty())
                    .andReturn();

            System.out.println("Response JSON: " + result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Should create a client with success")
        void saveClientWithoutContacts() throws Exception {

            cliente.setContatos(java.util.List.of());

            ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getContatos());

            when(clienteService.salvarCliente(any())).thenReturn(clienteResponseDTO);

            MvcResult result = mockMvc.perform(post("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteResponseDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cliente.getId().toString()))
                    .andExpect(jsonPath("$.contatos").isArray())
                    .andExpect(jsonPath("$.contatos").isEmpty())
                    .andReturn();

            System.out.println("Response JSON: " + result.getResponse().getContentAsString());
        }

    }


    @Test
    @DisplayName("Should find a client for id")
    void listClientWithSuccess() throws Exception {

        cliente.setContatos(java.util.List.of(contato, contato2));

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getContatos());

        when(clienteService.ListarContatoId(cliente.getId())).thenReturn(Collections.singletonList(clienteResponseDTO));
        MvcResult result = mockMvc.perform(get("/clientes/{id}/contatos", clienteResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clienteResponseDTO.id().toString()))
                .andReturn();
        System.out.println("Response JSON: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Should return all clients")
    void listAllClientWithSuccess() throws Exception {
        // Arrange

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                cliente.getId(), cliente.getNome(), List.of(contato,contato2)
        );

        Cliente cliente2 = new Cliente();
        cliente2.setId(UUID.randomUUID());
        cliente2.setNome("Julia");

        Contato c3 = new Contato();
        c3.setId(UUID.randomUUID());
        c3.setEmail("Julia@gmail.com");
        c3.setTelefone("98845-0000");
        c3.setCliente(cliente2);

        Contato c4 = new Contato();
        c4.setId(UUID.randomUUID());
        c4.setEmail("Julia2@gmail.com");
        c4.setTelefone("99412-1111");
        c4.setCliente(cliente2);

        ClienteResponseDTO clienteResponseDTO2 = new ClienteResponseDTO(
                cliente2.getId(), cliente2.getNome(), List.of(c3,c4)
        );

        List<ClienteResponseDTO> listClientsResponse = List.of(clienteResponseDTO, clienteResponseDTO2);

        when(clienteService.listarTodos()).thenReturn(listClientsResponse);

        // Act + Assert
        MvcResult result = mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clienteResponseDTO.id().toString()))
                .andExpect(jsonPath("$[1].id").value(clienteResponseDTO2.id().toString()))
                .andReturn();
        System.out.println("Response JSON: " + result.getResponse().getContentAsString());

    }

    @Test
    @DisplayName("Should delete a client for id")
    void deleteScheduling() throws Exception{

        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("Adalberto");

        doNothing().when(clienteService).deletarCliente(cliente.getId());

        mockMvc.perform(delete("/clientes/{id}", cliente.getId()))
                .andExpect(status().isNoContent());
    }
}
