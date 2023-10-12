package pizzoto.app.Repository.Remote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pizzoto.app.Repository.Remote.Conexion.Conexion;
import pizzoto.app.models.Cliente;

public class ClienteRepository {
    Connection connection;

    public ClienteRepository() {
        connection= Conexion.conectar();

    }
    public boolean autenticarCliente(String correo, String contraseña) {
        String sql = "SELECT id FROM public.\"Cliente\" WHERE correo = ? AND contraseña = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, contraseña);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Cliente obtenerClientePorCorreo(String correo) {
        Cliente cliente = new Cliente();

        String sql = "SELECT * FROM public.\"Cliente\" WHERE correo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, correo);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setCorreo(resultSet.getString("correo"));
                cliente.setContrasena(resultSet.getString("contraseña"));
                cliente.setApellidos(resultSet.getString("apellidos"));
            }
        } catch (SQLException e) {
            // Manejar excepciones
            e.printStackTrace();
        }

        return cliente;
    }

}
