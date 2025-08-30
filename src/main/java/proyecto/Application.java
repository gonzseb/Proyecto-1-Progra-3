package proyecto;

import proyecto.presentation.medicos.Controller;
import proyecto.presentation.medicos.Model;
import proyecto.presentation.medicos.View;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);

        JFrame window = new JFrame();
        window.setSize(600,400);
        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Personas");
        window.setContentPane(view.getPanel());
        window.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}