package la.dominga.activity.Inicio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import la.dominga.R;
import la.dominga.adapter.CarritoAdapter;
import la.dominga.entity.DatosCompra;
import la.dominga.utils.Carrito;

import java.util.List;

public class CarritoActivity extends AppCompatActivity {

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

        btnCheckout.setOnClickListener(view -> {
            // Implementar lógica de checkout
        });
    }

    private void inicializarRecyclerView() {
        List<DatosCompra> listaDatosCompras = Carrito.getDatosCompras();
        carritoAdapter = new CarritoAdapter(this, listaDatosCompras);
        rvCarritoCompras.setLayoutManager(new LinearLayoutManager(this));
        rvCarritoCompras.setAdapter(carritoAdapter);
    }

    private void actualizarUI() {
        double subtotal = 0;
        for (DatosCompra datosCompra : Carrito.getDatosCompras()) {
            subtotal += datosCompra.getProducto().getPrecio() * datosCompra.getCantidad();
        }
        double impuestos = subtotal * 0.15; // 15% de impuestos
        double total = subtotal + impuestos;

        tvSubtotal.setText("S/. " + String.format("%.2f", subtotal));
        tvImpuestos.setText("S/. " + String.format("%.2f", impuestos));
        tvTotal.setText("S/. " + String.format("%.2f", total));
    }

    // Llamado cada vez que un producto es agregado, eliminado o su cantidad es modificada
    public void actualizarCarrito() {
        carritoAdapter.notifyDataSetChanged();
        actualizarUI();
    }
}
