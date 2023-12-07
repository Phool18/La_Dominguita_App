package la.dominga.activity.Inicio;

import android.content.Intent;
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
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarritoActivity.this, TarjetaActivity.class);

                // Obtener el monto total del TextView
                String totalStr = tvTotal.getText().toString();
                totalStr = totalStr.replace("S/. ", ""); // Remover el símbolo de moneda si está presente
                double total = Double.parseDouble(totalStr); // Convertir a double

                // Poner el monto total en el Intent
                intent.putExtra("montoTotal", total);
                startActivity(intent);
            }
        });

        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementa aquí la navegación de retroceso
                onBackPressed(); // Esto es típicamente utilizado para cerrar la actividad actual
            }
        });
    }

    private void inicializarRecyclerView() {
        carritoAdapter = new CarritoAdapter(this, Carrito.obtenerProductos(), this);
        rvCarritoCompras.setLayoutManager(new LinearLayoutManager(this));
        rvCarritoCompras.setAdapter(carritoAdapter);
    }

    private void actualizarUI() {
        double subtotalSinImpuestos = 0;
        for (DatosCompra datosCompra : Carrito.obtenerProductos()) {
            subtotalSinImpuestos += datosCompra.getProducto().getPrecio() * datosCompra.getCantidad();
        }
        double impuestos = subtotalSinImpuestos * 0.18; // 18% de impuestos
        double total = subtotalSinImpuestos - impuestos;

        tvSubtotal.setText("S/. " + String.format("%.2f", total));
        tvImpuestos.setText("S/. " + String.format("%.2f", impuestos));
        tvTotal.setText("S/. " + String.format("%.2f", subtotalSinImpuestos));
    }

    // Llamado cada vez que un producto es agregado, eliminado o su cantidad es modificada
    @Override
    public void onCarritoUpdated() {
        actualizarUI();
    }
}
