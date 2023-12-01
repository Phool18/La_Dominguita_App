package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.entity.Producto;
import la.dominga.entity.RespuestaServidor;
import la.dominga.viewmodel.ProductoViewModel;

public class DetalleProductoActivity extends AppCompatActivity {

    private ProductoViewModel productoViewModel;
    private ImageView ivProducto;
    private TextView tvNombre, tvDescripcion, tvPrecio, tvStock;
    private int productoId;

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
    }

    private void initUI() {
        ivProducto = findViewById(R.id.ivProducto);
        tvNombre = findViewById(R.id.tvNombre);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvStock = findViewById(R.id.tvStock);
    }

    private void initViewModel() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
    }

    private void actualizarUI(RespuestaServidor<Producto> respuesta) {
        if (respuesta != null && respuesta.getRpta() == 1 && respuesta.getBody() != null) {
            Producto producto = respuesta.getBody();
            tvNombre.setText(producto.getNombre());
            tvDescripcion.setText(producto.getDescripcion());
            tvPrecio.setText("" + producto.getPrecio());
            tvStock.setText("Stock: " + producto.getCantidadEnStock() + " unidades");

            // Aquí debes cargar la imagen del producto como lo has estado haciendo
            String url = Connector.baseUrlE + "/foto/download/" + producto.getFoto().getNombreArchivo();
            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(Connector.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.image_not_found)
                    .into(ivProducto);
        } else {
            // Manejar el caso en que no se obtengan datos o haya un error
            Toast.makeText(this, "Error al obtener detalles del producto", Toast.LENGTH_SHORT).show();
        }
    }
}
