        package main.java.edu.mejiasoft.biblioteca;

        import java.sql.SQLException;
        import javafx.application.Application;
        import javafx.stage.Stage;
        import main.java.edu.mejiasoft.biblioteca.config.DataBaseConnection;
        import main.java.edu.mejiasoft.biblioteca.util.sceneManager.SceneManager;

        public class MainApp extends Application {

            private Stage primaryStage;

            @Override
            public void start(Stage primaryStage) throws Exception {
                this.primaryStage = primaryStage;

                SceneManager sceneManager = new SceneManager(primaryStage);
                sceneManager.mostrarLoginView();
                primaryStage.show();

            }

            public static void main(String[] args) {

                    try {
                    DataBaseConnection.getConnectionDataBase();
                    System.out.println("Conectado!");

                } catch (SQLException e) {
                    System.out.println("Error en la conexión "+ e.getMessage());
                }
                launch();
            }

        }
