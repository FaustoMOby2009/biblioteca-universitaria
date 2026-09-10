package main.java.edu.mejiasoft.biblioteca.controller.crudLibro;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.model.Libro;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class UpdateLibroController implements Initializable {
   
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutorPrincipal;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtYearPublicacion;
    @FXML
    private TextField txtCopias;
    @FXML

    private Bibliotecario bibliotecario;
    private SceneManager stage;
    private String isbnActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setSceneManager(SceneManager stege) {
        this.stage = stage;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public void cargarDatosLibro(Libro libro) {
        if (libro != null) {
            this.isbnActual = libro.getIsbn();
            txtTitulo.setText(libro.getTitulo());
            txtAutorPrincipal.setText(libro.getAutorPrincipal());
            txtEditorial.setText(libro.getEditorial());
            txtYearPublicacion.setText(libro.getYearPublicacion());
            txtCopias.setText(String.valueOf(libro.getCopiasDisponibles()));
        }
    }

    @FXML
    private void handleActualizarLibro(ActionEvent event) {
        int copias;
        try {
            copias = Integer.parseInt(txtCopias.getText().trim());
        } catch (NumberFormatException e) {
            stage.showInfoAlert("Error al ingresar los datos", "Las copias deben se un numero entero valido","=0", Alert.AlertType.WARNING);
            return;
        }

        String idBiblio = (this.bibliotecario != null) ? this.bibliotecario.getIdBibliotecario() : null;

        Libro libroEditado = new Libro(
            this.isbnActual,
            idBiblio,
            txtTitulo.getText().trim(),
            txtAutorPrincipal.getText().trim(),
            txtEditorial.getText().trim(),
            txtYearPublicacion.getText().trim(),
            copias,
            null
        );

        String resultado = libroEditado.actualizarLibroValidacion(libroEditado);

        if ("EXITO".equals(resultado)) {
            cerrarVentana(event);
        } else {
           stage.showInfoAlert("Error al ingresar los datos", "Las copias deben se un numero entero valido","=0", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleVolverDashborad(ActionEvent event) {
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
