package tech.czo.challengenubank.API.Nubank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.czo.challengenubank.API.Nubank.model.Contato;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ContatoRepository extends JpaRepository<Contato, UUID> {
}
