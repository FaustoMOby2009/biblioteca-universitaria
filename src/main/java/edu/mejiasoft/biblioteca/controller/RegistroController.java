package main.java.edu.mejiasoft.biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class RegistroController implements Initializable {

    private Bibliotecario bibliotecario;
    private SceneManager stage;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldUsername;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldPass;

    public RegistroController() {
    }

    public RegistroController(Bibliotecario bibliotecario, SceneManager stage) {
        this.bibliotecario = bibliotecario;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void handleMostrarLogin() {
        try {
            stage.mostrarLoginView();
        } catch (Exception e) {
            stage.showInfoAlert("Error", "No se pudo cambiar de ventana", "Detalle: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleRegistrarBibliotecario() {
        String nombre = txtFieldNombre != null ? txtFieldNombre.getText() : "";
        String username = txtFieldUsername != null ? txtFieldUsername.getText() : "";
        String password = txtFieldPass != null ? txtFieldPass.getText() : "";
        String email = txtFieldEmail != null ? txtFieldEmail.getText() : "";

        if (nombre == null || nombre.trim().isEmpty()
           || username == null || username.trim().isEmpty()
           || email == null || email.trim().isEmpty()
           || password == null || password.trim().isEmpty()) {

            stage.showInfoAlert("Campos Vacíos", "Por favor completa todos los campos", "No se permiten campos vacíos o solo con espacios.", AlertType.WARNING);
            return;
        }

        try {
            Bibliotecario nuevoBibliotecario = new Bibliotecario();
            nuevoBibliotecario.setNombre(nombre);
            nuevoBibliotecario.setUsername(username);
            nuevoBibliotecario.setPassword(password);
            nuevoBibliotecario.setEmail(email);
            Bibliotecario bibliotecario = new Bibliotecario(); 
            boolean registrado = bibliotecario.registrar(nuevoBibliotecario);

            if (registrado) {
                stage.showInfoAlert("Éxito", "Registro completado", "¡La cuenta ha sido creada!", AlertType.INFORMATION);
                stage.mostrarLoginView();
            } else {
                stage.showInfoAlert("Error", "No se pudo registrar", "Verifica si el usuario o correo ya existe.", AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            stage.showInfoAlert("Error", "Error interno", "Ocurrió un error: " + e.getMessage(), AlertType.ERROR);
        }
    }
}

