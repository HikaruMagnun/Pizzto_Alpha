package pizzoto.app.ui.Pedidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pizzoto.app.R;
import pizzoto.app.models.Pedido;


public class PedidosFragment extends Fragment {

    PedidosViewModel pedidosViewModel;
    TextView textViewID ;
    TextView textViewHora;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
         pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        TextView textViewID = view.findViewById(R.id.ID);
        TextView textViewHora = view.findViewById(R.id.fragment_pedidos_id);

         pedidosViewModel.cargarPedido();

         pedidosViewModel.getPedidoLiveData().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
                     @Override
                     public void onChanged(Pedido pedido) {
                         String id = String.valueOf(pedido.getId());
                         textViewID.setText(id);
                         String hora = pedido.getHoraEntrega().toString();
                         textViewHora.setText(hora);
                     }
         });

                 // Inflate the layout for this fragment
        return view;
    }
}