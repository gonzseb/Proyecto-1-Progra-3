package sistema.presentation.farmaceutas;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import sistema.ApplicationLogIn;
import sistema.logic.entities.Farmaceuta;


public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JTextField IdFieldAdmin;
    private JTextField nombreField;
    private JTextField nombreBusquedaField;
    private JButton buscarButton;
    private JButton reporteButton;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTable farmaceutas;

    private Model model;
    private Controller controller;

    public JPanel getPanel() { return panel1; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case sistema.presentation.farmaceutas.Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE};

                farmaceutas.setModel(new TableModel(cols, model.getList()));

                break;
            case sistema.presentation.farmaceutas.Model.CURRENT:
                IdFieldAdmin.setText(model.getCurrent().getId());
                nombreField.setText(model.getCurrent().getNombre());

                IdFieldAdmin.setBackground(null);
                IdFieldAdmin.setToolTipText(null);

                nombreField.setBackground(null);
                nombreField.setToolTipText(null);

                break;
        }
        this.panel1.revalidate();
    }

    public View() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Farmaceuta farmaceuta = take();
                    try {
                        // Si el modelo ya tiene un current con el mismo ID, entonces lo actualiza
                        if (model.getCurrent() != null && !model.getCurrent().getId().isEmpty()
                                && model.getCurrent().getId().equals(farmaceuta.getId())) {
                            controller.update(farmaceuta);
                            JOptionPane.showMessageDialog(panel1, "Farmaceuta actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Si no, se crea
                            controller.create(farmaceuta);
                            JOptionPane.showMessageDialog(panel1, "Farmaceuta registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        limpiarButton.addActionListener(e -> {
            controller.clear();
            nombreBusquedaField.setText("");
        });

        farmaceutas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = farmaceutas.getSelectedRow();
                if (selectedRow >= 0) {
                    Farmaceuta seleccionado = ((TableModel) farmaceutas.getModel()).getRowAt(selectedRow);
                    model.setCurrent(seleccionado);
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrent().getId().isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "No hay farmaceuta seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel1,
                        "¿Está seguro de borrar al farmaceuta " + model.getCurrent().getNombre() + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(model.getCurrent().getId());
                        JOptionPane.showMessageDialog(panel1, "Farmaceuta eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarButton.addActionListener(e -> {
            String textoBusqueda = nombreBusquedaField.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                controller.findFarmaceutaByName(textoBusqueda);

                if (model.getList().isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "No se encontraron farmaceutas con ese nombre.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel1, "Ingrese un texto para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

    }

    // --- Métodos auxiliares ---
    public Farmaceuta take() {
        Farmaceuta f = new Farmaceuta();
        f.setId(IdFieldAdmin.getText());
        f.setNombre(nombreField.getText());
        return f;
    }

    private boolean validateForm() {
        boolean valid = true;
        valid &= validarCampo(IdFieldAdmin, "ID Requerido");
        valid &= validarCampo(nombreField, "Nombre requerido");
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


}
