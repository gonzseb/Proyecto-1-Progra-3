package sistema.presentation.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

        ingresarButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    controller.login();
                } catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

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

    public void addIngresarListener(ActionListener listener) {
    }

    public void addCancelarListener(ActionListener listener) {
    }


}