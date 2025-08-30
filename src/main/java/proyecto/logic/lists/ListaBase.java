package proyecto.logic.lists;

import proyecto.exceptions.ElementoDuplicadoException;
import proyecto.exceptions.ElementoNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

abstract class ListaBase<T> {
    protected List<T> elementos = new ArrayList<>();

    protected void agregar(T elemento, Predicate<T> criterioDuplicado, String mensajeDuplicado) {
        if (elementos.stream().anyMatch(criterioDuplicado)) {
            throw new ElementoDuplicadoException(mensajeDuplicado);
        }
        elementos.add(elemento);
    }

    protected T buscar(Predicate<T> criterio, String mensajeNoEncontrado) {
        return elementos.stream()
                .filter(criterio)
                .findFirst()
                .orElseThrow(() -> new ElementoNoEncontradoException(mensajeNoEncontrado));
    }

    protected void eliminar(Predicate<T> criterio, String mensajeNoEncontrado) {
        boolean eliminado = elementos.removeIf(criterio);
        if (!eliminado) throw new ElementoNoEncontradoException(mensajeNoEncontrado);
    }

    public List<T> listar() {
        return elementos;
    }

}