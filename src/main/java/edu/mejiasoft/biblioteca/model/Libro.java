package main.java.edu.mejiasoft.biblioteca.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.mejiasoft.biblioteca.config.DataBaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Libro {

    private String isbn;
    private String idBibliotecario;
    private String titulo;
    private String autorPrincipal;
    private String editorial;
    private String yearPublicacion;
    private int copiasDisponibles;

    private String usernameBibliotecario;

    public Libro() {
    }

    public Libro(String isbn, String idBibliotecario, String titulo, String autorPrincipal, String editorial, String yearPublicacion, int copiasDisponibles, String usernameBibliotecario) {
        this.isbn = isbn;
        this.idBibliotecario = idBibliotecario;
        this.titulo = titulo;
        this.autorPrincipal = autorPrincipal;
        this.editorial = editorial;
        this.yearPublicacion = yearPublicacion;
        this.copiasDisponibles = copiasDisponibles;
        this.usernameBibliotecario = usernameBibliotecario;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdBibliotecario() {
        return idBibliotecario;
    }

    public void setIdBibliotecario(String idBibliotecario) {
        this.idBibliotecario = idBibliotecario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutorPrincipal() {
        return autorPrincipal;
    }

    public void setAutorPrincipal(String autorPrincipal) {
        this.autorPrincipal = autorPrincipal;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getYearPublicacion() {
        return yearPublicacion;
    }

    public void setYearPublicacion(String yearPublicacion) {
        this.yearPublicacion = yearPublicacion;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public String getUsernameBibliotecario() {
        return usernameBibliotecario;
    }

    public void setUsernameBibliotecario(String usernameBibliotecario) {
        this.usernameBibliotecario = usernameBibliotecario;
    }

// Consultas a la base de datos
//Metodo para la tabla
public static ObservableList<Libro> obtenerListaLibros() {
        ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
        String sql = "SELECT l.isbn, l.titulo, l.autor_principal, l.editorial, l.year_publicacion, l.copias_disponibles, b.username AS username_bibliotecario "
                + "FROM libros l LEFT JOIN bibliotecarios b ON l.id_bibliotecario = b.id_bibliotecario";

        try (Connection conn = DataBaseConnection.getConnectionDataBase(); 
             PreparedStatement pstm = conn.prepareStatement(sql); 
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutorPrincipal(rs.getString("autor_principal"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setYearPublicacion(rs.getString("year_publicacion"));
                libro.setCopiasDisponibles(rs.getInt("copias_disponibles"));
                libro.setUsernameBibliotecario(rs.getString("username_bibliotecario"));

                listaLibros.add(libro);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener los libros: " + e.getMessage());
        }

        return listaLibros;
    }
}
