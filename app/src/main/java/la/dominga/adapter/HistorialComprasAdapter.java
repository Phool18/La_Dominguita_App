package la.dominga.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import la.dominga.R;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import java.util.List;

public class HistorialComprasAdapter extends RecyclerView.Adapter<HistorialComprasAdapter.ViewHolder> {

    private final List<ImprimirPedidosDTO> compras;
    private OnDetalleClickListener detalleClickListener;
    private OnDescargaBoletaClickListener descargaBoletaClickListener;

    public interface OnDetalleClickListener {
        void onDetalleClick(ImprimirPedidosDTO compra);

    }
    public interface OnDescargaBoletaClickListener {
        void onDescargaBoletaClick(int idCompra);
    }

    public HistorialComprasAdapter(List<ImprimirPedidosDTO> compras, OnDetalleClickListener detalleClickListener, OnDescargaBoletaClickListener descargaBoletaClickListener) {
        this.compras = compras;
        this.detalleClickListener = detalleClickListener;
        this.descargaBoletaClickListener = descargaBoletaClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial_compras, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImprimirPedidosDTO compra = compras.get(position);
        holder.tvFechaCompraValor.setText(compra.getCarritoDeCompras().getFechaCompra().toString());
        holder.tvPrecioTotalValor.setText("S/." + compra.getCarritoDeCompras().getMonto());
        holder.btnVerDetalles.setOnClickListener(v -> detalleClickListener.onDetalleClick(compra));
        holder.btnDescargarBoleta.setOnClickListener(v -> descargaBoletaClickListener.onDescargaBoletaClick(compra.getCarritoDeCompras().getId()));
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFechaCompraValor, tvPrecioTotalValor;
        Button btnVerDetalles;
        ImageButton btnDescargarBoleta;

        ViewHolder(View itemView) {
            super(itemView);
            tvFechaCompraValor = itemView.findViewById(R.id.tvFechaCompraValor);
            tvPrecioTotalValor = itemView.findViewById(R.id.tvPrecioTotalValor);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
            btnDescargarBoleta = itemView.findViewById(R.id.btnDescargarBoleta);
        }
    }
}
