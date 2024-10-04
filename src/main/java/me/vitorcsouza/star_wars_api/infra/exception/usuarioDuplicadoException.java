package me.vitorcsouza.star_wars_api.infra.exception;

public class usuarioDuplicadoException extends RuntimeException {
    private final String message;
    public usuarioDuplicadoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
