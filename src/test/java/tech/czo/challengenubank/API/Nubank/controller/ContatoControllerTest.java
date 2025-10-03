package tech.czo.challengenubank.API.Nubank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.czo.challengenubank.API.Nubank.controller.mapper.ContatoMapper;
import tech.czo.challengenubank.API.Nubank.dto.ContatoResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.model.Contato;
import tech.czo.challengenubank.API.Nubank.repository.ContatoRepository;
import tech.czo.challengenubank.API.Nubank.service.ContatoService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContatoController.class)
public class ContatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContatoService contatoService;

    @MockitoBean
    private ContatoMapper contatoMapper;

    @MockitoBean
    private ContatoRepository contatoRepository;

    Cliente cliente;
    Contato contato;

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
    }

    @Test
    @DisplayName("Shuold save contact with success")
    void saveContact() throws Exception{
        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(
                UUID.randomUUID(), contato.getEmail(), contato.getTelefone(),contato.getCliente().getId()
        );

        when(contatoService.salvarContato(any())).thenReturn(contatoResponseDTO);

        MvcResult result = mockMvc.perform(post("/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contatoResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contatoResponseDTO.id().toString()))
                .andReturn();

        System.out.println("Response JSON: " + result.getResponse().getContentAsString());
    }
}
