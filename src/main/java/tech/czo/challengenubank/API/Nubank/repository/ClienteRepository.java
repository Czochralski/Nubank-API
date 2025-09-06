package tech.czo.challengenubank.API.Nubank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.czo.challengenubank.API.Nubank.model.Cliente;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
