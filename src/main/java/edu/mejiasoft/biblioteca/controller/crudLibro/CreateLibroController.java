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
import main.java.edu.mejiasoft.biblioteca.model.Libro;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class CreateLibroController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private SceneManager stage;

    @FXML
    private TextField txtIsbn;
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
    private void guardarLibro(ActionEvent event) {
        try {
            if (txtIsbn.getText().trim().isEmpty() || txtTitulo.getText().trim().isEmpty()) {
                stage.showInfoAlert("Campos vacíos", "El ISBN y el Título son obligatorios.", "asdjlk;asdfjklasdf", Alert.AlertType.WARNING);
                return;
            }

            int copias = Integer.parseInt(txtCopias.getText().trim());

            Libro nuevoLibro = new Libro();
            nuevoLibro.setIsbn(txtIsbn.getText().trim());
            nuevoLibro.setTitulo(txtTitulo.getText().trim());
            nuevoLibro.setAutorPrincipal(txtAutorPrincipal.getText().trim());
            nuevoLibro.setEditorial(txtEditorial.getText().trim());
            nuevoLibro.setYearPublicacion(txtYearPublicacion.getText().trim());
            nuevoLibro.setCopiasDisponibles(copias);
            nuevoLibro.setIdBibliotecario("ID_DEL_BIBLIOTECARIO_ACTIVO");

            // 3. Ejecutar la lógica de negocio y validaciones del DAO
            Libro libro = new Libro();
            String resultado = libro.createLibroValidacion(nuevoLibro);

            if (resultado.equals("EXITO")) {
                stage.showInfoAlert("Éxito", "Libro registrado correctamente."," =0 ",Alert.AlertType.INFORMATION);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                stage.showInfoAlert("La verdad no se", "Atención", resultado, Alert.AlertType.WARNING);
            }

        } catch (NumberFormatException e) {
            stage.showInfoAlert("Datos no validos", "Dato inválido", "El campo de copias debe ser un número entero válido.",Alert.AlertType.ERROR);
        }
    }
    
    @FXML
private void regresarAlDashboard(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
}
}
