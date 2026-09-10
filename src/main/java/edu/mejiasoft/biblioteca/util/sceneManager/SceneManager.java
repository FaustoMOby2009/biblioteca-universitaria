package main.java.edu.mejiasoft.biblioteca.util.sceneManager;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.edu.mejiasoft.biblioteca.controller.DashboardController;
import main.java.edu.mejiasoft.biblioteca.controller.LoginController;
import main.java.edu.mejiasoft.biblioteca.controller.RegistroController;
import main.java.edu.mejiasoft.biblioteca.controller.crudLibro.CreateLibroController;
import main.java.edu.mejiasoft.biblioteca.controller.crudLibro.UpdateLibroController;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.model.Libro;

public class SceneManager {

    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";
    private Bibliotecario bibliotecario;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
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

    public void mostrarDashboardView(Bibliotecario bibliotecario) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard-view.fxml"));

        loader.setControllerFactory(clazz -> {
            if (clazz == DashboardController.class) {
                return new DashboardController(bibliotecario, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        cargarYMostrarEscena(loader, "Dashboard - Biblioteca", 1000, 700);
    }

    public void abrirModalCrearLibro(Stage ventanaActual, Bibliotecario bibliotecario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "crudLibro/create-libro-view.fxml"));
            Parent root = loader.load();

            CreateLibroController controller = loader.getController();
            controller.setSceneManager(this);
            controller.setBibliotecario(bibliotecario);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);

            if (ventanaActual != null) {
                modalStage.initOwner(ventanaActual);
            }

            modalStage.setScene(new Scene(root));
            modalStage.setResizable(false);
            modalStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void abrirModalEditarLibro( Stage parentStage, Bibliotecario bibliotecario, Libro libroSeleccionado) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "crudLibro/update-libro-view.fxml"));
        Parent root = loader.load();

        UpdateLibroController controller = loader.getController();
        controller.setSceneManager(this);
        controller.setBibliotecario(bibliotecario);
        controller.cargarDatosLibro(libroSeleccionado);

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.initOwner(parentStage);
        modalStage.setScene(new Scene(root));
        modalStage.showAndWait();

    } catch (Exception e) {
        System.out.println("Error al abrir el modal de edición: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    public boolean showConfirmationAlert(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}
}
