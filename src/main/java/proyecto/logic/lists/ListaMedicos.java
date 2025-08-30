package proyecto.logic.lists;

import proyecto.logic.entities.Medico;

public class ListaMedicos extends ListaBase<Medico> {
    public void agregar(Medico m) {
        super.agregar(m, x -> x.getId().equals(m.getId()),
                "Médico duplicado - ID: " + m.getId());
    }

    public Medico buscarPorId(String id) {
        return super.buscar(m -> m.getId().equals(id),
                "Médico no encontrado - ID: " + id);
    }

    public void eliminarPorId(String id) {
        super.eliminar(m -> m.getId().equals(id),
                "Médico no encontrado - ID: " + id);
    }

    public void modificar(String id, String nuevoNombre, String nuevaEspecialidad) {
        Medico m = buscarPorId(id);
        m.setNombre(nuevoNombre);
        m.setEspecialidad(nuevaEspecialidad);
    }

    public ListaMedicos busquedaParcialPorNombre(String nombreParcial) {
        ListaMedicos resultado = new ListaMedicos();
        listar().stream()
                .filter(m -> m.getNombre() != null &&
                        m.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .forEach(resultado::agregar);
        return resultado;
    }

}