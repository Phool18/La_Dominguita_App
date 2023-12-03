package la.dominga.activity.Inicio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import la.dominga.R;
import la.dominga.adapter.CarritoAdapter;
import la.dominga.entity.DatosCompra;
import la.dominga.utils.Carrito;

public class CarritoActivity extends AppCompatActivity implements CarritoAdapter.ICarritoAdapterListener {

    private RecyclerView rvCarritoCompras;
    private CarritoAdapter carritoAdapter;
    private TextView tvSubtotal, tvImpuestos, tvTotal;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        rvCarritoCompras = findViewById(R.id.rvCarritoCompras);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvImpuestos = findViewById(R.id.tvImpuestos);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        inicializarRecyclerView();
        actualizarUI();

        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(v -> onBackPressed());

        btnCheckout.setOnClickListener(view -> {
            // Implementar lógica de checkout
        });
    }

    private void inicializarRecyclerView() {
        carritoAdapter = new CarritoAdapter(this, Carrito.obtenerProductos(), this);
        rvCarritoCompras.setLayoutManager(new LinearLayoutManager(this));
        rvCarritoCompras.setAdapter(carritoAdapter);
    }

    private void actualizarUI() {
        double subtotal = 0;
        for (DatosCompra datosCompra : Carrito.obtenerProductos()) {
            subtotal += datosCompra.getProducto().getPrecio() * datosCompra.getCantidad();
        }
        double impuestos = subtotal * 0.18; // 18% de impuestos
        double total = subtotal + impuestos;

        tvSubtotal.setText("S/. " + String.format("%.2f", subtotal));
        tvImpuestos.setText("S/. " + String.format("%.2f", impuestos));
        tvTotal.setText("S/. " + String.format("%.2f", total));
    }

    // Llamado cada vez que un producto es agregado, eliminado o su cantidad es modificada
    @Override
    public void onCarritoUpdated() {
        actualizarUI();
    }
}
