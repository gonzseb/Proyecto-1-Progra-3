package sistema.presentation.pacientes;
import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractTableModel;

import java.util.List;


public class TableModel extends AbstractTableModel<Paciente> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Paciente> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int NACIMIENTO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Teléfono";
        colNames[NACIMIENTO] = "Naciemiento";
    }

    @Override
    protected Object getPropetyAt(Paciente e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            case TELEFONO:
                return e.getTelefono();
            case NACIMIENTO:
                return e.getNacimiento();
            default:
                return "";
        }
    }
}

