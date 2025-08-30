package proyecto.logic.lists;

import proyecto.logic.entities.Medicamento;

public class ListaMedicamentos extends ListaBase<Medicamento> {
    public void agregar(Medicamento m) {
        super.agregar(m, x -> x.getCodigo().equals(m.getCodigo()),
                "Medicamento duplicado - Código: " + m.getCodigo());
    }
    public Medicamento buscarPorCodigo(String codigo) {
        return super.buscar(m -> m.getCodigo().equals(codigo),
                "Medicamento no encontrado - Código: " + codigo);
    }
    public void eliminarPorCodigo(String codigo) {
        super.eliminar(m -> m.getCodigo().equals(codigo),
                "Medicamento no encontrado - Código: " + codigo);
    }
    public void modificar(String codigo, String nuevoNombre, String nuevaPresentacion) {
        Medicamento m = buscarPorCodigo(codigo);
        m.setNombre(nuevoNombre);
        m.setPresentacion(nuevaPresentacion);
    }
}