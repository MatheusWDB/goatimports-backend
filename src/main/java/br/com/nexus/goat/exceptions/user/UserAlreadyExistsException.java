package br.com.nexus.goat.exceptions.user;

public class UserAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String msg) {
        super(msg + " já foi cadastrado por outro usuário.");
    }
}
