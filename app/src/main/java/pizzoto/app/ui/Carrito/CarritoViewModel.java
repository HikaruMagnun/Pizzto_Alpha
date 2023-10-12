package pizzoto.app.ui.Carrito;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pizzoto.app.Repository.Local.CarritoLocalRepository;
import pizzoto.app.Repository.Remote.PedidoRepository;
import pizzoto.app.Repository.Remote.ProductoRepository;
import pizzoto.app.models.Cliente;
import pizzoto.app.models.Empleado;
import pizzoto.app.models.Pedido;
import pizzoto.app.models.PedidoDescripcion;
import pizzoto.app.models.Producto;

public class CarritoViewModel extends ViewModel {
    List<PedidoDescripcion> listaPedido = new ArrayList<>();
    private MutableLiveData<List<PedidoDescripcion>> pedidoDescriptionListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> compraConfirmacion = new MutableLiveData<>();
    Integer integerCompraConfirmacion= 0;
    ProductoRepository productoRepository;
    PedidoRepository pedidoRepository;
    CarritoLocalRepository carritoLocalRepository ;

    public CarritoViewModel() {


    }


    public void pasarContext(View view){
        carritoLocalRepository = new CarritoLocalRepository(view.getContext());
        listaPedido = carritoLocalRepository.getPedidoDescriptions();
        cargarPedidoDescriptions(listaPedido);
    }



    public void cargarPedidoDescriptions(List<PedidoDescripcion> listarPedido) {
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    productoRepository = new ProductoRepository();
                    List<PedidoDescripcion> listaPed = new ArrayList<>();
                    for (PedidoDescripcion pedidoDescripcion : listarPedido) {
                        Producto producto = pedidoDescripcion.getProducto();
                        producto = productoRepository.getProductoPorId(producto.getId());
                        pedidoDescripcion.setProducto(producto);
                        listaPed.add(pedidoDescripcion);
                    }
                    pedidoDescriptionListMutableLiveData.postValue(listaPed);
                } catch (NullPointerException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void realizarCambiosEnLaLista(List<PedidoDescripcion> nuevaLista) {
        listaPedido = nuevaLista;
        pedidoDescriptionListMutableLiveData.postValue(nuevaLista);
    }
    public void comprar(){
        pedidoRepository = new PedidoRepository();
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Pedido pedido= new Pedido();
                    pedido.setFecha(new Date(System.currentTimeMillis())) ;
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, 30);
                    pedido.setHoraEntrega( new Time(calendar.getTimeInMillis()));
                    pedido.setEstado("espera");
                    double total = 0.0;
                    for (PedidoDescripcion pedi:listaPedido) {
                        total += (pedi.getProducto().getPrecio()*pedi.getCantidad());
                    }
                    pedido.setTotalPagar(total);
                    Cliente cliente = new Cliente();
                    cliente.setId(1);
                    pedido.setCliente(cliente);
                    pedido.setEmpleado(new Empleado());
                    pedido.setPedidoListaDescripton(listaPedido);
                    pedidoRepository.insertarPedido(pedido);
                    carritoLocalRepository.borrarRegistros();
                    integerCompraConfirmacion ++;
                    compraConfirmacion.postValue(integerCompraConfirmacion);
                }catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });
    }

    public LiveData<Integer> getIntegerMIntegerLiveData(){
        return compraConfirmacion;
    }
    public LiveData<List<PedidoDescripcion>> getpedidoDescriptionListMutableLiveData() {
        return pedidoDescriptionListMutableLiveData;
    }
}