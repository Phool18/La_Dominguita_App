package la.dominga.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import la.dominga.R;
import la.dominga.activity.Inicio.ListaProductosActivity;

public class SombreroFragment extends Fragment {

    private TextView tvCategoria1, tvCategoria2, tvCategoria3, tvCategoria4;

    public SombreroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sombrero, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        tvCategoria1 = view.findViewById(R.id.tvCategoria1);
        tvCategoria2 = view.findViewById(R.id.tvCategoria2);
        tvCategoria3 = view.findViewById(R.id.tvCategoria3);
        tvCategoria4 = view.findViewById(R.id.tvCategoria4);

        tvCategoria1.setOnClickListener(v -> abrirListaProductosPorCategoria(1));
        tvCategoria2.setOnClickListener(v -> abrirListaProductosPorCategoria(2));
        tvCategoria3.setOnClickListener(v -> abrirListaProductosPorCategoria(3));
        tvCategoria4.setOnClickListener(v -> abrirListaProductosPorCategoria(4));
    }

    private void abrirListaProductosPorCategoria(int idCategoria) {
        Intent intent = new Intent(getContext(), ListaProductosActivity.class);
        intent.putExtra("categoriaId", idCategoria);
        startActivity(intent);
    }
}
