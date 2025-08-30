package proyecto.data.lists;

import proyecto.data.entities.Paciente;

public class ListaPacientes extends ListaBase<Paciente> {
    public void agregar(Paciente p) {
        super.agregar(p, x -> x.getId().equals(p.getId()),
                "Paciente duplicado: id=" + p.getId());
    }
    public Paciente buscarPorId(String id) {
        return super.buscar(p -> p.getId().equals(id),
                "Paciente no encontrado: id=" + id);
    }
    public void eliminarPorId(String id) {
        super.eliminar(p -> p.getId().equals(id),
                "Paciente no encontrado: id=" + id);
    }
    public void modificar(String id, String nuevoNombre, String nuevaFecha, String nuevoTelefono) {
        Paciente p = buscarPorId(id);
        p.setNombre(nuevoNombre);
        p.setFechaNacimiento(nuevaFecha);
        p.setTelefono(nuevoTelefono);
    }
}