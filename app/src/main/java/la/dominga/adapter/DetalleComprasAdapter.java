package la.dominga.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.entity.DatosCompra;

public class DetalleComprasAdapter extends RecyclerView.Adapter<DetalleComprasAdapter.ViewHolder> {

    private final List<DatosCompra> detallesCompra;

    public DetalleComprasAdapter(List<DatosCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatosCompra detalle = detallesCompra.get(position);
        holder.tvNombreProducto.setText(detalle.getProducto().getNombre());
        holder.tvCantidad.setText(String.valueOf(detalle.getCantidad()));
        holder.tvPrecio.setText("S/." + detalle.getPrecio());

        // Cargar imagen del producto usando Picasso o cualquier otra librería de imágenes
        String url = Connector.baseUrlE + "/foto/download/" + detalle.getProducto().getFoto().getNombreArchivo();
        Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.imgProducto);
    }

    @Override
    public int getItemCount() {
        return detallesCompra.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView tvNombreProducto, tvCantidad, tvPrecio;

        ViewHolder(View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
