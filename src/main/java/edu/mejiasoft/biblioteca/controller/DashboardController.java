package main.java.edu.mejiasoft.biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.java.edu.mejiasoft.biblioteca.model.Bibliotecario;
import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    private Bibliotecario bibliotecario;
    private SceneManager stage;
    
    public DashboardController(Bibliotecario bibliotecario, SceneManager stage) {
    this.bibliotecario = bibliotecario;
    this.stage = stage;
}
}
