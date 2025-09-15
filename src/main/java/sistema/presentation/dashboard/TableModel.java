package sistema.presentation.dashboard;

import sistema.presentation.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<MedicationMonthlySummary> {

    public TableModel(int[] cols, List<MedicationMonthlySummary> rows) {
        super(cols, rows);
    }

    public static final int MEDICAMENTO = 0;
    public static final int PERIODO = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[MEDICAMENTO] = "Medicamento";
        colNames[PERIODO] = "Año-Mes";
    }

    @Override
    protected Object getPropetyAt(MedicationMonthlySummary e, int col) {
        switch (cols[col]) {
            case MEDICAMENTO:
                return e.getMedicationName();
            case PERIODO:
                return e.getYearMonth();
            default:
                return "";
        }
    }
}

class MedicationMonthlySummary {
    private String medicationName;
    private String yearMonth;
    private int count;

    public MedicationMonthlySummary(String medicationName, String yearMonth, int count) {
        this.medicationName = medicationName;
        this.yearMonth = yearMonth;
        this.count = count;
    }

    public String getMedicationName() { return medicationName; }
    public String getYearMonth() { return yearMonth; }
    public int getCount() { return count; }
}