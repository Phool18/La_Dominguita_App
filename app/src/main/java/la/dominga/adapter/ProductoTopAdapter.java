package la.dominga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.entity.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoTopAdapter extends RecyclerView.Adapter<ProductoTopAdapter.ViewHolder> {

    private List<Producto> listaProductos = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnProductoClickListener onProductoClickListener;

    // Constructor
    public ProductoTopAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    // Setear lista de productos
    public void setProductos(List<Producto> productos) {
        this.listaProductos = productos;
        notifyDataSetChanged();
    }

    // Interfaz para manejar clics en los productos
    public interface OnProductoClickListener {
        void onProductoClick(int productoId);
    }

    // Setear listener para clics en productos
    public void setOnProductoClickListener(OnProductoClickListener listener) {
        this.onProductoClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_productos_mas_vendidos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioProducto.setText("S/. " + producto.getPrecio());

        if (producto.getFoto() != null && producto.getFoto().getNombreArchivo() != null) {
            String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
            Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                    .downloader(new OkHttp3Downloader(Connector.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivProducto);
        }

        holder.ivProducto.setOnClickListener(view -> {
            if (onProductoClickListener != null) {
                onProductoClickListener.onProductoClick(producto.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecioProducto;
        ImageView ivProducto;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            ivProducto = itemView.findViewById(R.id.ivProducto);
        }
    }
}
