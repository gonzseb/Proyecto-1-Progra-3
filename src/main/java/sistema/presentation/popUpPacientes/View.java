package sistema.presentation.popUpPacientes;

import sistema.logic.entities.Paciente;
import sistema.logic.Service;
import sistema.presentation.pacientes.TableModel;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View extends JDialog {
    private JPanel panel1;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JTable pacientesTable;
    private JButton cancelarButton;
    private JButton OKButton;
    private JButton buscarButton;

    private Paciente selectedPatient;
    private boolean confirmed = false;

    public View(JFrame parent) {
        super(parent, "Seleccionar Paciente", true);
        setContentPane(panel1);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setupComponents();
        loadAllPatients();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponents() {
        // Button listeners
        buscarButton.addActionListener(e -> searchPatients());
        OKButton.addActionListener(e -> selectPatient());
        cancelarButton.addActionListener(e -> closeDialog());

        // Double-click to select patient
        pacientesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selectPatient();
                }
            }
        });

        // Enter in text field triggers search
        textField1.addActionListener(e -> searchPatients());
    }

    private void loadAllPatients() {
        try {
            List<Paciente> patients = Service.instance().findAllPacientes();
            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay pacientes registrados",
                        "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            } else {
                updateTable(patients);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando pacientes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchPatients() {
        String searchText = textField1.getText().trim();
        String searchType = (String) comboBox1.getSelectedItem();

        if (searchText.isEmpty()) {
            loadAllPatients();
            return;
        }

        try {
            List<Paciente> patients;

            if ("ID".equals(searchType)) {
                // Search by ID - contains
                patients = Service.instance().findAllPacientes().stream()
                        .filter(p -> p.getId().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(java.util.stream.Collectors.toList());
            } else { // "Nombre"
                // Search by name using existing service method
                patients = Service.instance().findSomePacientes(searchText);
            }

            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron pacientes con " + searchType.toLowerCase() + " que contenga: " + searchText,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                // Keep current table, don't clear it
            } else {
                updateTable(patients);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Paciente> patients) {
        int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.NACIMIENTO};
        pacientesTable.setModel(new TableModel(cols, patients));
    }

    private void selectPatient() {
        int selectedRow = pacientesTable.getSelectedRow();
        if (selectedRow >= 0) {
            TableModel model = (TableModel) pacientesTable.getModel();
            selectedPatient = model.getRowAt(selectedRow);
            confirmed = true;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un paciente de la tabla",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void closeDialog() {
        confirmed = false;
        setVisible(false);
    }

    public Paciente getSelectedPatient() {
        return confirmed ? selectedPatient : null;
    }
}