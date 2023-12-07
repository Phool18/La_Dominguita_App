package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import la.dominga.R;
import la.dominga.adapter.HistorialComprasAdapter;
import la.dominga.entity.Usuario;
import la.dominga.entity.dto.ImprimirPedidosDTO;
import la.dominga.utils.BoletaDownloader;
import la.dominga.viewmodel.CarritoDeComprasViewModel;
import okhttp3.ResponseBody;

import java.util.List;

public class HistorialDeComprasActivity extends AppCompatActivity {

    private RecyclerView rvHistorialCompras;
    private CarritoDeComprasViewModel viewModel;
    private HistorialComprasAdapter adapter;
    private BoletaDownloader boletaDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_de_compras);

        rvHistorialCompras = findViewById(R.id.rvHistorialCompras);
        viewModel = new ViewModelProvider(this).get(CarritoDeComprasViewModel.class);
        boletaDownloader = new BoletaDownloader(this);

        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(v -> onBackPressed());

        cargarHistorialDeCompras();
    }

    private void cargarHistorialDeCompras() {
        int idCliente = obtenerIdCliente();
        if (idCliente != -1) {
            viewModel.obtenerMisCompras(idCliente).observe(this, respuesta -> {
                if (respuesta != null && respuesta.getRpta() == 1) {
                    List<ImprimirPedidosDTO> listaCompras = respuesta.getBody();
                    mostrarCompras(listaCompras);
                } else {
                    Toast.makeText(this, "Error al cargar el historial de compras", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error al obtener el ID del cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarCompras(List<ImprimirPedidosDTO> compras) {
        if (compras != null && !compras.isEmpty()) {
            adapter = new HistorialComprasAdapter(compras, this::abrirDetalleActivity, this::descargarBoleta);
            rvHistorialCompras.setLayoutManager(new LinearLayoutManager(this));
            rvHistorialCompras.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay compras en el historial", Toast.LENGTH_SHORT).show();
        }
    }


    private int obtenerIdCliente() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usuarioJson = preferences.getString("UsuarioJson", "");
        if (!usuarioJson.isEmpty()) {
            Usuario usuario = new Gson().fromJson(usuarioJson, Usuario.class);
            return usuario.getCliente().getId();
        }
        return -1;
    }

    private void abrirDetalleActivity(ImprimirPedidosDTO compra) {
        Intent intent = new Intent(this, DetalleComprasActivity.class);
        intent.putExtra("idCarrito", compra.getCarritoDeCompras().getId());
        startActivity(intent);
    }

    private void descargarBoleta(int idCompra) {
        viewModel.descargarBoleta(idCompra).observe(this, body -> {
            if (body != null) {
                boletaDownloader.descargarYMostrarBoleta(body, "boleta_" + idCompra + ".pdf");
            } else {
                Toast.makeText(HistorialDeComprasActivity.this, "Error al descargar la boleta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
