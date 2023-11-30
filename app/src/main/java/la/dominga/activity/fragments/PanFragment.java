package la.dominga.activity.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import la.dominga.R;
import la.dominga.adapter.CategoriaAdapter;
import la.dominga.adapter.OfertaAdapter;
import la.dominga.adapter.ProductoTopAdapter;
import la.dominga.entity.Producto;
import la.dominga.viewmodel.CategoriaViewModel;
import la.dominga.viewmodel.ProductoViewModel;

public class PanFragment extends Fragment {

    private CategoriaViewModel categoriaViewModel;
    private CategoriaAdapter categoriaAdapter;
    private RecyclerView rcvCategorias;
    private ViewPager viewPagerOfertas;

    private RecyclerView rvMasVendidos;
    private ProductoViewModel productoViewModel;

    public PanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pan, container, false);

        viewPagerOfertas = view.findViewById(R.id.viewPagerOfertas);
        OfertaAdapter ofertaAdapter = new OfertaAdapter(getContext());
        viewPagerOfertas.setAdapter(ofertaAdapter);

        // Botones para navegar en el ViewPager
        ImageButton btnIzquierda = view.findViewById(R.id.btnIzquierda);
        ImageButton btnDerecha = view.findViewById(R.id.btnDerecha);

        // Controlador de eventos para btnIzquierda
        btnIzquierda.setOnClickListener(v -> {
            int currentItem = viewPagerOfertas.getCurrentItem();
            if (currentItem > 0) {
                viewPagerOfertas.setCurrentItem(currentItem - 1);
            } else {
                viewPagerOfertas.setCurrentItem(ofertaAdapter.getCount() - 1);
            }
        });

        // Controlador de eventos para btnDerecha
        btnDerecha.setOnClickListener(v -> {
            int currentItem = viewPagerOfertas.getCurrentItem();
            if (currentItem < ofertaAdapter.getCount() - 1) {
                viewPagerOfertas.setCurrentItem(currentItem + 1);
            } else {
                viewPagerOfertas.setCurrentItem(0);
            }
        });


        rvMasVendidos = view.findViewById(R.id.rvMasVendidos);
        inicializarRvMasVendidos();
        initViewModel();
        setupRecyclerView(view);
        loadCategorias();

        return view;
    }

    private void initViewModel() {
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
    }

    private void setupRecyclerView(View view) {
        rcvCategorias = view.findViewById(R.id.rcvCategorias);
        categoriaAdapter = new CategoriaAdapter();
        rcvCategorias.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvCategorias.setAdapter(categoriaAdapter);
    }

    private void loadCategorias() {
        categoriaViewModel.listarCategoriasBD().observe(getViewLifecycleOwner(), respuestaServidor -> {
            if (respuestaServidor != null && respuestaServidor.getBody() != null && !respuestaServidor.getBody().isEmpty()) {
                categoriaAdapter.setListaCategorias(respuestaServidor.getBody());
            } else {
                Toast.makeText(getContext(), "No se encontraron categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void inicializarRvMasVendidos() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        productoViewModel.listarProductosTop().observe(getViewLifecycleOwner(), respuesta -> {
            if (respuesta != null && respuesta.getRpta() == 1) {
                List<Producto> productos = respuesta.getBody();
                ProductoTopAdapter adapter = new ProductoTopAdapter(getContext(), productos);
                rvMasVendidos.setAdapter(adapter);
                rvMasVendidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            }
            // Puedes manejar aquí los casos en los que no se reciban datos
        });
    }

}
