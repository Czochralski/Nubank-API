package tech.czo.challengenubank.API.Nubank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.czo.challengenubank.API.Nubank.dto.ContatoRequestDTO;
import tech.czo.challengenubank.API.Nubank.dto.ContatoResponseDTO;
import tech.czo.challengenubank.API.Nubank.service.ContatoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contatos")
@Tag(name = "Contatos")
public class ContatoController {
    
    private final ContatoService contatoService;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastrar novo contato")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso")
    })
    public ResponseEntity<ContatoResponseDTO> salvarContato(@RequestBody @Valid ContatoRequestDTO dto){
        return ResponseEntity.ok().body(contatoService.salvarContato(dto));}
}
