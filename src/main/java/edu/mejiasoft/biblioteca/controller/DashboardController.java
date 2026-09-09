package main.java.edu.mejiasoft.biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
private void abrirVentana(ActionEvent event) {
    Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    this.stage.abrirModalCrearLibro("Registrar Nuevo Libro", stageActual);
    
    cargarTablaLibros();
}

    public void cargarTablaLibros() {
        Libro libro = new Libro();
        ObservableList<Libro> listaLibros = Libro.obtenerListaLibros();
        tvCatalogoLibros.setItems(listaLibros);
    }
}
