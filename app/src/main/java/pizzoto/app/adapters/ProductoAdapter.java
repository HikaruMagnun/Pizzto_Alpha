package pizzoto.app.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import pizzoto.app.R;
import pizzoto.app.models.Producto;
import pizzoto.app.ui.Menu.MenuFragmentDirections;


public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private final List<Producto> listaProducto;

    public ProductoAdapter(List<Producto> listaPizza) {
        this.listaProducto = listaPizza;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Producto producto = listaProducto.get(position);
        holder.nombreTextView.setText(producto.getNombre());
        holder.descripcionTextView.setText(producto.getDescripcion());
        String precio = " S/." + String.format("%.2f", producto.getPrecio());
        holder.precioTextView.setText(precio);

        holder.botonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = MenuFragmentDirections.actionMenuFragmentToProductoDescription(producto.getNombre(),producto.getId());

                Navigation.findNavController(view)
                        .navigate(action);
            }

        });

    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewpizza;
        TextView nombreTextView;
        TextView descripcionTextView;
        TextView precioTextView;
        ImageButton botonImagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewpizza = itemView.findViewById(R.id.item_pizza_image);
            nombreTextView = itemView.findViewById(R.id.item_pizza_nombre);
            descripcionTextView = itemView.findViewById(R.id.item_pizza_descripcion);
            precioTextView = itemView.findViewById(R.id.item_pizza_precio);
            botonImagen = itemView.findViewById(R.id.item_producto_button);
        }
    }
}
