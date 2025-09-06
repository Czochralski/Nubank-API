package tech.czo.challengenubank.API.Nubank.exceptions;

public class ContatoEmptyException extends RuntimeException{

    public ContatoEmptyException() {
        super("Nenhum campo de contato deve estar vazio");
    }

    public ContatoEmptyException(String message) {
        super(message);
    }
}
