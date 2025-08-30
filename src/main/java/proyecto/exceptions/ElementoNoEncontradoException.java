package proyecto.exceptions;

// Excepción para elementos no encontrados
public class ElementoNoEncontradoException extends RuntimeException {
    public ElementoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}