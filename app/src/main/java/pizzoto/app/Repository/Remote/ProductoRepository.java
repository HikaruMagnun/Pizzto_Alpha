package pizzoto.app.Repository.Remote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pizzoto.app.Repository.Remote.Conexion.Conexion;
import pizzoto.app.models.Ingrediente;
import pizzoto.app.models.Producto;

public class ProductoRepository {
    Connection connection;

    public ProductoRepository() {
        connection= Conexion.conectar();

    }

    public List<Producto> ListarProducto(){
        List<Producto> listaProducto = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from \"Producto\";");
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String tamano = resultSet.getString("tamaño");
                    int tiempoPreparacion = resultSet.getInt("tiempo_preparacion");
                    double precio = resultSet.getDouble("precio");
                    String descripcion = resultSet.getString("descripcion");

                    Producto producto = new Producto(id, nombre, tamano, tiempoPreparacion, precio, descripcion);
                    listaProducto.add(producto);
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaProducto;



    }
    public List<Producto> LsitarProductosUnicos(){
        List<Producto> listaProducto = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT ON (nombre) * FROM \"Producto\" ORDER BY nombre, id; ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre( resultSet.getString("nombre"));
                producto.setPrecio(resultSet.getDouble("precio"))  ;
                producto.setDescripcion(resultSet.getString("descripcion"));
                listaProducto.add(producto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaProducto;

    }

    public List<Producto> productosPorNombre(String nombre){
        List<Producto> listaProducto = new ArrayList<>();
        try {
            String sql = ("select * from \"Producto\" where nombre = ?;");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setTamano(resultSet.getString("tamaño"));
                producto.setTiempo_preparacion(resultSet.getInt("tiempo_preparacion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                listaProducto.add(producto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaProducto;

    }


    public Producto obtenerIngredientesPorIdProducto(int idproducto) {
        Producto receta = new Producto();
        List<Ingrediente> listaIngrediente = new ArrayList<>();
        try{
            String sql = "SELECT I.id, I.nombre " +
                    "FROM \"Producto\" P " +
                    "JOIN \"Producto_receta\" R ON P.id = R.id_producto " +
                    "JOIN \"Ingrediente\" I ON I.id = R.id_ingrediente " +
                    "WHERE P.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idproducto);
            ResultSet resultSet = preparedStatement.executeQuery();
            listaIngrediente= receta.getListaIngrediente();
            while (resultSet.next()) {
                int idIngrediente = resultSet.getInt("id");
                String nombreIngrediente = resultSet.getString("nombre");
                Ingrediente ingrediente = new Ingrediente(idIngrediente,nombreIngrediente);
                receta.getListaIngrediente().add(ingrediente);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receta;


    }
    public Producto getProductoPorId(int id) {
        Producto producto = new Producto();
        try {
            connection = Conexion.conectar();

            String query = "SELECT * FROM \"Producto\" WHERE id = ?";
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                producto = new Producto();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setTamano(resultSet.getString("tamaño"));
                producto.setTiempo_preparacion(resultSet.getInt("tiempo_preparacion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setDescripcion(resultSet.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return producto;
    }


}