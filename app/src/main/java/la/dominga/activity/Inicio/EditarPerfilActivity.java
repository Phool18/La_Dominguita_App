package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import la.dominga.R;
import la.dominga.entity.Usuario;
import la.dominga.viewmodel.UsuarioViewModel;

public class EditarPerfilActivity extends AppCompatActivity {

    private TextInputEditText edtcampoNombre;
    private TextInputEditText edtcampoCorreoElectronico;
    private TextInputEditText edtcampoNumeroTelefonico;
    private Button btnGuardarDatos;
    private UsuarioViewModel viewModel;
    private Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        edtcampoNombre = findViewById(R.id.edtcampoNombre);
        edtcampoCorreoElectronico = findViewById(R.id.edtcampoCorreoElectronico);
        edtcampoNumeroTelefonico = findViewById(R.id.edtcampoNumeroTelefonico);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);

        usuarioActual = obtenerUsuarioActual();
        if (usuarioActual != null) {
            rellenarDatosUsuario();
        }

        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatosUsuario();
            }
        });
    }

    private Usuario obtenerUsuarioActual() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usuarioJson = preferences.getString("UsuarioJson", "");

        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(usuarioJson, Usuario.class);
        }
        return null;
    }

    private void rellenarDatosUsuario() {
        edtcampoNombre.setText(usuarioActual.getCliente().getNombreCompleto());
        edtcampoCorreoElectronico.setText(usuarioActual.getCorreo());
        edtcampoNumeroTelefonico.setText(usuarioActual.getCliente().getNumeroTelefonico());
    }

    private void actualizarDatosUsuario() {
        String nombre = edtcampoNombre.getText().toString().trim();
        String correo = edtcampoCorreoElectronico.getText().toString().trim();
        String numeroTelefonico = edtcampoNumeroTelefonico.getText().toString().trim();

        if (!validarEntradas(nombre, correo, numeroTelefonico)) {
            return;
        }

        usuarioActual.getCliente().setNombreCompleto(nombre);
        usuarioActual.setCorreo(correo);
        usuarioActual.getCliente().setNumeroTelefonico(numeroTelefonico);

        viewModel.actualizarUsuario(usuarioActual).observe(this, respuestaServidor -> {
            if (respuestaServidor != null && respuestaServidor.getRpta() == 1) {
                guardarDatosUsuarioEnPreferences(usuarioActual);
                Toast.makeText(this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarEntradas(String nombre, String correo, String numeroTelefonico) {
        if (nombre.isEmpty()) {
            edtcampoNombre.setError("El nombre no puede estar vacío");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            edtcampoCorreoElectronico.setError("Ingrese una dirección de correo válida");
            return false;
        }

        if (numeroTelefonico.length() != 9) {
            edtcampoNumeroTelefonico.setError("El número de teléfono debe tener 9 dígitos");
            return false;
        }

        return true;
    }

    private void guardarDatosUsuarioEnPreferences(Usuario usuario) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String usuarioJson = new Gson().toJson(usuario);
        editor.putString("UsuarioJson", usuarioJson);
        editor.apply();
    }
}
