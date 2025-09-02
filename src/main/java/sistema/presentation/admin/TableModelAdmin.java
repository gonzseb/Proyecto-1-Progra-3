package sistema.presentation.admin;

import sistema.logic.entities.Farmaceuta;
import sistema.presentation.AbstractTableModel;

import java.util.List;


public class TableModelAdmin extends AbstractTableModel<Farmaceuta> implements javax.swing.table.TableModel {

    public TableModelAdmin(int[] cols, List<Farmaceuta> rows) {
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
    protected Object getPropetyAt(Farmaceuta e, int col) {
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
