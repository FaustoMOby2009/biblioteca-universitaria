package main.java.edu.mejiasoft.biblioteca.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.security.jbcrypt.BCrypt;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class LoginController {

    private Bibliotecario bibliotecario;
    private SceneManager stage;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    public LoginController(Bibliotecario bibliotecario, SceneManager stage) {
        this.bibliotecario = bibliotecario;
        this.stage = stage;
    }

    public LoginController() {
    }

    public void setSceneManager(SceneManager stage) {
        this.stage = stage;
    }

    @FXML
    public void handleIniciarSesion() {
        if (stage == null) {
            System.out.println("Error crítico: SceneManager no ha sido inyectado en LoginController.");
            return;
        }

        String email = (txtEmail != null && txtEmail.getText() != null) ? txtEmail.getText().trim() : "";
        String contrasena = (txtPassword != null && txtPassword.getText() != null) ? txtPassword.getText() : "";

        if (email == null || email.trim().isEmpty()
                || contrasena == null || contrasena.trim().isEmpty()) {
            stage.showInfoAlert("Campos Incompletos", "Datos requeridos", "Por favor ingresa tu usuario/correo y contraseña.", AlertType.WARNING);
            return;
        }

        try {
            Bibliotecario bibliotecario = new Bibliotecario();
            bibliotecario.setEmail(email);
            bibliotecario.setPassword(contrasena);

            String hashGuardado = bibliotecario.findUserByEmail(bibliotecario);

            boolean autenticado = (hashGuardado != null) && BCrypt.checkpw(bibliotecario.getPassword(), hashGuardado);

            if (autenticado) {
                System.out.println("ID cargado correctamente: " + bibliotecario.getIdBibliotecario());
                System.out.println("ID del Bibliotecario antes de entrar al Dashboard: " + bibliotecario.getIdBibliotecario());
                stage.mostrarDashboardView(bibliotecario);
            } else {
                stage.showInfoAlert("Acceso Denegado", "Credenciales incorrectas", "El correo o la contraseña no coinciden.", AlertType.ERROR);
            }
        } catch (Exception e) {
            stage.showInfoAlert("Error de validación", "Datos inválidos", "=0", AlertType.WARNING);
        }
    }

    @FXML
    public void handleMostrarRegistroView() {
        try {
            stage.mostrarRegistroView();
        } catch (Exception e) {
            e.printStackTrace();
            stage.showInfoAlert("Error", "No se pudo cambiar de ventana", "Detalle: " + e.getMessage(), AlertType.ERROR);
        }
    }
}
