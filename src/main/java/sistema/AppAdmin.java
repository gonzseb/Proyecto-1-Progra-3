package sistema;

import sistema.presentation.admin.Model;
import sistema.presentation.admin.Admin;
import sistema.presentation.admin.Controller;

import javax.swing.*;
import java.awt.*;

public class AppAdmin {
    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        Admin view = new Admin();
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
