package la.dominga.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.activity.Inicio.CarritoActivity;
import la.dominga.activity.Inicio.DetalleProductoActivity;
import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;
import la.dominga.utils.Carrito;

import java.util.ArrayList;
import java.util.List;

public class ProductoTopAdapter extends RecyclerView.Adapter<ProductoTopAdapter.ViewHolder> {

    private List<Producto> listaProductos;
    private LayoutInflater mInflater;
    private Context context;

    // Constructor
    public ProductoTopAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.listaProductos = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_productos_mas_vendidos, parent, false);
        return new ViewHolder(view);
    }
    public void setProductos(List<Producto> nuevosProductos) {
        listaProductos = nuevosProductos;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioProducto.setText("S/. " + producto.getPrecio());

        // Carga de imagen con Picasso
        String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
        Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.ivProducto);

        // Click listener para ver detalles del producto
        holder.ivProducto.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetalleProductoActivity.class);
            intent.putExtra("productoId", producto.getId());
            view.getContext().startActivity(intent);
        });

        // Click listener para agregar al carrito
        holder.btnAgregarCarrito.setOnClickListener(view -> {
            if (context != null) {
                DatosCompra datosCompra = new DatosCompra();
                datosCompra.setProducto(producto);
                datosCompra.setCantidad(1); // Cantidad predeterminada
                String mensaje = Carrito.agregarProductos(datosCompra);
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

                if (context instanceof CarritoActivity) {
                    ((CarritoActivity) context).actualizarCarrito();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProductos != null ? listaProductos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecioProducto;
        ImageView ivProducto, btnAgregarCarrito;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            ivProducto = itemView.findViewById(R.id.ivProducto);
            btnAgregarCarrito = itemView.findViewById(R.id.btnAgregarCarrito);
        }
    }
}
