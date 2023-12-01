package la.dominga.activity.fragments;

import android.content.Intent;
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

import la.dominga.R;
import la.dominga.activity.Inicio.CarritoActivity;
import la.dominga.adapter.CategoriaAdapter;
import la.dominga.adapter.OfertaAdapter;
import la.dominga.adapter.ProductoTopAdapter;
import la.dominga.entity.Producto;
import la.dominga.viewmodel.CategoriaViewModel;
import la.dominga.viewmodel.ProductoViewModel;

public class PanFragment extends Fragment {

    private CategoriaViewModel categoriaViewModel;
    private ProductoViewModel productoViewModel;
    private CategoriaAdapter categoriaAdapter;
    private ProductoTopAdapter productoTopAdapter;
    private RecyclerView rcvCategorias, rvMasVendidos;
    private ViewPager viewPagerOfertas;

    public PanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pan, container, false);
        ImageButton btnCarrito = view.findViewById(R.id.btnCarrito); // Asegúrate de que el ID corresponde
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para CarritoActivity
                Intent intent = new Intent(getContext(), CarritoActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });
        initViewModels();
        setupViewComponents(view);
        loadCategorias();
        loadProductosTop();

        return view;
    }

    private void initViewModels() {
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
    }

    private void setupViewComponents(View view) {
        setupViewPager(view);
        setupRecyclerViews(view);
    }

    private void setupViewPager(View view) {
        viewPagerOfertas = view.findViewById(R.id.viewPagerOfertas);
        OfertaAdapter ofertaAdapter = new OfertaAdapter(getContext());
        viewPagerOfertas.setAdapter(ofertaAdapter);

        ImageButton btnIzquierda = view.findViewById(R.id.btnIzquierda);
        ImageButton btnDerecha = view.findViewById(R.id.btnDerecha);

        btnIzquierda.setOnClickListener(v -> navigateViewPager(-1, ofertaAdapter.getCount()));
        btnDerecha.setOnClickListener(v -> navigateViewPager(1, ofertaAdapter.getCount()));
    }

    private void navigateViewPager(int direction, int itemCount) {
        int currentItem = viewPagerOfertas.getCurrentItem();
        if (currentItem + direction >= 0 && currentItem + direction < itemCount) {
            viewPagerOfertas.setCurrentItem(currentItem + direction);
        } else {
            viewPagerOfertas.setCurrentItem(direction > 0 ? 0 : itemCount - 1);
        }
    }

    private void setupRecyclerViews(View view) {
        setupCategoriasRecyclerView(view);
        setupProductosTopRecyclerView(view);
    }

    private void setupCategoriasRecyclerView(View view) {
        rcvCategorias = view.findViewById(R.id.rcvCategorias);
        categoriaAdapter = new CategoriaAdapter();
        rcvCategorias.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvCategorias.setAdapter(categoriaAdapter);
    }

    private void setupProductosTopRecyclerView(View view) {
        rvMasVendidos = view.findViewById(R.id.rvMasVendidos);
        productoTopAdapter = new ProductoTopAdapter(getContext());
        rvMasVendidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMasVendidos.setAdapter(productoTopAdapter);
    }

    private void loadCategorias() {
        categoriaViewModel.listarCategoriasBD().observe(getViewLifecycleOwner(), respuestaServidor -> {
            if (respuestaServidor != null && respuestaServidor.getBody() != null && !respuestaServidor.getBody().isEmpty()) {
                categoriaAdapter.setListaCategorias(respuestaServidor.getBody());
            } else {
                // Manejar error o ausencia de datos
            }
        });
    }

    private void loadProductosTop() {
        productoViewModel.listarProductosTop().observe(getViewLifecycleOwner(), respuesta -> {
            if (respuesta != null && respuesta.getRpta() == 1 && respuesta.getBody() != null) {
                productoTopAdapter.setProductos(respuesta.getBody());
            } else {
                // Manejar error o ausencia de datos
            }
        });
    }

}
