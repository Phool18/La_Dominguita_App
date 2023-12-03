package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;

import la.dominga.R;
import la.dominga.activity.MainActivity;
import la.dominga.entity.Usuario;
import la.dominga.utils.DateSerializer;
import la.dominga.utils.TimeSerializer;
import la.dominga.viewmodel.UsuarioViewModel;

public class IniciarSesionActivity extends AppCompatActivity {

    private EditText edtcampoCorreoElectronico, edtcampoContraseña;
    private Button botonIniciarSesion;
    private ImageButton btnLogAtras;
    private UsuarioViewModel viewModel;
    private TextInputLayout textInputCorreoElectronico, textInputContrasena;
    private TextView textoCrearCuenta;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        this.initViewModel();
        this.init();
        ImageButton btnRetroceder = findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementa aquí la navegación de retroceso
                onBackPressed(); // Esto es típicamente utilizado para cerrar la actividad actual
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }
    private void init(){
        edtcampoCorreoElectronico = findViewById(R.id.edtcampoCorreoElectronico);
        edtcampoContraseña = findViewById(R.id.edtcampoContraseña);

        textInputCorreoElectronico = findViewById(R.id.textInputCorreoElectronico);
        textInputContrasena = findViewById(R.id.textInputContrasena);
        textoCrearCuenta = findViewById(R.id.textoCrearCuenta);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        botonIniciarSesion.setOnClickListener(view -> {
            try {
                if (validar()) {
                    viewModel.login(edtcampoCorreoElectronico.getText().toString(),edtcampoContraseña.getText().toString()).observe(this, usuarioGenericResponse -> {
                        if (usuarioGenericResponse.getRpta()== 1 ) {
                            toastCorrecto(usuarioGenericResponse.getMessage());
                            Usuario u = usuarioGenericResponse.getBody();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = preferences.edit();
                            final Gson g = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer())
                                    .create();
                            editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Usuario>() {}.getType()));
                            editor.apply();
                            edtcampoCorreoElectronico.setText("");
                            edtcampoContraseña.setText("");
                            startActivity(new Intent(this, MainActivity.class));
                        } else {
                            toastIncorrecto(usuarioGenericResponse.getMessage());
                        }
                    });
                }else{
                    toastIncorrecto("Por favor, Complete todos los campos");
                }
            }catch (Exception e){
                toastIncorrecto("Se ha producido un error al intentar loguearte : " + e.getMessage());
            }

        });
        textoCrearCuenta.setOnClickListener(v -> {
            Intent i = new Intent(this, RegistrarUsuarioActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });

        edtcampoCorreoElectronico.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputCorreoElectronico.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtcampoContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputContrasena.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private boolean validar() {
        boolean retorno = true;
        String usuario, password;
        usuario = edtcampoCorreoElectronico.getText().toString();
        password = edtcampoContraseña.getText().toString();
        if (usuario.isEmpty()) {
            textInputCorreoElectronico.setError("Ingrese su correo electrónico");
            retorno = false;
        } else {
            textInputCorreoElectronico.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            textInputContrasena.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            textInputContrasena.setErrorEnabled(false);
        }
        return retorno;
    }

    public void toastCorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast1);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
    public void toastIncorrecto(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = preferences.getString("UsuarioJson", "");
        if(!pref.equals("")){
            toastCorrecto("Parece que dejaste tu cuenta Abierta\n          Nos saltaremos el Login");
            this.startActivity(new Intent(this, MainActivity.class));
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

}