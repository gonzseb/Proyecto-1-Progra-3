package sistema.presentation.popUpMedicamentoDetalles;

import sistema.logic.entities.Medicamento;
import sistema.logic.entities.RecetaDetalle;
import javax.swing.*;

public class View extends JDialog {
    private JPanel MainPanel;
    private JSpinner spinnerCantidad;
    private JSpinner spinnerDuracion;
    private JButton guardarButton;
    private JButton volverButton;
    private JTextArea textAreaIndicaciones;

    private Medicamento selectedMedicamento;
    private RecetaDetalle recetaDetalle;
    private boolean saved = false;

    public View(JFrame parent, Medicamento medicamento) {
        super(parent, "Detalles - " + medicamento.getNombre(), true);
        this.selectedMedicamento = medicamento;

        setContentPane(MainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setupComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponents() {
        // Setup spinners
        SpinnerNumberModel cantidadModel = new SpinnerNumberModel(1, 1, 60, 1);
        SpinnerNumberModel duracionModel = new SpinnerNumberModel(1, 1, 30, 1);

        spinnerCantidad.setModel(cantidadModel);
        spinnerDuracion.setModel(duracionModel);

        // Setup text area
        textAreaIndicaciones.setRows(4);
        textAreaIndicaciones.setLineWrap(true);
        textAreaIndicaciones.setWrapStyleWord(true);

        // Button listeners
        guardarButton.addActionListener(e -> saveDetails());
        volverButton.addActionListener(e -> closeDialog());
    }

    private void saveDetails() {
        if (!validateFields()) {
            return;
        }

        int cantidad = (Integer) spinnerCantidad.getValue();
        int duracion = (Integer) spinnerDuracion.getValue();
        String indicaciones = textAreaIndicaciones.getText().trim();

        recetaDetalle = new RecetaDetalle(
                selectedMedicamento.getCodigo(),
                cantidad,
                indicaciones,
                duracion
        );

        saved = true;
        setVisible(false);
    }

    private boolean validateFields() {
        int cantidad = (Integer) spinnerCantidad.getValue();
        int duracion = (Integer) spinnerDuracion.getValue();

        if (cantidad < 1 || cantidad > 60) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe estar entre 1 y 60",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (duracion < 1 || duracion > 30) {
            JOptionPane.showMessageDialog(this,
                    "La duración debe estar entre 1 y 30 días",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String indicaciones = textAreaIndicaciones.getText().trim();
        if (indicaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Las indicaciones son requeridas",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void closeDialog() {
        saved = false;
        setVisible(false);
    }

    public RecetaDetalle getRecetaDetalle() {
        return saved ? recetaDetalle : null;
    }
}