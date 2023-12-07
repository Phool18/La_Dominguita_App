package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import la.dominga.R;
import la.dominga.adapter.DetalleComprasAdapter;
import la.dominga.entity.DatosCompra;
import la.dominga.viewmodel.CarritoDeComprasViewModel;

import java.util.List;

public class DetalleComprasActivity extends AppCompatActivity {

    private RecyclerView rvDetalleCompras;
    private CarritoDeComprasViewModel viewModel;
    private DetalleComprasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_compras);

        rvDetalleCompras = findViewById(R.id.rvDetalleCompras);
        viewModel = new ViewModelProvider(this).get(CarritoDeComprasViewModel.class);
        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementa aquí la navegación de retroceso
                onBackPressed(); // Esto es típicamente utilizado para cerrar la actividad actual
            }
        });
        cargarDetalleDeCompra();
    }

    private void cargarDetalleDeCompra() {
        // Obtiene el ID del carrito de la compra desde el Intent
        int idCarrito = getIntent().getIntExtra("idCarrito", -1);
        Log.d("El id que se envio es: ", String.valueOf(idCarrito));
        if (idCarrito != -1) {
            viewModel.obtenerDetallesCarrito(idCarrito).observe(this, respuesta -> {
                if (respuesta != null && respuesta.getRpta() == 1) {
                    List<DatosCompra> detallesCompra = respuesta.getBody();
                    mostrarDetallesCompra(detallesCompra);
                } else {
                    Toast.makeText(this, "Error al cargar los detalles de la compra", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error al obtener el ID del carrito de compras", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDetallesCompra(List<DatosCompra> detallesCompra) {
        if (detallesCompra != null && !detallesCompra.isEmpty()) {
            adapter = new DetalleComprasAdapter(detallesCompra);
            rvDetalleCompras.setLayoutManager(new LinearLayoutManager(this));
            rvDetalleCompras.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay detalles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
}
