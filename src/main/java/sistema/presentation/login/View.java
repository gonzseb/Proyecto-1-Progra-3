package sistema.presentation.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View de Login basada en tu GUI form.
 * Ahora extiende JDialog para ser modal.
 */
public class View extends JDialog {

    private JPanel mainPanel;
    private JTextField textFieldUsuario;
    private JPasswordField passwordField;
    private JButton ingresarButton;
    private JButton cancelarButton;
    private JButton cambiarContraseñaButton;

    private Controller controller;

    public View(JFrame parent) {
        super(parent, "Login", true); // true = modal
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        // Listener para botón "Ingresar"
        ingresarButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    controller.login();
                    // Si login exitoso, el controller ya llama dispose()
                } catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        // Listener para botón "Cancelar"
        cancelarButton.addActionListener(e -> {
            textFieldUsuario.setText("");
            passwordField.setText("");
        });

        cambiarContraseñaButton.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            sistema.presentation.popUpCambioClave.View changePasswordDialog =
                    new sistema.presentation.popUpCambioClave.View(parentWindow);
            changePasswordDialog.setVisible(true);

            if (changePasswordDialog.wasPasswordChanged()) {
                // handle it
            }
        });

    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Métodos para obtener los datos ingresados
    public String getUsuario() {
        return textFieldUsuario.getText();
    }

    public String getClave() {
        return new String(passwordField.getPassword());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
    }

    // Methods for adding listeners (used by Controller)
    public void addIngresarListener(ActionListener listener) {
        // Controller will handle this, so we can remove if not needed
    }

    public void addCancelarListener(ActionListener listener) {
        // Controller will handle this, so we can remove if not needed
    }


}