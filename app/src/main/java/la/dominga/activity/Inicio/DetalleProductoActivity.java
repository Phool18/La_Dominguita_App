package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.entity.Producto;
import la.dominga.entity.RespuestaServidor;
import la.dominga.utils.Carrito;
import la.dominga.entity.DatosCompra;
import la.dominga.viewmodel.ProductoViewModel;

public class DetalleProductoActivity extends AppCompatActivity {

    private ProductoViewModel productoViewModel;
    private ImageView ivProducto;
    private TextView tvNombre, tvDescripcion, tvPrecio, tvStock;
    private Button btnAgregarAlCarrito;
    private int productoId;
    private Producto productoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        productoId = getIntent().getIntExtra("productoId", -1);

        initUI();
        initViewModel();

        if (productoId != -1) {
            productoViewModel.obtenerProductoPorId(productoId).observe(this, this::actualizarUI);
        }
        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementa aquí la navegación de retroceso
                onBackPressed(); // Esto es típicamente utilizado para cerrar la actividad actual
            }
        });
    }

    private void initUI() {
        ivProducto = findViewById(R.id.ivProducto);
        tvNombre = findViewById(R.id.tvNombre);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvStock = findViewById(R.id.tvStock);
        btnAgregarAlCarrito = findViewById(R.id.btnAgregarAlCarrito);
        btnAgregarAlCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoActual != null) {
                    int cantidad = 1;
                    String mensaje = Carrito.agregarProducto(productoActual, cantidad);
                    Toast.makeText(DetalleProductoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViewModel() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
    }

    private void actualizarUI(RespuestaServidor<Producto> respuesta) {
        if (respuesta != null && respuesta.getRpta() == 1 && respuesta.getBody() != null) {
            productoActual = respuesta.getBody();

            tvNombre.setText(productoActual.getNombre());
            tvDescripcion.setText(productoActual.getDescripcion());
            tvPrecio.setText("S/. " + productoActual.getPrecio());

            // Verifica si hay stock y actualiza la interfaz de usuario
            if (productoActual.getCantidadEnStock() > 0) {
                tvStock.setText("Stock: " + productoActual.getCantidadEnStock() + " unidades");
                tvStock.setTextColor(getResources().getColor(android.R.color.black));
                btnAgregarAlCarrito.setEnabled(true);
            } else {
                tvStock.setText("Sin Stock");
                tvStock.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                btnAgregarAlCarrito.setEnabled(false);
            }

            cargarImagenProducto(productoActual.getFoto().getNombreArchivo());
        } else {
            Toast.makeText(this, "Error al obtener detalles del producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarImagenProducto(String nombreArchivo) {
        String url = Connector.baseUrlE + "/foto/download/" + nombreArchivo;
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(Connector.getClient()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(ivProducto);
    }


}
