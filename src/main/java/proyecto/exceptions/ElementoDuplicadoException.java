package proyecto.exceptions;

// Excepción para elementos duplicados
public class ElementoDuplicadoException extends RuntimeException {
    public ElementoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}