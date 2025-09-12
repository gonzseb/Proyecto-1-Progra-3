package sistema;

import sistema.logic.Service;
import sistema.presentation.login.View;
import sistema.presentation.login.Model;
import sistema.presentation.login.Controller;
import sistema.presentation.Sesion;
import sistema.logic.entities.enums.UsuarioRol;

import javax.swing.*;
import java.awt.*;

public class ApplicationLogIn {

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Saving data before exit...");
            Service.instance().stop();
            System.out.println("Data saved successfully!");
        }));


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Mostrar ventana de login
        doLogin();

        // Si el login fue exitoso, abrir la aplicación principal
        if (Sesion.isLoggedIn()) {
            doRun();
        }
    }

    private static void doLogin() {
        // Crear vista, modelo y controlador
        View loginView = new View(null); // Pass null as parent
        Model loginModel = new Model();
        Controller loginController = new Controller(loginView, loginModel);

        loginView.setController(loginController);
        loginView.setVisible(true); // Show the modal dialog
    }

    private static void doRun() {
        JFrame window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Instanciar todas las vistas y modelos ---
        // Médicos
        sistema.presentation.medicos.Model medicosModel = new sistema.presentation.medicos.Model();
        sistema.presentation.medicos.View medicosView = new sistema.presentation.medicos.View();
        sistema.presentation.medicos.Controller medicosController = new sistema.presentation.medicos.Controller(medicosView, medicosModel);
        medicosView.setModel(medicosModel);
        medicosView.setController(medicosController);

        // Farmaceutas
        sistema.presentation.farmaceutas.Model farmaceutasModel = new sistema.presentation.farmaceutas.Model();
        sistema.presentation.farmaceutas.View farmaceutasView = new sistema.presentation.farmaceutas.View();
        sistema.presentation.farmaceutas.Controller farmaceutasController = new sistema.presentation.farmaceutas.Controller(farmaceutasView, farmaceutasModel);
        farmaceutasView.setModel(farmaceutasModel);
        farmaceutasView.setController(farmaceutasController);

        // Pacientes
        sistema.presentation.pacientes.Model pacientesModel = new sistema.presentation.pacientes.Model();
        sistema.presentation.pacientes.View pacientesView = new sistema.presentation.pacientes.View();
        sistema.presentation.pacientes.Controller pacientesController = new sistema.presentation.pacientes.Controller(pacientesView, pacientesModel);
        pacientesView.setModel(pacientesModel);
        pacientesView.setController(pacientesController);

        // Medicamentos
        sistema.presentation.medicamentos.Model medicamentosModel = new sistema.presentation.medicamentos.Model();
        sistema.presentation.medicamentos.View medicamentosView = new sistema.presentation.medicamentos.View();
        sistema.presentation.medicamentos.Controller medicamentosController = new sistema.presentation.medicamentos.Controller(medicamentosView, medicamentosModel);
        medicamentosView.setModel(medicamentosModel);
        medicamentosView.setController(medicamentosController);

        // Prescribir
        sistema.presentation.prescribir.Model prescribirModel = new sistema.presentation.prescribir.Model();
        sistema.presentation.prescribir.View prescribirView = new sistema.presentation.prescribir.View();
        sistema.presentation.prescribir.Controller prescribirController = new sistema.presentation.prescribir.Controller(prescribirView, prescribirModel);
        prescribirView.setModel(prescribirModel);
        prescribirView.setController(prescribirController);
/*
        // Dashboard
        sistema.presentation.dashboard.View dashboardView = new sistema.presentation.dashboard.View();
        sistema.presentation.dashboard.Controller dashboardController = new sistema.presentation.dashboard.Controller(dashboardView, dashboardModel);
        dashboardView.setModel(dashboardModel);
        dashboardView.setController(dashboardController);
*/
        //Historico
        sistema.presentation.historico.Model historicoModel = new sistema.presentation.historico.Model();
        sistema.presentation.historico.View historicoView = new sistema.presentation.historico.View();
        sistema.presentation.historico.Controller historicoController = new sistema.presentation.historico.Controller(historicoView, historicoModel);
        historicoView.setModel(historicoModel);
        historicoView.setController(historicoController);

        // Despacho Recetas
        sistema.presentation.despachoRecetas.Model despachoModel = new sistema.presentation.despachoRecetas.Model();
        sistema.presentation.despachoRecetas.View despachoView = new sistema.presentation.despachoRecetas.View();
        sistema.presentation.despachoRecetas.Controller despachoController = new sistema.presentation.despachoRecetas.Controller(despachoView, despachoModel);

        // Acerca de
        sistema.presentation.acercade.View acercadeView = new sistema.presentation.acercade.View();




        // --- Agregar al tabbed pane según rol ---
        switch (Sesion.getUsuario().getRol()) {
            case ADMIN:
                tabbedPane.addTab("Médicos", medicosView.getPanel());
                tabbedPane.addTab("Farmaceutas", farmaceutasView.getPanel());
                tabbedPane.addTab("Pacientes", pacientesView.getPanel());
                tabbedPane.addTab("Medicamentos", medicamentosView.getPanel());
                //tabbedPane.addTab("Dashboard", dashboardView.getPanel());
                tabbedPane.addTab("Histórico", historicoView.getPanel());
                tabbedPane.addTab("Acerca de", acercadeView.getMainPanel());
                break;

            case MEDICO:
                tabbedPane.addTab("Prescribir", prescribirView.getPanel());
                //tabbedPane.addTab("Dashboard", dashboardView.getPanel());
                tabbedPane.addTab("Histórico", historicoView.getPanel());
                tabbedPane.addTab("Acerca de", acercadeView.getMainPanel());
                break;

            case FARMACEUTA: //por terminar
                tabbedPane.addTab("Histórico", historicoView.getPanel());
                tabbedPane.addTab("Despacho", despachoView.getPanel());
                //tabbedPane.addTab("Dashboard", dashboardView.getPanel());
                tabbedPane.addTab("Acerca de", acercadeView.getMainPanel());
                break;
        }

        window.setContentPane(tabbedPane);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Recetas - " + Sesion.getUsuario().getId() + " (" + Sesion.getUsuario().getRol() + ")");
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        }
}
