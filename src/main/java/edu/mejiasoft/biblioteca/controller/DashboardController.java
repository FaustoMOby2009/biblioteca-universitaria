package main.java.edu.mejiasoft.biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.model.Libro;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
    }

    private Bibliotecario bibliotecario;
    private SceneManager stage;

    @FXML
    private TableView<Libro> tvCatalogoLibros;
    @FXML
    private TableColumn<Libro, String> tvColumnIsbn;
    @FXML
    private TableColumn<Libro, String> tvColumnTitulo;
    @FXML
    private TableColumn<Libro, String> tvColumnAutorPrincipal;
    @FXML
    private TableColumn<Libro, String> tvColumnEditorial;
    @FXML
    private TableColumn<Libro, String> tvColumnYearPublicacion;
    @FXML
    private TableColumn<Libro, Integer> tvColumnCopias;
    @FXML
    private TableColumn<Libro, String> tvColumnUserBibliotecario;

    public DashboardController(Bibliotecario bibliotecario, SceneManager stage) {
        this.bibliotecario = bibliotecario;
        this.stage = stage;
    }

    public DashboardController() {
    }

    public void setSceneManager(SceneManager stage) {
    this.stage = stage;
}
    
    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }
    
    private void configurarColumnas() {
        tvColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tvColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tvColumnAutorPrincipal.setCellValueFactory(new PropertyValueFactory<>("autorPrincipal"));
        tvColumnEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        tvColumnYearPublicacion.setCellValueFactory(new PropertyValueFactory<>("yearPublicacion"));
        tvColumnCopias.setCellValueFactory(new PropertyValueFactory<>("copiasDisponibles"));
        tvColumnUserBibliotecario.setCellValueFactory(new PropertyValueFactory<>("usernameBibliotecario"));
        tvCatalogoLibros.setItems(Libro.obtenerListaLibros());
    }

@FXML
private void abrirVentanaCreate(ActionEvent event) {
    Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    System.out.println("Bibliotecario actual en Dashboard: " + (this.bibliotecario != null ? this.bibliotecario.getIdBibliotecario() : "ES NULO"));
    stage.abrirModalCrearLibro(stageActual, this.bibliotecario);
    
    cargarTablaLibros();
}

@FXML
    private void abrirVentanaEditar(ActionEvent event) {
        Libro libroSeleccionado = tvCatalogoLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            stage.showInfoAlert("Selección requerida", "Por favor, selecciona un libro de la tabla para editar.","=0", Alert.AlertType.WARNING);
            return;
        }

        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.abrirModalEditarLibro(stageActual, this.bibliotecario, libroSeleccionado);
        
        cargarTablaLibros();
    }

    public void cargarTablaLibros() {
        Libro libro = new Libro();
        ObservableList<Libro> listaLibros = Libro.obtenerListaLibros();
        tvCatalogoLibros.setItems(listaLibros);
    }
}
