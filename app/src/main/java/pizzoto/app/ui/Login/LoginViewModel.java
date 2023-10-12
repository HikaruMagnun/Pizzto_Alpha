package pizzoto.app.ui.Login;

import android.content.Context;
import android.text.Editable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pizzoto.app.Repository.Local.ClienteLocalRepository;
import pizzoto.app.Repository.Remote.ClienteRepository;
import pizzoto.app.Repository.Remote.PedidoRepository;
import pizzoto.app.models.Cliente;
import pizzoto.app.models.Pedido;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Boolean> getConfirmacion() {
        return confirmacion;
    }

    private MutableLiveData<Boolean> confirmacion = new MutableLiveData<>();
    public void pasarCrendenciales(String correo, String contrasena/*, Context context*/) {


        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ClienteRepository clienteRepository=new ClienteRepository();
                    Boolean boo = clienteRepository.autenticarCliente(correo,contrasena);
                    confirmacion.postValue(boo);
                    Cliente cliente = clienteRepository.obtenerClientePorCorreo(correo);
                    /*ClienteLocalRepository clienteLocalRepository = new ClienteLocalRepository(context);

                    if (boo==true){
                        clienteLocalRepository.insertarCliente(cliente);
                    }else {
                        clienteLocalRepository.eliminarTodosLosClientes();
                    }*/
                }catch (Exception e) {
                    System.out.println("Error inesperado: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });


    }
    // TODO: Implement the ViewModel
}