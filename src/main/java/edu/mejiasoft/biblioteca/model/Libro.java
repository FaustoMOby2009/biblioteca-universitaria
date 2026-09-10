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

// Metodo para la consulta SQL del create
    public boolean createLibro(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, autor_principal, editorial, year_publicacion, copias_disponibles, id_bibliotecario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnectionDataBase();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, libro.getIsbn());
            pstmt.setString(2, libro.getTitulo());
            pstmt.setString(3, libro.getAutorPrincipal());
            pstmt.setString(4, libro.getEditorial());
            pstmt.setString(5, libro.getYearPublicacion());
            pstmt.setInt(6, libro.getCopiasDisponibles());
            pstmt.setString(7, libro.getIdBibliotecario());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeIsbn(Libro libro) {
        String sql = "SELECT COUNT(*) FROM libros WHERE isbn = ?";

        try (Connection conn = DataBaseConnection.getConnectionDataBase();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, libro.getIsbn().trim());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Logica de negocio del create libro
    public String createLibroValidacion(Libro libro) {
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()
                || libro.getIdBibliotecario() == null || libro.getIdBibliotecario().trim().isEmpty()
                || libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()
                || libro.getAutorPrincipal() == null || libro.getAutorPrincipal().trim().isEmpty()
                || libro.getEditorial() == null || libro.getEditorial().trim().isEmpty()
                || libro.getYearPublicacion() == null || libro.getYearPublicacion().trim().isEmpty()) {
            return "campos son OBLIGATORIOS.";
        }

        if (existeIsbn(libro)) {
            return "El ISBN '" + libro.getIsbn().trim() + "' ya está registrado en el sistema.";
        }

        if (libro.getCopiasDisponibles() < 0) {
            return "Las copias disponibles no pueden ser un valor negativo.";
        }

        boolean exito = createLibro(libro);

        return exito ? "EXITO" : "Error crítico al guardar en la base de datos.";
    }

// Consulta a base de datos para el update
    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor_principal = ?, editorial = ?, year_publicacion = ?, copias_disponibles = ?, id_bibliotecario = ? WHERE isbn = ?";

        try (Connection conn = DataBaseConnection.getConnectionDataBase();
                PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, libro.getTitulo());
            pstm.setString(2, libro.getAutorPrincipal());
            pstm.setString(3, libro.getEditorial());
            pstm.setString(4, libro.getYearPublicacion());
            pstm.setInt(5, libro.getCopiasDisponibles());
            pstm.setString(6, libro.getIdBibliotecario());
            pstm.setString(7, libro.getIsbn());

            int filasAfectadas = pstm.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
            return false;
        }
    }

// Logica de negocio del update libro
    public String actualizarLibroValidacion(Libro libro) {
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()
                || libro.getIdBibliotecario() == null || libro.getIdBibliotecario().trim().isEmpty()
                || libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()
                || libro.getAutorPrincipal() == null || libro.getAutorPrincipal().trim().isEmpty()
                || libro.getEditorial() == null || libro.getEditorial().trim().isEmpty()
                || libro.getYearPublicacion() == null || libro.getYearPublicacion().trim().isEmpty()) {
            return "Todos los campos son OBLIGATORIOS.";
        }

        if (libro.getCopiasDisponibles() < 0) {
            return "Las copias disponibles no pueden ser un valor negativo.";
        }

        boolean exito = actualizarLibro(libro);

        return exito ? "EXITO" : "Error al actualizar en la base de datos. Verifica que el ISBN exista.";
    }
    
// Consulta a base de datos para el delete
    public boolean eliminarLibro(Libro libro) {
        String sql = "DELETE FROM libros WHERE isbn = ?";

        try (Connection conn = DataBaseConnection.getConnectionDataBase();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getIsbn());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el libro con ISBN " + libro.getIsbn() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

// Logica de negocio del delete libro
    public String eliminarLibroValidacion(Libro libro) {
        if (libro == null || libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            return "Debe seleccionar un libro válido para eliminar.";
        }

        boolean exito = eliminarLibro(libro);

        return exito ? "EXITO" : "No se pudo eliminar el libro. Verifica que no tenga préstamos o registros asociados en el sistema.";
    }
}
