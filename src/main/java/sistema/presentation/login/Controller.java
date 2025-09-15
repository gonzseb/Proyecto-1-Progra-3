package sistema.presentation.login;

import sistema.logic.Service;
import sistema.logic.entities.Usuario;
import sistema.presentation.Sesion;

public class Controller {

    private final Model model;
    private final View view;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.addIngresarListener(e -> {
            try {
                login();
            } catch (Exception ex) {
                view.mostrarError(ex.getMessage());
            }
        });

        this.view.addCancelarListener(e -> view.dispose());
    }

    public void login() throws Exception {
        String id = view.getUsuario();
        String clave = view.getClave();

        if (id.isEmpty() || clave.isEmpty()) {
            throw new Exception("Usuario y clave requeridos");
        }

        Usuario temp = new Usuario(id, clave, null);
        Usuario logged = Service.instance().read(temp);

        if (!logged.getClave().equals(clave)) {
            throw new Exception("Usuario o clave incorrectos");
        }

        Sesion.setUsuario(logged);
        model.setUsuario(logged);

        view.dispose();
    }
}
