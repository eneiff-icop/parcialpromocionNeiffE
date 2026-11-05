package icop.parcialpromocionneiffe.exception;

// Excepción personalizada por si un recurso no existe
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
