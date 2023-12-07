package la.dominga.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        cargarImagen(producto.getFoto().getNombreArchivo(), holder.ivProducto);

        // Click listener para ver detalles del producto
        holder.ivProducto.setOnClickListener(view -> abrirDetalleProducto(producto.getId(), view.getContext()));

        // Click listener para agregar al carrito
        holder.btnAgregarCarrito.setOnClickListener(view -> {
            // Verifica si hay stock antes de agregar al carrito
            if (producto.getCantidadEnStock() > 0) {
                String mensaje = Carrito.agregarProducto(producto, 1);
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Lo sentimos, no hay stock para este producto. Elija otro.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para cargar la imagen del producto
    private void cargarImagen(String nombreArchivo, ImageView imageView) {
        String url = Connector.baseUrlE + "/foto/download/" + nombreArchivo;
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(imageView);
    }

    // Método para abrir detalles del producto
    private void abrirDetalleProducto(int productoId, Context context) {
        Intent intent = new Intent(context, DetalleProductoActivity.class);
        intent.putExtra("productoId", productoId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listaProductos != null ? listaProductos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecioProducto;
        ImageView ivProducto;
        ImageView btnAgregarCarrito; // Si es un ImageView
        ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            ivProducto = itemView.findViewById(R.id.ivProducto);
            btnAgregarCarrito = itemView.findViewById(R.id.btnAgregarCarrito);
        }
    }
}
