package sistema;

import sistema.presentation.medicamentos.Model;
import sistema.presentation.medicamentos.View;
import sistema.presentation.medicamentos.Controller;

import javax.swing.*;
import java.awt.*;

public class AppMedicamentos {

    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);

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
