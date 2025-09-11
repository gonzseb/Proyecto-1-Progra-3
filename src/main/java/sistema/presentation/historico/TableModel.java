package sistema.presentation.historico;

import sistema.logic.entities.Receta;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.Medico;
import sistema.logic.Service;
import sistema.presentation.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Receta> {

    public TableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int PACIENTE = 1;
    public static final int MEDICO = 2;
    public static final int FECHA_CONFECCION = 3;
    public static final int ESTADO = 4;

    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[ID] = "ID Receta";
        colNames[PACIENTE] = "Paciente";
        colNames[MEDICO] = "Médico";
        colNames[FECHA_CONFECCION] = "Fecha Confección";
        colNames[ESTADO] = "Estado";
    }

    @Override
    protected Object getPropetyAt(Receta e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case PACIENTE:
                // Look up patient name by ID
                try {
                    List<Paciente> pacientes = Service.instance().findAllPacientes();
                    return pacientes.stream()
                            .filter(p -> p.getId().equals(e.getIdPaciente()))
                            .map(Paciente::getNombre)
                            .findFirst()
                            .orElse("Desconocido");
                } catch (Exception ex) {
                    return "Error";
                }
            case MEDICO:
                // Look up doctor name by ID
                try {
                    List<Medico> medicos = Service.instance().findAllMedicos();
                    return medicos.stream()
                            .filter(m -> m.getId().equals(e.getIdMedico()))
                            .map(Medico::getNombre)
                            .findFirst()
                            .orElse("Desconocido");
                } catch (Exception ex) {
                    return "Error";
                }
            case FECHA_CONFECCION:
                return e.getFechaConfeccion();
            case ESTADO:
                return e.getEstado();
            default:
                return "";
        }
    }
}