package la.dominga.activity.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import la.dominga.R;
import la.dominga.adapter.CategoriaAdapter;
import la.dominga.adapter.OfertaAdapter;
import la.dominga.adapter.ProductoTopAdapter;
import la.dominga.viewmodel.CategoriaViewModel;
import la.dominga.viewmodel.ProductoViewModel;
import la.dominga.activity.Inicio.DetalleProductoActivity;

public class PanFragment extends Fragment {

    private CategoriaViewModel categoriaViewModel;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvCategorias, rvMasVendidos;
    private ViewPager viewPagerOfertas;

    public PanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pan, container, false);

        // Inicializar componentes
        viewPagerOfertas = view.findViewById(R.id.viewPagerOfertas);
        rcvCategorias = view.findViewById(R.id.rcvCategorias);
        rvMasVendidos = view.findViewById(R.id.rvMasVendidos);

        // Configurar ViewPager de ofertas
        OfertaAdapter ofertaAdapter = new OfertaAdapter(getContext());
        viewPagerOfertas.setAdapter(ofertaAdapter);
        configurarBotonesViewPager(view, ofertaAdapter);

        // Configurar ViewModel
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        // Configurar RecyclerViews
        configurarRecyclerViewCategorias();
        configurarRecyclerViewMasVendidos();

        // Cargar datos
        cargarCategorias();
        cargarProductosMasVendidos();

        return view;
    }

    private void configurarBotonesViewPager(View view, OfertaAdapter ofertaAdapter) {
        ImageButton btnIzquierda = view.findViewById(R.id.btnIzquierda);
        ImageButton btnDerecha = view.findViewById(R.id.btnDerecha);

        btnIzquierda.setOnClickListener(v -> navegarViewPager(viewPagerOfertas, ofertaAdapter, true));
        btnDerecha.setOnClickListener(v -> navegarViewPager(viewPagerOfertas, ofertaAdapter, false));
    }

    private void navegarViewPager(ViewPager viewPager, OfertaAdapter ofertaAdapter, boolean irIzquierda) {
        int currentItem = viewPager.getCurrentItem();
        if (irIzquierda) {
            viewPager.setCurrentItem(currentItem > 0 ? currentItem - 1 : ofertaAdapter.getCount() - 1);
        } else {
            viewPager.setCurrentItem(currentItem < ofertaAdapter.getCount() - 1 ? currentItem + 1 : 0);
        }
    }

    private void configurarRecyclerViewCategorias() {
        CategoriaAdapter categoriaAdapter = new CategoriaAdapter();
        rcvCategorias.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvCategorias.setAdapter(categoriaAdapter);
    }

    private void cargarCategorias() {
        categoriaViewModel.listarCategoriasBD().observe(getViewLifecycleOwner(), respuesta -> {
            if (respuesta != null && respuesta.getBody() != null) {
                CategoriaAdapter adapter = (CategoriaAdapter) rcvCategorias.getAdapter();
                if (adapter != null) {
                    adapter.setListaCategorias(respuesta.getBody());
                }
            } else {
                Toast.makeText(getContext(), "No se encontraron categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarRecyclerViewMasVendidos() {
        ProductoTopAdapter adapter = new ProductoTopAdapter(getContext());
        rvMasVendidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMasVendidos.setAdapter(adapter);
        adapter.setOnProductoClickListener(this::abrirDetalleProductoActivity);
    }

    private void cargarProductosMasVendidos() {
        productoViewModel.listarProductosTop().observe(getViewLifecycleOwner(), respuesta -> {
            if (respuesta != null && respuesta.getBody() != null) {
                ProductoTopAdapter adapter = (ProductoTopAdapter) rvMasVendidos.getAdapter();
                if (adapter != null) {
                    adapter.setProductos(respuesta.getBody());
                }
            } else {
                Toast.makeText(getContext(), "Error al cargar productos más vendidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirDetalleProductoActivity(int productoId) {
        Intent intent = new Intent(getContext(), DetalleProductoActivity.class);
        intent.putExtra("productoId", productoId);
        startActivity(intent);
    }
}
