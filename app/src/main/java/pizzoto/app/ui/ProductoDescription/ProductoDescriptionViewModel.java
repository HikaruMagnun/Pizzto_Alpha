package pizzoto.app.ui.ProductoDescription;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pizzoto.app.Repository.Local.CarritoLocalRepository;
import pizzoto.app.Repository.Remote.ProductoRepository;
import pizzoto.app.models.PedidoDescripcion;
import pizzoto.app.models.Producto;

public class ProductoDescriptionViewModel extends ViewModel {
    private MutableLiveData<Producto> productoRecetaLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Producto>> productosListaLiveData = new MutableLiveData<>();

    private String nombreProducto;
    private int idProducto;

    private Integer confimarInsercionCarrito =0 ;
    private ProductoRepository productoRepository = new ProductoRepository();
    public void setNombreProducto(String nombre) {
        nombreProducto = nombre;
        cargarPruductos(nombreProducto);
    }
    public void cargarPruductos(String nombre){
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    productosListaLiveData.postValue(productoRepository.productosPorNombre(nombreProducto));

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });
    }
    public void setIdProducto(int id) {
        idProducto = id;
        cargarReceta(idProducto);
    }
    public void cargarReceta(int idProducto){
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    productoRecetaLiveData.postValue(productoRepository.obtenerIngredientesPorIdProducto(idProducto));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });
    }

    public LiveData<Producto> getproductoRecetaLiveData() {
        return productoRecetaLiveData;
    }
    public LiveData<List<Producto>> getproductosListaLiveData(){return productosListaLiveData;}




    public void InsertarEnCarrito(Producto productoConfirmado, int cantidad, Context context) {
        PedidoDescripcion nuevoPedidoDescripcion = new PedidoDescripcion();
        nuevoPedidoDescripcion.setProducto(productoConfirmado);
        nuevoPedidoDescripcion.setCantidad(cantidad);

        CarritoLocalRepository productoLocalRepository = new CarritoLocalRepository(context);

        try {
            productoLocalRepository.insertProductos(nuevoPedidoDescripcion);
            confimarInsercionCarrito += 1;

        } catch (Exception e) {
            confimarInsercionCarrito -= 1;
            e.printStackTrace();
        } finally {
            productoLocalRepository.close();
        }
    }

    public void setConfimarInsercionCarrito(Integer confimarInsercionCarrito) {
        this.confimarInsercionCarrito = confimarInsercionCarrito;
    }
}