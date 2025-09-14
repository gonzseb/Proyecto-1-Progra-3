package sistema.presentation.medicamentos;

import sistema.ApplicationLogIn;
import sistema.logic.entities.Medicamento;

import javax.swing.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField presentacionField;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTextField nombreBusquedaField;
    private JButton buscarButton;
    private JTable medicamentos;

    // -- MVC --
    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public View() {

        // Guardar/Actualizar
        guardarButton.addActionListener(e -> {
            if (validateForm()) {
                Medicamento medicamento = take();
                try {
                    // Si el current ya tiene el mismo código, actualiza
                    if (model.getCurrent() != null && !model.getCurrent().getCodigo().isEmpty()
                            && model.getCurrent().getCodigo().equals(medicamento.getCodigo())) {
                        controller.update(medicamento);
                        JOptionPane.showMessageDialog(panel1, "Medicamento actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.create(medicamento);
                        JOptionPane.showMessageDialog(panel1, "Medicamento registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(e -> {
            controller.clear();
            nombreBusquedaField.setText("");
        });

        medicamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = medicamentos.getSelectedRow();
                if (selectedRow >= 0) {
                    Medicamento seleccionado = ((sistema.presentation.medicamentos.TableModel) medicamentos.getModel()).getRowAt(selectedRow);
                    model.setCurrent(seleccionado);
                }
            }
        });

        borrarButton.addActionListener(e -> {
            if (model.getCurrent() == null || model.getCurrent().getCodigo().isEmpty()) {
                JOptionPane.showMessageDialog(panel1, "No hay medicamento seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1,
                    "¿Está seguro de borrar el medicamento " + model.getCurrent().getNombre() + "?",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.delete(model.getCurrent().getCodigo());
                    JOptionPane.showMessageDialog(panel1, "Medicamento eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buscarButton.addActionListener(e -> {
            String texto = nombreBusquedaField.getText().trim();
            if (!texto.isEmpty()) {
                controller.findMedicamentoByName(texto);

                if (model.getList().isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "No se encontraron medicamentos con ese nombre.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel1, "Ingrese un texto para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });


    }

    // --- Métodos auxiliares ---
    public Medicamento take() {
        Medicamento e = new Medicamento();
        e.setCodigo(codigoField.getText());
        e.setNombre(nombreField.getText());
        e.setPresentacion(presentacionField.getText());
        return e;
    }

    private boolean validateForm() {
        boolean valid = true;
        valid &= validarCampo(codigoField, "Código Requerido");
        valid &= validarCampo(nombreField, "Nombre requerido");
        valid &= validarCampo(presentacionField, "Presentación requerida");
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

    public JPanel getPanel() { return panel1; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {

            case Model.LIST:
                int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.PRESENTACION};
                medicamentos.setModel(new TableModel(cols, model.getList()));
                break;

            case Model.CURRENT:
                if (model.getCurrent() != null) {
                    codigoField.setText(model.getCurrent().getCodigo());
                    nombreField.setText(model.getCurrent().getNombre());
                    presentacionField.setText(model.getCurrent().getPresentacion());

                    codigoField.setBackground(null);
                    nombreField.setBackground(null);
                    presentacionField.setBackground(null);

                    codigoField.setToolTipText(null);
                    nombreField.setToolTipText(null);
                    presentacionField.setToolTipText(null);
                }
                break;
        }
        panel1.revalidate();
    }




}
