package icop.parcialpromocionneiffe.exception;

// Excepción personalizada para errores en reglas del negocio
public class OperacionInvalidaException extends RuntimeException {
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }

}
