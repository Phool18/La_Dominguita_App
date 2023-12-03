package la.dominga.activity.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import la.dominga.R;
import la.dominga.adapter.ProductosPorCategoria;
import la.dominga.entity.Producto;
import la.dominga.viewmodel.ProductoViewModel;

public class ListaProductosActivity extends AppCompatActivity {

    private static final String TAG = "ListaProductosActivity";

    private ProductoViewModel productoViewModel;
    private ProductosPorCategoria adapter;
    private RecyclerView rcvProductosPorCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_por_categoria);

        // Inicialización del RecyclerView
        rcvProductosPorCategoria = findViewById(R.id.rcvProdCategorias);
        rcvProductosPorCategoria.setLayoutManager(new LinearLayoutManager(this));

        // Inicialización del Adapter
        adapter = new ProductosPorCategoria(this, this);
        rcvProductosPorCategoria.setAdapter(adapter);

        // Inicialización del ViewModel
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(v -> onBackPressed());

        int idCategoria = getIntent().getIntExtra("categoriaId", -1);
        if (idCategoria != -1) {
            cargarProductosPorCategoria(idCategoria);
        } else {
            Log.e(TAG, "ID de categoría no encontrado o es inválido");
            // Mostrar mensaje de error o tomar acción apropiada
        }
    }

    public void abrirDetalleProducto(int productoId) {
        Intent intent = new Intent(this, DetalleProductoActivity.class);
        intent.putExtra("productoId", productoId);
        startActivity(intent);
    }

    private void cargarProductosPorCategoria(int idCategoria) {
        productoViewModel.listarProductosPorCategoria(idCategoria).observe(this, respuestaServidor -> {
            if (respuestaServidor != null && respuestaServidor.getBody() != null) {
                List<Producto> listaProductos = respuestaServidor.getBody();
                adapter.setProductos(listaProductos);
            } else {
                Log.e(TAG, "No se pudo obtener datos del servidor o están vacíos");
                // Mostrar mensaje de error o tomar acción apropiada
            }
        });
    }
}
