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

    @FXML
    public void handleIniciarSesion() {
        String email = (txtEmail != null && txtEmail.getText() != null) ? txtEmail.getText().trim() : "";
        String contrasena = (txtPassword != null && txtPassword.getText() != null) ? txtPassword.getText() : "";

        if (email == null || email.trim().isEmpty()
                || contrasena == null || contrasena.trim().isEmpty()) {
            stage.showInfoAlert("Campos Incompletos", "Datos requeridos", "Por favor ingresa tu usuario/correo y contraseña.", AlertType.WARNING);
            return;
        }

        try {
            Bibliotecario bibliotecarioLogin = new Bibliotecario();
            bibliotecarioLogin.setEmail(email);
            bibliotecarioLogin.setPassword(contrasena);

            String hashGuardado = bibliotecarioLogin.findUserByEmail(bibliotecarioLogin);

            boolean autenticado = (hashGuardado != null) && BCrypt.checkpw(bibliotecarioLogin.getPassword(), hashGuardado);

            if (autenticado) {
                stage.showInfoAlert("Acceso autorizado", "Credenciales correctas", "Usted ha iniciado sesion", AlertType.INFORMATION);
            } else {
                stage.showInfoAlert("Acceso Denegado", "Credenciales incorrectas", "El correo o la contraseña no coinciden.", AlertType.ERROR);
            }
        } catch (Exception e) {
            stage.showInfoAlert("Error de validación", "Datos inválidos", e.getMessage(), AlertType.WARNING);
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
