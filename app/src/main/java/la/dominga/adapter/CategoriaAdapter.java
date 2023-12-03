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
    private OnCategoriaClickListener listener;

    // Define la interfaz para el clic en una categoría
    public interface OnCategoriaClickListener {
        void onCategoriaClick(int categoriaId);
    }
    public CategoriaAdapter() {
        this.listaCategorias = new ArrayList<>();
    }
    public void setOnCategoriaClickListener(OnCategoriaClickListener listener) {
        this.listener = listener;
    }
    // Constructor modificado para incluir el listener
    public CategoriaAdapter(OnCategoriaClickListener listener) {
        this.listaCategorias = new ArrayList<>();
        this.listener = listener;
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
        holder.bind(categoria, listener);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onCategoriaClick(categoria.getId());
                }
            }
        });
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

        void bind(final Categoria categoria, final OnCategoriaClickListener listener) {
            tvNombreCategoria.setText(categoria.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && categoria != null) {
                        listener.onCategoriaClick(categoria.getId()); // Usar getId() de la categoría
                    }
                }
            });
        }
    }
}