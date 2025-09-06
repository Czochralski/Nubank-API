package tech.czo.challengenubank.API.Nubank.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
    public  ClienteNotFoundException(){
        super("Cliente não encontrado");
    }
}
