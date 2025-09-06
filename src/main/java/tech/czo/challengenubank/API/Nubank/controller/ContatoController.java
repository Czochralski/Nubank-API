package tech.czo.challengenubank.API.Nubank.controller;

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
public class ContatoController {
    
    private final ContatoService contatoService;

    @PostMapping
    public ResponseEntity<Contato> salvarContato(@RequestBody @Valid ContatoDTO dto){
         Contato contato = contatoService.salvarContato(dto);
         return ResponseEntity.status(HttpStatus.CREATED).body(contato);
    }
}
