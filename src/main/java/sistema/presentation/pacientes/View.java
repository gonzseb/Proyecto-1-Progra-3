package sistema.presentation.pacientes;

import sistema.ApplicationLogIn;
import sistema.logic.entities.Paciente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener  {
    private JPanel panel1;
    private JTextField idPaciente;
    private JTextField nacimientoField;
    private JTextField nombrePaciente;
    private JTextField telefonoField;
    private JButton guardarButton1;
    private JButton limpiarButton1;
    private JButton borrarButton1;
    private JTextField pacienteBusquedaField;
    private JButton buscarButton1;
    private JTable pacientes;

    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanel() { return panel1; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.NACIMIENTO};

                pacientes.setModel(new TableModel(cols, model.getList()));

                break;
            case Model.CURRENT:
                idPaciente.setText(model.getCurrent().getId());
                nombrePaciente.setText(model.getCurrent().getNombre());
                telefonoField.setText(model.getCurrent().getTelefono());
                nacimientoField.setText(model.getCurrent().getNacimiento());

                idPaciente.setBackground(null);
                idPaciente.setToolTipText(null);

                nombrePaciente.setBackground(null);
                nombrePaciente.setToolTipText(null);

                telefonoField.setBackground(null);
                telefonoField.setToolTipText(null);

                nacimientoField.setBackground(null);
                nacimientoField.setToolTipText(null);


                break;
        }
        this.panel1.revalidate();
    }


    public View() {
        guardarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Paciente paciente = take();
                    try {
                        // Si el modelo ya tiene un current con el mismo ID, entonces lo actualiza
                        if (model.getCurrent() != null && !model.getCurrent().getId().isEmpty()
                                && model.getCurrent().getId().equals(paciente.getId())) {
                            controller.update(paciente);
                            JOptionPane.showMessageDialog(panel1, "Paciente actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Si no, se crea
                            controller.create(paciente);
                            JOptionPane.showMessageDialog(panel1, "Paciente registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        limpiarButton1.addActionListener(e -> {
            controller.clear();
            pacienteBusquedaField.setText("");
        });

        pacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = pacientes.getSelectedRow();
                if (selectedRow >= 0) {
                    Paciente seleccionado = ((TableModel) pacientes.getModel()).getRowAt(selectedRow);
                    model.setCurrent(seleccionado);
                }
            }
        });

        borrarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrent().getId().isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "No hay pacientes seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel1,
                        "¿Está seguro de borrar al paciente " + model.getCurrent().getNombre() + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(model.getCurrent().getId());
                        JOptionPane.showMessageDialog(panel1, "Paciente eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarButton1.addActionListener(e -> {
            String textoBusqueda = pacienteBusquedaField.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                controller.findPacienteByName(textoBusqueda);

                if (model.getList().isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "No se encontraron pacientes con ese nombre.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel1, "Ingrese un texto para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

    }

    // --- Métodos auxiliares ---
    public Paciente take() {
        Paciente p = new Paciente();
        p.setId(idPaciente.getText());
        p.setNombre(nombrePaciente.getText());
        p.setTelefono(telefonoField.getText());
        p.setNacimiento(nacimientoField.getText());
        return p;
    }

    private boolean validateForm() {
        boolean valid = true;
        valid &= validarCampo(idPaciente, "ID Requerido");
        valid &= validarCampo(nombrePaciente, "Nombre requerido");
        valid &= validarTelefono(telefonoField);
        valid &= validarNacimiento(nacimientoField);
        return valid;
    }

    private boolean validarCampo(JTextField field, String mensajeError) {
        if (field.getText().isEmpty()) {
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText(mensajeError);
            return false;
        } else {
            field.setBackground(null);
            field.setToolTipText(null);
            return true;
        }
    }

    private boolean validarTelefono(JTextField field) {
        String texto = field.getText().trim();
        if (!texto.matches("\\d{8}")) {  // 8 dígitos exactos
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText("Teléfono debe tener 8 números");
            return false;
        }
        field.setBackground(null);
        field.setToolTipText(null);
        return true;
    }

    private boolean validarNacimiento(JTextField field) {
        String texto = field.getText().trim();
        if (!texto.matches("\\d{2}-\\d{2}-\\d{4}")) {  // formato dd/MM/yyyy
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText("Formato inválido: dd/MM/yyyy");
            return false;
        }

        String[] partes = texto.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        if (dia <= 0 || dia >= 32) {
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText("Día inválido (1-31)");
            return false;
        }
        if (mes <= 0 || mes > 12) {
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText("Mes inválido (1-12)");
            return false;
        }

        // Fecha máxima 14/09/2025
        if (ano > 2025 || (ano == 2025 && (mes > 9 || (mes == 9 && dia > 14)))) {
            field.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            field.setToolTipText("Fecha no puede ser posterior al 14/09/2025");
            return false;
        }

        field.setBackground(null);
        field.setToolTipText(null);
        return true;
    }

}
