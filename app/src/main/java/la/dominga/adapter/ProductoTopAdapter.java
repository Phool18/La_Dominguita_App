package la.dominga.adapter;

import android.content.Context;
import android.content.Intent;
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
import la.dominga.activity.Inicio.DetalleProductoActivity;
import la.dominga.entity.Producto;

import java.util.List;

public class ProductoTopAdapter extends RecyclerView.Adapter<ProductoTopAdapter.ViewHolder> {

    private List<Producto> listaProductos;
    private LayoutInflater mInflater;

    // Constructor
    public ProductoTopAdapter(Context context, List<Producto> listaProductos) {
        this.mInflater = LayoutInflater.from(context);
        this.listaProductos = listaProductos;
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

        if (producto.getFoto() != null && producto.getFoto().getNombreFoto() != null) {
            String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
            Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                    .downloader(new OkHttp3Downloader(Connector.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivProducto);
        }
        holder.ivProducto.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetalleProductoActivity.class);
            intent.putExtra("productoId", producto.getId());
            view.getContext().startActivity(intent);
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
