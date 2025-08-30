package proyecto.logic.lists;

import proyecto.logic.entities.Farmaceuta;

public class ListaFarmaceutas extends ListaBase<Farmaceuta> {
    public void agregar(Farmaceuta f) {
        super.agregar(f, x -> x.getId().equals(f.getId()),
                "Farmaceuta duplicado - ID: " + f.getId());
    }
    public Farmaceuta buscarPorId(String id) {
        return super.buscar(f -> f.getId().equals(id),
                "Farmaceuta no encontrado - ID: " + id);
    }
    public void eliminarPorId(String id) {
        super.eliminar(f -> f.getId().equals(id),
                "Farmaceuta no encontrado - ID: " + id);
    }
    public void modificar(String id, String nuevoNombre, String clave) {
        Farmaceuta f = buscarPorId(id);
        f.setNombre(nuevoNombre);
        f.setClave(clave);
    }
}