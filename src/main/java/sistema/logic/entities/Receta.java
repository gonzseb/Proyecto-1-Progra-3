package sistema.logic.entities;

import sistema.logic.entities.enums.EstadoReceta;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import sistema.data.LocalDateAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {
    private String id;

    //@XmlIDREF
    private String idMedico;
    // Sánchez dijo que esto no se hace en POO, lo correcto es tener la referencia al puntero (Medico medico)

    //@XmlIDREF
    private String idPaciente; // (Paciente paciente)

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaConfeccion;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaRetiro;

    private EstadoReceta estado;

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    private List<RecetaDetalle> detalles;

    public Receta(String id, String idMedico, String idPaciente, LocalDate fechaConfeccion) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fechaConfeccion = fechaConfeccion;
        this.estado = EstadoReceta.CONFECCIONADA;
        this.detalles = new ArrayList<>();
    }

    public Receta() {
        this.id = "";
        this.idMedico = "";
        this.idPaciente = "";
        this.fechaConfeccion = null;
        this.fechaRetiro = null;
        this.estado = EstadoReceta.CONFECCIONADA;
        this.detalles = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public LocalDate getFechaConfeccion() {
        return fechaConfeccion;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public EstadoReceta getEstado() {
        return estado;
    }

    public List<RecetaDetalle> getDetalles() {
        return detalles;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public void setEstado(EstadoReceta estado) {
        this.estado = estado;
    }

    public void agregarDetalle(RecetaDetalle detalle) {
        detalles.add(detalle);
    }
}