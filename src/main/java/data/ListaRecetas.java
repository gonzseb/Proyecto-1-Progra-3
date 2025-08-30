package data;

import logic.Receta;
import java.util.ArrayList;
import java.util.List;

public class ListaRecetas {
    private List<Receta> recetas;

    public ListaRecetas() {
        recetas = new ArrayList<>();
    }

    public void agregar(Receta r) {
        if (!recetas.contains(r)) {
            recetas.add(r);
        }
    }

    public Receta buscarPorId(String id) {
        return recetas.stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Receta> getTodas() {
        return recetas;
    }
}

