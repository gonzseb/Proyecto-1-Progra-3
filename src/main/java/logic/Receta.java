package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {
    private String id;
    private String idMedico;
    private String idPaciente;
    private LocalDate fechaConfeccion;
    private LocalDate fechaRetiro;
    private EstadoReceta estado;
    private List<RecetaDetalle> detalles;

    public Receta(String id, String idMedico, String idPaciente, LocalDate fechaConfeccion) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fechaConfeccion = fechaConfeccion;
        this.estado = EstadoReceta.CONFECCIONADA;
        this.detalles = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getIdMedico() { return idMedico; }
    public String getIdPaciente() { return idPaciente; }
    public LocalDate getFechaConfeccion() { return fechaConfeccion; }
    public LocalDate getFechaRetiro() { return fechaRetiro; }
    public EstadoReceta getEstado() { return estado; }
    public List<RecetaDetalle> getDetalles() { return detalles; }

    public void setFechaRetiro(LocalDate fechaRetiro) { this.fechaRetiro = fechaRetiro; }
    public void setEstado(EstadoReceta estado) { this.estado = estado; }

    public void agregarDetalle(RecetaDetalle detalle) {
        detalles.add(detalle);
    }

    @Override
    public String toString() {
        return "Receta " + id + " - Paciente: " + idPaciente + " - Estado: " + estado;
    }
}
