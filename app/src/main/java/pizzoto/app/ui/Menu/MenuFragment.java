package pizzoto.app.ui.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import pizzoto.app.R;
import pizzoto.app.adapters.ProductoAdapter;
import pizzoto.app.models.Producto;


public class MenuFragment extends Fragment {


    private MenuViewModel menuViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.lista_producto);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.getListaProductosLiveData().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> datos) {
                ProductoAdapter productoAdapter = new ProductoAdapter(datos);
                recyclerView.setAdapter(productoAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}