package pizzoto.app.ui.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pizzoto.app.R;
import pizzoto.app.Repository.Local.ClienteLocalRepository;
import pizzoto.app.models.Cliente;
import pizzoto.app.models.Ingrediente;
import pizzoto.app.models.Producto;
import pizzoto.app.ui.Menu.MenuFragmentDirections;


public class HomeFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        TextView textViewboton = view.findViewById(R.id.textView20);
        ImageView imageButtonboton = view.findViewById(R.id.buttonimg);
        Button button = view.findViewById(R.id.button);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.cargarDatos(getContext());


        homeViewModel.getStringMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String nombreCliente) {
                textViewboton.setText(nombreCliente);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto = new Producto();
                producto.setNombre("Pizza Americana");
                producto.setId(3);
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToProductoDescription(producto.getNombre(), producto.getId());

                Navigation.findNavController(view)
                        .navigate(action);
            }

        });


        return view;
    }
}