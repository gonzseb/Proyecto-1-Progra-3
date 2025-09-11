package sistema.presentation.popUpSeleccionMedicamento;

import sistema.logic.entities.Medicamento;
import sistema.logic.Service;
import sistema.logic.entities.RecetaDetalle;
import sistema.presentation.medicamentos.TableModel;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View extends JDialog {
    private JPanel panel1;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JTable tableMedicamentos;
    private JButton cancelarButton;
    private JButton OKButton1;
    private JButton buscarButton;

    private Medicamento selectedMedicamento;
    private RecetaDetalle recetaDetalle;
    private boolean confirmed = false;

    public View(JFrame parent) {
        super(parent, "Medicamentos", true);
        setContentPane(panel1);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setupComponents();
        loadAllMedicamentos();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponents() {
        // Initialize combo box with search options
        comboBox1.addItem("Nombre");
        comboBox1.addItem("Código");

        // Button listeners
        buscarButton.addActionListener(e -> searchMedicamentos());
        OKButton1.addActionListener(e -> selectMedicamento());
        cancelarButton.addActionListener(e -> closeDialog());

        // Double-click to select medication
        tableMedicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selectMedicamento();
                }
            }
        });

        // Enter in text field triggers search
        textField1.addActionListener(e -> searchMedicamentos());
    }

    private void loadAllMedicamentos() {
        try {
            List<Medicamento> medicamentos = Service.instance().findAllMedicamentos();
            if (medicamentos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay medicamentos registrados",
                        "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            } else {
                updateTable(medicamentos);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando medicamentos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchMedicamentos() {
        String searchText = textField1.getText().trim();
        String searchType = (String) comboBox1.getSelectedItem();

        if (searchText.isEmpty()) {
            loadAllMedicamentos();
            return;
        }

        try {
            List<Medicamento> medicamentos;

            if ("Código".equals(searchType)) {
                // Search by codigo - contains
                medicamentos = Service.instance().findAllMedicamentos().stream()
                        .filter(m -> m.getCodigo().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(java.util.stream.Collectors.toList());
            } else { // "Nombre"
                // Search by name using existing service method
                medicamentos = Service.instance().findSomeMedicamentos(searchText);
            }

            if (medicamentos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron medicamentos con " + searchType.toLowerCase() + " que contenga: " + searchText,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                // Keep current table, don't clear it
            } else {
                updateTable(medicamentos);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Medicamento> medicamentos) {
        int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.PRESENTACION};
        tableMedicamentos.setModel(new TableModel(cols, medicamentos));
    }

    private void selectMedicamento() {
        int selectedRow = tableMedicamentos.getSelectedRow();
        if (selectedRow >= 0) {
            TableModel model = (TableModel) tableMedicamentos.getModel();
            selectedMedicamento = model.getRowAt(selectedRow);

            // Close this dialog and open details dialog
            setVisible(false);

            // Open details dialog
            sistema.presentation.popUpMedicamentoDetalles.View detailsDialog =
                    new sistema.presentation.popUpMedicamentoDetalles.View((JFrame)getParent(), selectedMedicamento);
            detailsDialog.setVisible(true);

            // Check if details were saved
            if (detailsDialog.getRecetaDetalle() != null) {
                confirmed = true;
                // Store the complete RecetaDetalle instead of just Medicamento
                this.recetaDetalle = detailsDialog.getRecetaDetalle();
            } else {
                confirmed = false;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un medicamento de la tabla",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ADD this new getter method:
    public RecetaDetalle getRecetaDetalle() {
        return confirmed ? recetaDetalle : null;
    }

    // Keep the existing getter for backward compatibility:
    public Medicamento getSelectedMedicamento() {
        return confirmed ? selectedMedicamento : null;
    }

    private void closeDialog() {
        confirmed = false;
        setVisible(false);
    }

}