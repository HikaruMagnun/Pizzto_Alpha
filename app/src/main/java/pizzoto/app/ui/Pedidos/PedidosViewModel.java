package pizzoto.app.ui.Pedidos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pizzoto.app.Repository.Remote.PedidoRepository;
import pizzoto.app.models.Cliente;
import pizzoto.app.models.Empleado;
import pizzoto.app.models.Pedido;
import pizzoto.app.models.PedidoDescripcion;

public class PedidosViewModel extends ViewModel {
    PedidoRepository pedidoRepository;

    public MutableLiveData<Pedido> getPedidoLiveData() {
        return pedidoLiveData;
    }

    private MutableLiveData<Pedido> pedidoLiveData = new MutableLiveData<>();




    public void cargarPedido(){

        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    pedidoRepository = new PedidoRepository();
                    Cliente cliente = new Cliente();
                    cliente.setId(1);
                    Pedido pedido= pedidoRepository.obtenerPedidoMasAltoPorCliente(cliente.getId());
                    pedidoLiveData.postValue(pedido);
                }catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });
    }
}
