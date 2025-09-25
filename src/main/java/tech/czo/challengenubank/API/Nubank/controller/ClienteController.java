package tech.czo.challengenubank.API.Nubank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.czo.challengenubank.API.Nubank.dto.ClienteRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.service.ClienteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes")
public class ClienteController{


    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastrar novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso")
    })
    public ResponseEntity<ClienteResponseDTO> salvarCliente(@RequestBody @Valid ClienteRequestDTO dto){
        return ResponseEntity.ok().body(clienteService.salvarCliente(dto));
    }

    @GetMapping
    @Operation(summary = "Listar Clientes", description = "Retorna lista de clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<ClienteResponseDTO>> listarCliente(){
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("{id}/contatos")
    @Operation(summary = "Buscar por ID", description = "Retorna cliente e seus contatos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<ClienteResponseDTO>> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(clienteService.ListarContatoId(id));
        
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar", description = "Deleta cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void>deletarCliente(@PathVariable UUID id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
