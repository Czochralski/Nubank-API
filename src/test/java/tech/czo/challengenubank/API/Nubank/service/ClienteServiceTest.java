package tech.czo.challengenubank.API.Nubank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.czo.challengenubank.API.Nubank.controller.ClienteController;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ClienteMapper;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private ClienteMapper clienteMapper;

    @MockitoBean
    private ContatoMapper contatoMapper;

    @Nested
    class saveClient {
        @Test
        @DisplayName("Should create a client with success")
        void saveClientWithContacts() throws Exception {

            Cliente cliente = new Cliente();
            cliente.setId(UUID.randomUUID());
            cliente.setNome("João");

            Contato c1 = new Contato();
            c1.setId(UUID.randomUUID());
            c1.setEmail("Adalberto@gmail.com");
            c1.setTelefone("9999-0000");
            c1.setCliente(cliente);

            Contato c2 = new Contato();
            c2.setId(UUID.randomUUID());
            c2.setEmail("Adalberto2@gmail.com");
            c2.setTelefone("8888-1111");
            c2.setCliente(cliente);

            cliente.setContatos(java.util.List.of(c1, c2));

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

            Cliente cliente = new Cliente();
            cliente.setId(UUID.randomUUID());
            cliente.setNome("João");


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

        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("João");

        Contato c1 = new Contato();
        c1.setId(UUID.randomUUID());
        c1.setEmail("Adalberto@gmail.com");
        c1.setTelefone("9999-0000");
        c1.setCliente(cliente);

        Contato c2 = new Contato();
        c2.setId(UUID.randomUUID());
        c2.setEmail("Adalberto2@gmail.com");
        c2.setTelefone("8888-1111");
        c2.setCliente(cliente);


        cliente.setContatos(java.util.List.of(c1, c2));

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
        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("Adalberto");

        Contato c1 = new Contato();
        c1.setId(UUID.randomUUID());
        c1.setEmail("Adalberto@gmail.com");
        c1.setTelefone("9999-0000");
        c1.setCliente(cliente);

        Contato c2 = new Contato();
        c2.setId(UUID.randomUUID());
        c2.setEmail("Adalberto2@gmail.com");
        c2.setTelefone("8888-1111");
        c2.setCliente(cliente);

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                cliente.getId(), cliente.getNome(), List.of(c1,c2)
        );

        Cliente cliente2 = new Cliente();
        cliente2.setId(UUID.randomUUID());
        cliente2.setNome("Julia");

        Contato c3 = new Contato();
        cliente.setId(UUID.randomUUID());
        c3.setEmail("Julia@gmail.com");
        c3.setTelefone("98845-0000");
        c3.setCliente(cliente);

        Contato c4 = new Contato();
        cliente.setId(UUID.randomUUID());
        c4.setEmail("Julia2@gmail.com");
        c4.setTelefone("99412-1111");
        c4.setCliente(cliente);

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
