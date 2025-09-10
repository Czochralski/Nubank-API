package tech.czo.challengenubank.API.Nubank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.czo.challengenubank.API.Nubank.dto.ContatoDTO;
import tech.czo.challengenubank.API.Nubank.model.Contato;
import tech.czo.challengenubank.API.Nubank.service.ContatoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("contatos")
@Tag(name = "Contatos")
public class ContatoController {
    
    private final ContatoService contatoService;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastrar novo contato")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso")
    })
    public ResponseEntity<Contato> salvarContato(@RequestBody @Valid ContatoDTO dto){
         Contato contato = contatoService.salvarContato(dto);
         return ResponseEntity.status(HttpStatus.CREATED).body(contato);
    }
}
