package pizzoto.app.ui.Menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pizzoto.app.Repository.Remote.ProductoRepository;
import pizzoto.app.models.Producto;

public class MenuViewModel extends ViewModel {
    private List<Producto> listaProducto;
    private MutableLiveData<List<Producto>> listaProductoLiveData = new MutableLiveData<>();

    private ProductoRepository productoRepository;
    public MenuViewModel() {
        listaProducto = null;
        listaProducto = new ArrayList<>();

        cargarProductos();
    }

    private void cargarProductos() {
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    productoRepository = new ProductoRepository();
                    listaProducto = productoRepository.LsitarProductosUnicos();
                    listaProductoLiveData.postValue(listaProducto);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });

    }
    public LiveData<List<Producto>> getListaProductosLiveData() {
        return listaProductoLiveData;
    }
}
