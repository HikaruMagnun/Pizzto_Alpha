package pizzoto.app.Repository.Remote.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection connection = null;

    public static Connection conectar() {
        if (connection != null) {
            return connection;
        }

        String url = "jdbc:postgresql://db.fnnfbbhmjovfhdrbbgdw.supabase.co:5432/postgres";
        String usuario = "postgres";
        String contrasena = "PIZZOTOAPP2023";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador de PostgreSQL: " + e.getMessage());
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        return connection;
    }

    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
