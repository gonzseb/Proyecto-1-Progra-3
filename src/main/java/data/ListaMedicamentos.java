package data;

import logic.Medicamento;
import java.util.ArrayList;
import java.util.List;

public class ListaMedicamentos {
    private List<Medicamento> medicamentos;

    public ListaMedicamentos() {
        medicamentos = new ArrayList<>();
    }

    public void agregar(Medicamento m) {
        if (!medicamentos.contains(m)) {
            medicamentos.add(m);
        }
    }

    public Medicamento buscarPorCodigo(String codigo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Medicamento> getTodos() {
        return medicamentos;
    }
}

