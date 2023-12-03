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

import java.util.ArrayList;
import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.activity.Inicio.CarritoActivity;
import la.dominga.activity.Inicio.DetalleProductoActivity;
import la.dominga.activity.Inicio.ListaProductosActivity;
import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;
import la.dominga.utils.Carrito;

public class ProductosPorCategoria extends RecyclerView.Adapter<ProductosPorCategoria.ViewHolder> {

    private List<Producto> listaProductos;
    private LayoutInflater mInflater;
    private Context context;

    private ListaProductosActivity listaProductosActivity; // Agrega esta variable

    // Constructor
    public ProductosPorCategoria(Context context, ListaProductosActivity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.listaProductos = new ArrayList<>();
        this.context = context;
        this.listaProductosActivity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_productos_por_categoria, parent, false);
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
        holder.tvPrecioProducto.setText("Precio :"+producto.getPrecio());
        holder.tvStockProducto.setText("Cantidad en Stock: "+String.valueOf(producto.getCantidadEnStock()));

        // Carga de imagen con Picasso
        String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
        Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.ivProductoImage);

        // Click listener para ver detalles del producto
        holder.ivProductoImage.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetalleProductoActivity.class);
            intent.putExtra("productoId", producto.getId());
            view.getContext().startActivity(intent);
        });
        holder.ivProductoImage.setOnClickListener(view -> {
            // Utiliza el método de la actividad para abrir el detalle del producto
            listaProductosActivity.abrirDetalleProducto(producto.getId());
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos != null ? listaProductos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecioProducto, tvStockProducto;
        ImageView ivProductoImage;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            tvStockProducto = itemView.findViewById(R.id.tvStockProducto);
            ivProductoImage = itemView.findViewById(R.id.ivProductoImage);

        }
    }
}
