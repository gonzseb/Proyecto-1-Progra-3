package sistema.presentation.popUpCambioClave;

import sistema.logic.Service;
import sistema.presentation.Sesion;
import sistema.logic.entities.Usuario;
import javax.swing.*;
import java.awt.*;

public class View extends JDialog {
    private JPanel mainPanel;
    private JPasswordField passwordFieldClaveActual;
    private JPasswordField passwordFieldClaveNueva;
    private JPasswordField passwordFieldConfirmarClaveNueva;
    private JButton OKButton;
    private JButton cancelarButton;
    private JTextField textFieldUsuarioId;
    private JLabel usuarioID;
    private JLabel claveNueva;
    private JLabel confirmarClaveNueva;
    private JLabel claveActuak;
    private JPanel cambiarClavePanel;

    private boolean passwordChanged = false;

    public View(Window parent) {
        super(parent, "Cambiar Contraseña", ModalityType.APPLICATION_MODAL);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setupEventListeners();
        pack();
        setLocationRelativeTo(parent);
    }


    private void setupEventListeners() {
        OKButton.addActionListener(e -> changePassword());
        cancelarButton.addActionListener(e -> closeDialog());
        passwordFieldClaveActual.addActionListener(e -> changePassword());
        passwordFieldClaveNueva.addActionListener(e -> changePassword());
        passwordFieldConfirmarClaveNueva.addActionListener(e -> changePassword());
    }

    private void changePassword() {
        if (!validateFields()) {
            return;
        }

        try {
            String usuarioId = textFieldUsuarioId.getText().trim();
            String currentPassword = new String(passwordFieldClaveActual.getPassword());
            String newPassword = new String(passwordFieldClaveNueva.getPassword());

            if (usuarioId.isEmpty()) {
                showError("Ingrese el ID de usuario");
                textFieldUsuarioId.requestFocus();
                return;
            }

            Usuario user = Service.instance().read(new Usuario(usuarioId, currentPassword, null));

            if (!user.getClave().equals(currentPassword)) {
                showError("La contraseña actual es incorrecta");
                passwordFieldClaveActual.selectAll();
                passwordFieldClaveActual.requestFocus();
                return;
            }

            user.setClave(newPassword);

            Service.instance().stop();

            passwordChanged = true;
            showMessage("Contraseña cambiada exitosamente");
            closeDialog();

        } catch (Exception e) {
            showError("Error al cambiar la contraseña: " + e.getMessage());
        } finally {
            clearFields();
        }
    }
    private boolean validateFields() {
        String currentPassword = new String(passwordFieldClaveActual.getPassword());
        String newPassword = new String(passwordFieldClaveNueva.getPassword());
        String confirmPassword = new String(passwordFieldConfirmarClaveNueva.getPassword());

        if (currentPassword.isEmpty()) {
            showError("Ingrese la contraseña actual");
            passwordFieldClaveActual.requestFocus();
            return false;
        }

        if (newPassword.isEmpty()) {
            showError("Ingrese la nueva contraseña");
            passwordFieldClaveNueva.requestFocus();
            return false;
        }

        if (newPassword.length() < 3) {
            showError("La nueva contraseña debe tener al menos 3 caracteres");
            passwordFieldClaveNueva.requestFocus();
            return false;
        }

        if (confirmPassword.isEmpty()) {
            showError("Confirme la nueva contraseña");
            passwordFieldConfirmarClaveNueva.requestFocus();
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("Las contraseñas nuevas no coinciden");
            passwordFieldConfirmarClaveNueva.selectAll();
            passwordFieldConfirmarClaveNueva.requestFocus();
            return false;
        }

        if (currentPassword.equals(newPassword)) {
            showError("La nueva contraseña debe ser diferente a la actual");
            passwordFieldClaveNueva.requestFocus();
            return false;
        }

        return true;
    }

    private void clearFields() {
        passwordFieldClaveActual.setText("");
        passwordFieldClaveNueva.setText("");
        passwordFieldConfirmarClaveNueva.setText("");
    }

    private void closeDialog() {
        clearFields();
        passwordChanged = false;
        setVisible(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean wasPasswordChanged() {
        return passwordChanged;
    }
}