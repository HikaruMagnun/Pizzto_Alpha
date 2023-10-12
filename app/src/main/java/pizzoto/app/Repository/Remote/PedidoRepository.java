package pizzoto.app.Repository.Remote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pizzoto.app.Repository.Remote.Conexion.Conexion;
import pizzoto.app.models.Pedido;
import pizzoto.app.models.PedidoDescripcion;

public class PedidoRepository {
    Connection connection;

    public PedidoRepository() {
        connection= Conexion.conectar();

    }
    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO public.\"Pedido\" (fecha, hora_entrega, tipo, estado, total_pagar, id_cliente, id_empleado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) returning id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, pedido.getFecha());
            preparedStatement.setTime(2, pedido.getHoraEntrega());
            preparedStatement.setString(3, pedido.getTipo());
            preparedStatement.setString(4, pedido.getEstado());
            preparedStatement.setDouble(5, pedido.getTotalPagar());
            preparedStatement.setLong(6, pedido.getCliente().getId());
            preparedStatement.setLong(7, pedido.getEmpleado().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int pedidoId = resultSet.getInt(1);

                for (PedidoDescripcion pedidoDescription : pedido.getPedidoListaDescripton()) {
                    insertarPedidoDescription(pedidoId, pedidoDescription);
                }

                System.out.println("Pedido y PedidoDescription insertados correctamente.");
            } else {
                System.out.println("Error al insertar el pedido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error SQL: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Vendor Error Code: " + e.getErrorCode());
        }

    }
    private void insertarPedidoDescription(int pedidoId, PedidoDescripcion pedidoDescription) {
        // Sentencia SQL para la inserción de PedidoDescription
        String sqlPedidoDescription = "INSERT INTO \"Pedido_decripcion\" (id_pedido, id_producto, cantidad) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatementPedidoDescription = connection.prepareStatement(sqlPedidoDescription)) {
            // Establecer los valores de los parámetros para PedidoDescription
            preparedStatementPedidoDescription.setInt(1,pedidoId);
            preparedStatementPedidoDescription.setInt(2,pedidoDescription.getProducto().getId());
            preparedStatementPedidoDescription.setInt(3,pedidoDescription.getCantidad());

            // Ejecutar la inserción de PedidoDescription
            int filasInsertadas = preparedStatementPedidoDescription.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("PedidoDescription insertado correctamente.");
            } else {
                System.out.println("Error al insertar el PedidoDescription.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error SQL al insertar PedidoDescription: " + e.getMessage());
        }
    }

    public Pedido obtenerPedidoMasAltoPorCliente(int idCliente) {
        String sql = "SELECT * FROM public.\"Pedido\" WHERE id_cliente = ? ORDER BY id DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idCliente);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Mapea los valores del resultado a un objeto Pedido
                    Pedido pedido = new Pedido();
                    pedido.setId(resultSet.getInt("id"));
                    pedido.setFecha(resultSet.getDate("fecha"));
                    pedido.setHoraEntrega(resultSet.getTime("hora_entrega"));
                    pedido.setTipo(resultSet.getString("tipo"));
                    pedido.setEstado(resultSet.getString("estado"));
                    pedido.setTotalPagar(resultSet.getDouble("total_pagar"));


                    return pedido;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error SQL: " + e.getMessage());
        }

        return null; // Retorna null si no se encuentra ningún pedido
    }





}
