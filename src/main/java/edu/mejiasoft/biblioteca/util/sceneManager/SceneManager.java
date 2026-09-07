package main.java.edu.mejiasoft.biblioteca.util.sceneManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.edu.mejiasoft.biblioteca.controller.LoginController;
import main.java.edu.mejiasoft.biblioteca.controller.RegistroController;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;

public class SceneManager {

    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // --- 1. MOSTRAR LOGIN ---
    public void mostrarLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));

        loader.setControllerFactory(clazz -> {
            if (clazz == LoginController.class) {
                Bibliotecario usuario = new Bibliotecario();
                return new LoginController(usuario, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        cargarYMostrarEscena(loader, "Inicio de Sesión", 600, 480);
    }

    // --- 2. MOSTRAR REGISTRO ---
    public void mostrarRegistroView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "registro-view.fxml"));

        loader.setControllerFactory(clazz -> {
            if (clazz == RegistroController.class) {
                Bibliotecario usuario = new Bibliotecario();
                return new RegistroController(usuario, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        cargarYMostrarEscena(loader, "Crear Cuenta", 600, 520);
    }

    // --- MÉTODOS AUXILIARES ---
    private void cargarYMostrarEscena(FXMLLoader loader, String titulo, double width, double height) throws Exception {
        Parent root = loader.load();
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showInfoAlert(String title, String head, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(this.primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

