package la.dominga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.activity.Inicio.CarritoActivity;
import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;
import la.dominga.utils.Carrito;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private List<DatosCompra> listaDatosCompras;
    private Context context;
    private ICarritoAdapterListener listener;

    // Constructor
    public CarritoAdapter(Context context, List<DatosCompra> listaDatosCompras, ICarritoAdapterListener listener) {
        this.context = context;
        this.listaDatosCompras = listaDatosCompras;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_productos_en_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatosCompra datosCompra = listaDatosCompras.get(position);
        Producto producto = datosCompra.getProducto();

        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecio.setText("S/. " + producto.getPrecio());
        holder.edtCantidad.setText(String.valueOf(datosCompra.getCantidad()));

        String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.imgProducto);

        holder.btnEliminarProducto.setOnClickListener(view -> {
            Carrito.eliminarProducto(producto.getId());
            listaDatosCompras.removeIf(dc -> dc.getProducto().getId() == producto.getId());
            notifyDataSetChanged();
            listener.onCarritoUpdated();
        });

        holder.btnAdd.setOnClickListener(view -> {
            int currentQuantity = Integer.parseInt(holder.edtCantidad.getText().toString());
            if (currentQuantity < producto.getCantidadEnStock()) {
                holder.edtCantidad.setText(String.valueOf(currentQuantity + 1));
                datosCompra.setCantidad(currentQuantity + 1);
                listener.onCarritoUpdated();
            }
        });

        holder.btnDecrease.setOnClickListener(view -> {
            int currentQuantity = Integer.parseInt(holder.edtCantidad.getText().toString());
            if (currentQuantity > 1) {
                holder.edtCantidad.setText(String.valueOf(currentQuantity - 1));
                datosCompra.setCantidad(currentQuantity - 1);
                listener.onCarritoUpdated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDatosCompras.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView tvNombreProducto, tvPrecio;
        EditText edtCantidad;
        ImageButton btnEliminarProducto, btnAdd, btnDecrease;

        ViewHolder(View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }

    // Interfaz de escucha para comunicar eventos de actualización al activity
    public interface ICarritoAdapterListener {
        void onCarritoUpdated();
    }
}
