package sistema.presentation.login;

import javax.swing.*;
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

        // Listener para botón "Cambiar Contraseña"
        cambiarContraseñaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Función no implementada");
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