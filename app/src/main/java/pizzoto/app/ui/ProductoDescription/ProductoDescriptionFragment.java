package pizzoto.app.ui.ProductoDescription;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pizzoto.app.R;
import pizzoto.app.models.Ingrediente;
import pizzoto.app.models.Producto;

public class ProductoDescriptionFragment extends Fragment {
    TextView textViewNombre;
    TextView textViewIngredientes;
    TextView textViewPrecio;
    TextView textViewTiempo;
    TextView textViewPrecioTotal;

    List<Producto> productoList;
    Producto productoSelect;
    Spinner spinnerTamano;
    EditText editTextCantidad;

    Button buttonPLus;
    Button buttonLess;
    Button buttonComprar;
    View view;
    int idP;

    private ProductoDescriptionViewModel productoDescriptionViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //declaraciones
        view = inflater.inflate(R.layout.fragment_producto_description, container, false);
        textViewNombre = view.findViewById(R.id.producto_nombre);
        textViewIngredientes =  view.findViewById(R.id.pizza_ingredientes) ;
        textViewPrecio = view.findViewById(R.id.pizza_precio);
        textViewTiempo = view.findViewById(R.id.pizza_tiempo);
        textViewPrecioTotal = view.findViewById(R.id.pizza_precio_total);
        spinnerTamano = view.findViewById(R.id.pizza_tamano_spinner);
        editTextCantidad = view.findViewById(R.id.frame_carrito_pizza_cantidad);
        buttonPLus = view.findViewById(R.id.carrito_pizza_buton_less);
        buttonLess = view.findViewById(R.id.carrito_pizza_buton_plus);
        buttonComprar = view.findViewById(R.id.pizza_comprar);
        productoDescriptionViewModel = new ViewModelProvider(this).get(ProductoDescriptionViewModel.class);
        //control de id pizza y envio
        idP = ProductoDescriptionFragmentArgs.fromBundle(getArguments()).getPizzaId();
        productoDescriptionViewModel.setIdProducto(idP);
        //control nombre pizza

        String name = ProductoDescriptionFragmentArgs.fromBundle(getArguments()).getPizzaName();
        textViewNombre.setText(name);
        productoDescriptionViewModel.setNombreProducto(name);//envio del nombre
        //control de lista de ingredientes
        productoDescriptionViewModel.getproductoRecetaLiveData().observe(getViewLifecycleOwner(), new Observer<Producto>() {
            @Override
            public void onChanged(Producto productoReceta) {
                List<Ingrediente> ingredienteList = productoReceta.getListaIngrediente();
                StringBuilder receta = new StringBuilder();
                for (Ingrediente ingrediente : ingredienteList) {
                    receta.append(ingrediente.getNombre()).append("\n");
                }
                textViewIngredientes.setText(receta);
            }
        });

        /// control de spinner tamaño
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        spinnerTamano.setAdapter(adapter);
        //recogo de productos y asignacion al spinner
        productoDescriptionViewModel.getproductosListaLiveData().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productoLista) {
                productoList = productoLista;
                List<String> items = new ArrayList<>();
                for (Producto producto : productoLista) {
                    items.add( (producto.getTamano()));
                    idP = producto.getId();
                }
                adapter.addAll(items);

            }
        });
        //control tiempo de coccion y precio
        spinnerTamano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                productoSelect = productoList.get(position);
                textViewPrecio.setText(Double.toString(productoSelect.getPrecio()));
                textViewTiempo.setText(Integer.toString(productoSelect.getTiempo_preparacion()));
                recalcularPrecioTotal(textViewPrecio,editTextCantidad,textViewPrecioTotal);
                idP = productoSelect.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        //control botones de aumentar y decrecer
        buttonPLus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editTextCantidad.setText(String.valueOf(Integer.parseInt(editTextCantidad.getText().toString()) + 1 ));
                recalcularPrecioTotal(textViewPrecio,editTextCantidad,textViewPrecioTotal);
            }
        });
        buttonLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(editTextCantidad.getText().toString())>1){
                    editTextCantidad.setText(String.valueOf(Integer.parseInt(editTextCantidad.getText().toString()) - 1 ));
                    recalcularPrecioTotal(textViewPrecio,editTextCantidad,textViewPrecioTotal);
                }
            }
        });
        //control botones de comprar
        buttonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidad = Integer.parseInt(editTextCantidad.getText().toString());
                Producto productoconfirmado =  new Producto();
                productoconfirmado.setId(idP);
                productoDescriptionViewModel.InsertarEnCarrito(productoconfirmado,cantidad,getContext());
                Toast.makeText(getContext(), "Compra añadida al carrito", Toast.LENGTH_SHORT).show();
                NavDirections action = ProductoDescriptionFragmentDirections.actionProductoDescriptionToMenuFragment();
                Navigation.findNavController(view)
                        .navigate(action);

            }
        });



        return view;

    }
    //recalula es precio total
    private void recalcularPrecioTotal(TextView precio,EditText cantidad, TextView total){
        String Total =String.valueOf(Double.parseDouble( precio.getText().toString())
                * (double) Integer.parseInt(cantidad.getText().toString())) ;
        total.setText(Total);
    }





}