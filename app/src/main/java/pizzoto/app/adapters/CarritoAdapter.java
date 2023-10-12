package pizzoto.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import pizzoto.app.R;
import pizzoto.app.Repository.Local.CarritoLocalRepository;
import pizzoto.app.Repository.Local.ClienteLocalRepository;
import pizzoto.app.models.PedidoDescripcion;
import pizzoto.app.models.Producto;
import pizzoto.app.ui.Carrito.CarritoViewModel;


public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private final List<PedidoDescripcion> listaPedido;
    private int nume = 0;
    private CarritoViewModel carritoViewModel;


    public CarritoAdapter(List<PedidoDescripcion> listaPedido,  CarritoViewModel carritoViewModel
) {
        this.listaPedido = listaPedido;
        this.carritoViewModel = carritoViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    CarritoLocalRepository carritoLocalRepository;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //declaracioes
        PedidoDescripcion pedido = listaPedido.get(position);
        Producto producto = pedido.getProducto();

        //asiganciones
        holder.textViewSubTotal.setText(String.valueOf(pedido.getCantidad() * producto.getPrecio()));
        holder.textViewPizzaName.setText(producto.getNombre());
        holder.editTextCantidad.setText(String.valueOf(pedido.getCantidad()));
        holder.textViewPrecioUnitario.setText(String.valueOf(producto.getPrecio()));
        holder.textViewTamano.setText(producto.getTamano());
        holder.textViewSubTotal.setText(String.valueOf(pedido.getCantidad() * producto.getPrecio()));
        //control de botn mas
        holder.buttonplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Incrementar la cantidad en el EditText y actualizar el subtotal
                int cantidad = Integer.parseInt(holder.editTextCantidad.getText().toString());
                cantidad++;
                holder.editTextCantidad.setText(String.valueOf(cantidad));
                pedido.setCantidad(cantidad);
                holder.textViewSubTotal.setText(String.valueOf(pedido.getCantidad() * producto.getPrecio()));
                carritoLocalRepository = new CarritoLocalRepository(view.getContext());
                carritoLocalRepository.aumentarCantidadProducto(producto.getId());
                notifyDataSetChanged();
                carritoViewModel.realizarCambiosEnLaLista(listaPedido);
            }
        });
        //control del botn menos
        holder.buttonless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidad = Integer.parseInt(holder.editTextCantidad.getText().toString());
                if (cantidad > 1) {
                    cantidad--;
                    holder.editTextCantidad.setText(String.valueOf(cantidad));
                    holder.textViewSubTotal.setText(String.valueOf(pedido.getCantidad() * producto.getPrecio()));
                    pedido.setCantidad(cantidad);
                    carritoLocalRepository = new CarritoLocalRepository(view.getContext());
                    carritoLocalRepository.disminuirCantidadProducto(producto.getId());
                    notifyDataSetChanged();
                    carritoViewModel.realizarCambiosEnLaLista(listaPedido);
                }
            }

        });
        //cancelar la ocmpra de un producto
        holder.imageButtoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carritoLocalRepository = new CarritoLocalRepository(view.getContext());
                carritoLocalRepository.eliminarRegistroPorId(producto.getId());
                int position = holder.getAdapterPosition();
                removeItem(position);
                notifyDataSetChanged();
                carritoViewModel.realizarCambiosEnLaLista(listaPedido);
            }
        });


    }

    public void removeItem(int position) {
        listaPedido.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaPedido.size());
    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPizzaName;
        TextView textViewSubTotal;
        TextView textViewPrecioUnitario;
        ImageButton buttonplus;
        ImageButton buttonless;
        ImageButton imageButtoncancel;
        EditText editTextCantidad;
        TextView textViewTamano;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubTotal = itemView.findViewById(R.id.carrito_pizza_precio_total);
            textViewPizzaName = itemView.findViewById(R.id.carrito_pizza_name);
            textViewPrecioUnitario = itemView.findViewById(R.id.carrito_pizza_precio_unidad);
            buttonplus = itemView.findViewById(R.id.frame_carrito_pizza_buton_plus);
            buttonless = itemView.findViewById(R.id.frame_carrito_pizza_buton_less);
            imageButtoncancel = itemView.findViewById(R.id.carrito_pizza_cancel);
            editTextCantidad = itemView.findViewById(R.id.fram_carrito_pizza_cantidad);
            textViewTamano = itemView.findViewById(R.id.frame_carrito_tamaño);

        }
    }
    //actualizar
   /* private void updateSubtotal(ViewHolder holder, int position) {
        // Verificar si la posición es válida
        if (position >= 0 && position < listaPedido.size()) {
            PedidoDescripcion pedido = listaPedido.get(position);
            Producto producto = pedido.getProducto();
            double subtotal = pedido.getCantidad() * producto.getPrecio();
            holder.textViewSubTotal.setText(String.valueOf(subtotal));
        }

    }

*/


}
