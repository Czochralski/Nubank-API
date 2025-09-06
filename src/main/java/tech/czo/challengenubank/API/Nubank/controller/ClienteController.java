package tech.czo.challengenubank.API.Nubank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.czo.challengenubank.API.Nubank.dto.ClienteDTO;
import tech.czo.challengenubank.API.Nubank.dto.ClienteResponseDTO;
import tech.czo.challengenubank.API.Nubank.model.Cliente;
import tech.czo.challengenubank.API.Nubank.service.ClienteService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController{


    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid ClienteDTO dto){
        Cliente cliente = clienteService.salvarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarCliente(){
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("{id}/contatos")
    public ResponseEntity<List<ClienteResponseDTO>> listarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(clienteService.ListarContatoId(id));
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void>deletarCliente(@PathVariable UUID id){
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(id);
        if(clienteOptional.isPresent()){
            clienteService.deletarCliente(clienteOptional.get());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
