package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import la.dominga.R;
import la.dominga.activity.MainActivity;
import la.dominga.entity.CarritoDeCompras;
import la.dominga.entity.Cliente;
import la.dominga.entity.DatosCompra;
import la.dominga.entity.Producto;
import la.dominga.entity.Usuario;
import la.dominga.entity.dto.CrearPedidoDTO;
import la.dominga.utils.Carrito;
import la.dominga.utils.FechaVencimientoTextWatcher;
import la.dominga.viewmodel.CarritoDeComprasViewModel;
import la.dominga.viewmodel.TarjetaViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class TarjetaActivity extends AppCompatActivity {

    private TextInputEditText edtFechaVencimiento, edtNumeroTarjeta, edtNombreTitular, edtCvv;
    private Button btnCheckout, btnValidar;
    private TextView tvMontoTotal;
    private TarjetaViewModel tarjetaViewModel;
    private CarritoDeComprasViewModel carritoDeComprasViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);
        tarjetaViewModel = new ViewModelProvider(this).get(TarjetaViewModel.class);
        carritoDeComprasViewModel = new ViewModelProvider(this).get(CarritoDeComprasViewModel.class);
        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        edtFechaVencimiento = findViewById(R.id.edtFechaVencimiento);
        edtNumeroTarjeta = findViewById(R.id.edtNumeroTarjeta);
        edtNombreTitular = findViewById(R.id.edtNombreTitular);
        edtCvv = findViewById(R.id.edtCvv);
        btnValidar = findViewById(R.id.btnValidar);
        btnCheckout = findViewById(R.id.btnCheckout);
        tvMontoTotal = findViewById(R.id.tvMontoTotal);
        tarjetaViewModel = new ViewModelProvider(this).get(TarjetaViewModel.class);
        double montoTotal = getIntent().getDoubleExtra("montoTotal", 0);
        tvMontoTotal.setText("S/. " + String.format("%.2f", montoTotal));
        btnValidar.setOnClickListener(v -> validarTarjeta());
        btnCheckout.setOnClickListener(v -> procesarPago());
        btnRetroceder.setOnClickListener(v -> onBackPressed());
        edtFechaVencimiento.addTextChangedListener(new FechaVencimientoTextWatcher(edtFechaVencimiento));
        btnCheckout.setEnabled(false);
    }

    private void validarTarjeta() {
        String numeroTarjeta = edtNumeroTarjeta.getText().toString();
        String titular = edtNombreTitular.getText().toString();
        String cvv = edtCvv.getText().toString();
        String mesAnio = edtFechaVencimiento.getText().toString();

        tarjetaViewModel.validarTarjeta(numeroTarjeta, titular, cvv, mesAnio).observe(this, respuesta -> {
            if (respuesta != null && respuesta.getRpta() == 1) {
                btnCheckout.setEnabled(true);
                Toast.makeText(TarjetaActivity.this, "La tarjeta es válida.", Toast.LENGTH_SHORT).show();
            } else {
                btnCheckout.setEnabled(false);
                Toast.makeText(TarjetaActivity.this, "La tarjeta no es válida.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void procesarPago() {
        Cliente clienteActual = obtenerClienteActual();
        if (clienteActual == null) {
            Toast.makeText(this, "Error: Cliente no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<DatosCompra> productosEnCarrito = obtenerProductosEnCarrito();
        if (productosEnCarrito.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío.", Toast.LENGTH_SHORT).show();
            return;
        }

        double montoTotal = productosEnCarrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        CrearPedidoDTO crearPedidoDTO = new CrearPedidoDTO();
        CarritoDeCompras carritoDeCompras = new CarritoDeCompras();
        carritoDeCompras.setMonto(montoTotal); // Solo monto, sin ID
        crearPedidoDTO.setCarritoDeCompras(carritoDeCompras);

        Cliente clienteSimple = new Cliente();
        clienteSimple.setId(clienteActual.getId()); // Solo ID del cliente

        crearPedidoDTO.setCliente(clienteSimple);
        crearPedidoDTO.setInformacionDeLaVenta(productosEnCarrito);

        carritoDeComprasViewModel.generarPedidoAlCliente(crearPedidoDTO).observe(this, respuesta -> {
            if (respuesta != null && respuesta.getRpta() == 1) {
                Toast.makeText(this, "Pedido generado con éxito.", Toast.LENGTH_SHORT).show();
                Carrito.limpiarCarrito();

                // Cierra las actividades actuales y redirige al inicio (PanFragment)
                Intent intent = new Intent(TarjetaActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpia todas las actividades anteriores
                intent.putExtra("goto", "PanFragment"); // Opcional, si necesitas abrir un fragmento específico en MainActivity
                startActivity(intent);
                finish(); // Cierra esta actividad
            } else {
                String errorMessage = respuesta != null ? respuesta.getMessage() : "Error desconocido";
                Toast.makeText(this, "Error al generar el pedido: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Cliente obtenerClienteActual() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usuarioJson = preferences.getString("UsuarioJson", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
            return usuario.getCliente();
        }
        return null;
    }

    private List<DatosCompra> obtenerProductosEnCarrito() {
        List<DatosCompra> productosEnCarrito = new ArrayList<>();
        for (DatosCompra datosCompra : Carrito.obtenerProductos()) {
            DatosCompra nuevoDatosCompra = new DatosCompra();
            // Solo establece el ID del producto, sin otros detalles
            Producto productoSimple = new Producto();
            productoSimple.setId(datosCompra.getProducto().getId());
            nuevoDatosCompra.setProducto(productoSimple);

            nuevoDatosCompra.setCantidad(datosCompra.getCantidad());
            nuevoDatosCompra.setPrecio(datosCompra.getPrecio());

            productosEnCarrito.add(nuevoDatosCompra);
        }
        return productosEnCarrito;
    }





}
