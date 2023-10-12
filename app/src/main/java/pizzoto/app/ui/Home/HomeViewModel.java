package pizzoto.app.ui.Home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pizzoto.app.Repository.Local.ClienteLocalRepository;
import pizzoto.app.models.Cliente;
import pizzoto.app.models.Pedido;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public void cargarDatos(Context context){
        //recogo de nombre del cliente
        /*
        ClienteLocalRepository clienteLocalRepository= new ClienteLocalRepository(context);
        Cliente cliente = clienteLocalRepository.obtenerPrimerCliente();
        String nombre;
        if(cliente.getNombre() == null|| cliente.getNombre().isEmpty()||cliente.getApellidos()==null||cliente.getApellidos().isEmpty()){
            nombre = "JHON DOE";
        }else {
            nombre = cliente.getNombre() + " " + cliente.getApellidos();
        }
             */
        String nombre = "JHON DOE";

        stringMutableLiveData.postValue(nombre);
    }

}
