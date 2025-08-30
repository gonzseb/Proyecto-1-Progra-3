package data;

import logic.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class ListaFarmaceutas {
    private List<Farmaceuta> farmaceutas;

    public ListaFarmaceutas() {
        farmaceutas = new ArrayList<>();
    }

    // Agregar farmaceuta
    public void agregarFarmaceuta(Farmaceuta farmaceuta) {
        farmaceutas.add(farmaceuta);
    }

    // Eliminar farmaceuta por ID
    public boolean eliminarFarmaceuta(String id) {
        return farmaceutas.removeIf(f -> f.getId().equals(id));
    }

    // Buscar farmaceuta por ID
    public Farmaceuta buscarPorId(String id) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    // Mostrar todos los farmaceutas
    public void mostrarFarmaceutas() {
        if (farmaceutas.isEmpty()) {
            System.out.println("No hay farmaceutas registrados.");
        } else {
            for (Farmaceuta f : farmaceutas) {
                System.out.println(f.mostrarInfo());
            }
        }
    }

    // Obtener lista completa
    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }
}
