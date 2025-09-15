package sistema.logic.entities;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class RecetaDetalle {

    private String codigoMedicamento; // (Medicamento medicamento)
    private int cantidad;
    private String indicaciones;
    private int duracionDias;

    public RecetaDetalle(String codigoMedicamento, int cantidad, String indicaciones, int duracionDias) {
        this.codigoMedicamento = codigoMedicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    public RecetaDetalle() {
        this.codigoMedicamento = "";
        this.cantidad = 0;
        this.indicaciones = "";
        this.duracionDias = 0;
    }

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public int getDuracionDias() {
        return duracionDias;
    }
}