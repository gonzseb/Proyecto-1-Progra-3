package sistema.presentation.prescribir;

import sistema.logic.entities.RecetaDetalle;
import sistema.logic.entities.Medicamento;
import sistema.logic.Service;
import sistema.presentation.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<RecetaDetalle> {

    public TableModel(int[] cols, List<RecetaDetalle> rows) {
        super(cols, rows);
    }

    public static final int MEDICAMENTO = 0;
    public static final int PRESENTACION = 1;
    public static final int CANTIDAD = 2;
    public static final int INDICACIONES = 3;
    public static final int DURACION = 4;

    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[MEDICAMENTO] = "Medicamento";
        colNames[PRESENTACION] = "Presentación";
        colNames[CANTIDAD] = "Cantidad";
        colNames[INDICACIONES] = "Indicaciones";
        colNames[DURACION] = "Duración (días)";
    }

    @Override
    protected Object getPropetyAt(RecetaDetalle e, int col) {
        switch (cols[col]) {
            case MEDICAMENTO:
                try {
                    List<Medicamento> meds = Service.instance().findAllMedicamentos();
                    return meds.stream()
                            .filter(m -> m.getCodigo().equals(e.getCodigoMedicamento()))
                            .map(Medicamento::getNombre)
                            .findFirst()
                            .orElse("Desconocido");
                } catch (Exception ex) {
                    return "Error";
                }
            case PRESENTACION:
                try {
                    List<Medicamento> meds = Service.instance().findAllMedicamentos();
                    return meds.stream()
                            .filter(m -> m.getCodigo().equals(e.getCodigoMedicamento()))
                            .map(Medicamento::getPresentacion)
                            .findFirst()
                            .orElse("Desconocida");
                } catch (Exception ex) {
                    return "Error";
                }
            case CANTIDAD:
                return e.getCantidad();
            case INDICACIONES:
                return e.getIndicaciones();
            case DURACION:
                return e.getDuracionDias();
            default:
                return "";
        }
    }
}