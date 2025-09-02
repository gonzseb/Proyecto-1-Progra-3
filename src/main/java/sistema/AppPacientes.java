package sistema;

import sistema.presentation.admin.Admin;
import sistema.presentation.admin.Controller;
import sistema.presentation.admin.Model;
import sistema.presentation.pacientes.ModelPacientes;
import sistema.presentation.pacientes.Pacientes;
import sistema.presentation.pacientes.ControllerPacientes;

import javax.swing.*;
import java.awt.*;

public class AppPacientes {

    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        Pacientes view = new Pacientes();
        ModelPacientes model = new ModelPacientes();
        ControllerPacientes controller = new ControllerPacientes(view, model);

        JFrame window = new JFrame();
        window.setSize(700,400);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Sistema Médico");
        window.setContentPane(view.getPanel());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);


}
