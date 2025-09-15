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
        comboBox1.addItem("Nombre");
        comboBox1.addItem("Código");

        buscarButton.addActionListener(e -> searchMedicamentos());
        OKButton1.addActionListener(e -> selectMedicamento());
        cancelarButton.addActionListener(e -> closeDialog());

        tableMedicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selectMedicamento();
                }
            }
        });

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
                medicamentos = Service.instance().findAllMedicamentos().stream()
                        .filter(m -> m.getCodigo().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(java.util.stream.Collectors.toList());
            } else {
                medicamentos = Service.instance().findSomeMedicamentos(searchText);
            }

            if (medicamentos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron medicamentos con " + searchType.toLowerCase() + " que contenga: " + searchText,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
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

            setVisible(false);

            sistema.presentation.popUpMedicamentoDetalles.View detailsDialog =
                    new sistema.presentation.popUpMedicamentoDetalles.View((JFrame)getParent(), selectedMedicamento);
            detailsDialog.setVisible(true);

            if (detailsDialog.getRecetaDetalle() != null) {
                confirmed = true;
                this.recetaDetalle = detailsDialog.getRecetaDetalle();
            } else {
                confirmed = false;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un medicamento de la tabla",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }

    public RecetaDetalle getRecetaDetalle() {
        return confirmed ? recetaDetalle : null;
    }

    public Medicamento getSelectedMedicamento() {
        return confirmed ? selectedMedicamento : null;
    }

    private void closeDialog() {
        confirmed = false;
        setVisible(false);
    }

}