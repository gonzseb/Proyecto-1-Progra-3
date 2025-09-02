package sistema.presentation.pacientes;
import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractTableModel;

import java.util.List;


public class TableModelPaciente extends AbstractTableModel<Paciente> implements javax.swing.table.TableModel {

    public TableModelPaciente(int[] cols, List<Paciente> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
    }

    @Override
    protected Object getPropetyAt(Paciente e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            default:
                return "";
        }
    }
}

