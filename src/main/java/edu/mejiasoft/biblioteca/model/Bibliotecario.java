package main.java.edu.mejiasoft.biblioteca.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.edu.mejiasoft.biblioteca.config.DataBaseConnection;
import main.java.edu.mejiasoft.biblioteca.security.jbcrypt.BCrypt;

public class Bibliotecario {

    private String idBibliotecario;
    private String nombre;
    private String username;
    private String password;
    private String email;

    public Bibliotecario() {
    }

    public Bibliotecario(String idBibliotecario, String nombre, String username, String password, String email) {
        this.idBibliotecario = idBibliotecario;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getIdBibliotecario() {
        return idBibliotecario;
    }

    public void setIdBibliotecario(String idBibliotecario) {
        this.idBibliotecario = idBibliotecario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- LÓGICA DE REGISTRO ---
    public boolean registrar(Bibliotecario bibliotecario) {
        String contrasenaHash = BCrypt.hashpw(bibliotecario.getPassword(), BCrypt.gensalt(12));

        String sql = "INSERT INTO Bibliotecarios (nombre, username, password_hash, email) VALUES (?,?,?,?)";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, bibliotecario.getNombre());
            pstm.setString(2, bibliotecario.getUsername());
            pstm.setString(3, contrasenaHash);
            pstm.setString(4, bibliotecario.getEmail());

            int filas = pstm.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar bibliotecario en BD: " + e.getMessage());
            return false;
        }
    }

    // --- LÓGICA DE LOGIN ---
    public String findUserByEmail(Bibliotecario bibliotecario) {
        String sql = "select password_hash from bibliotecarios where email = ?";

        try (Connection conn = DataBaseConnection.getConnectionDataBase(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setString(1, bibliotecario.getEmail());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("password_hash");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
