package la.dominga.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import la.dominga.R;
import la.dominga.entity.Categoria;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> listaCategorias;

    // Constructor
    public CategoriaAdapter() {
        this.listaCategorias = new ArrayList<>();
    }

    // Método para actualizar la lista de categorías
    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);
        holder.bind(categoria);
    }

    @Override
    public int getItemCount() {
        return listaCategorias != null ? listaCategorias.size() : 0;
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreCategoria;

        CategoriaViewHolder(View itemView) {
            super(itemView);
            tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria);
        }

        void bind(Categoria categoria) {
            tvNombreCategoria.setText(categoria.getNombre());
            // Aquí puedes establecer más propiedades si tu categoría tiene más información a mostrar
        }
    }
}
