package pizzoto.app.ui.Carrito;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pizzoto.app.R;
import pizzoto.app.adapters.CarritoAdapter;
import pizzoto.app.adapters.ProductoAdapter;
import pizzoto.app.models.PedidoDescripcion;
import pizzoto.app.models.Producto;
import pizzoto.app.ui.Menu.MenuViewModel;

public class CarritoFragment extends Fragment {

    CarritoViewModel carritoViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //desclaraciones
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.listaPedidos);
        CardView cardView = view.findViewById(R.id.carview_price);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ProgressBar progressBar = view.findViewById(R.id.progressBar2);
        TextView textViewsubtotaltottal  = view.findViewById(R.id.carrito_subsub_total);
        TextView textViewPrecioFinal  = view.findViewById(R.id.carrito_finafinal_precio);
        Button fragment_carrito_button = view.findViewById(R.id.fragment_carrito_button);
        carritoViewModel = new ViewModelProvider(this).get(CarritoViewModel.class);
        carritoViewModel.pasarContext(view);
        final int[] numero = {0};
        //control de reciclerview
        carritoViewModel.getpedidoDescriptionListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<PedidoDescripcion>>() {
            @Override
            public void onChanged(List<PedidoDescripcion> pedidoDescripcionList) {
                if (numero[0] == 0){
                    CarritoAdapter carritoAdapter = new CarritoAdapter(pedidoDescripcionList,carritoViewModel);
                    recyclerView.setAdapter(carritoAdapter);
                    numero[0]++;
                }

                Double total = 0.0;
                for (PedidoDescripcion pedi:pedidoDescripcionList) {
                    total += (pedi.getProducto().getPrecio()*pedi.getCantidad());

                }
                textViewsubtotaltottal.setText(Double.toString(total));
                textViewPrecioFinal.setText(Double.toString(total));
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);

            }
        });
        //boton comprar
        fragment_carrito_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carritoViewModel.comprar();
            }
        });
        //espera de compra realizada
        carritoViewModel.getIntegerMIntegerLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer ==1){
                    Toast.makeText(getContext(), "COMPRA EXISTOSA", Toast.LENGTH_SHORT).show();
                    NavDirections action = CarritoFragmentDirections.actionCarritoFragmentToPedidosFragment();
                    Navigation.findNavController(getView()).navigate(action);

                }else {
                    Toast.makeText(getContext(), "COMPRA RECHAZADA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}