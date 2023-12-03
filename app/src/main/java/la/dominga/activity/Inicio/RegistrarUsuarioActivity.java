package la.dominga.activity.Inicio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.time.LocalDateTime;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import la.dominga.R;
import la.dominga.entity.Cliente;
import la.dominga.entity.Picture;
import la.dominga.entity.Usuario;
import la.dominga.viewmodel.ClienteViewModel;
import la.dominga.viewmodel.PictureViewModel;
import la.dominga.viewmodel.UsuarioViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText edtcampoNombreCompleto, edtcampoCorreoElectronico, edtcampoTelefono, edtcampoContraseña; //god
    private Button botonCrearCuenta, btnSubirImagen;

    private ImageButton btnRegistroAtras;
    private UsuarioViewModel usuarioViewModel;
    private ClienteViewModel clienteViewModel;
    private File f;
    private PictureViewModel fotoViewModel;
    private CircleImageView fotoPerfil;

    private TextInputLayout textInputNombreCompleto, textInputCorreoElectronico, textInputTelefono, textInputContrasena; //god
    private TextView textoIniciarSesion;
    private final static int LOCATION_REQUEST_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        this.init();
        this.initViewModel();

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
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.clienteViewModel = vmp.get(ClienteViewModel.class);
        this.usuarioViewModel = vmp.get(UsuarioViewModel.class);
        this.fotoViewModel = vmp.get(PictureViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder los permisos para " +
                            "leer el almacenamiento, estos permisos son necesarios para poder " +
                            "escoger tu foto de perfil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podemos realizar el registro si no nos concedes los permisos para leer el almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void init() {
        //BOTONES
        botonCrearCuenta = findViewById(R.id.botonCrearCuenta);
        btnSubirImagen = findViewById(R.id.btnSubirImagen);
        //DATOS USUARIO

        fotoPerfil = findViewById(R.id.fotoPerfil);
        //EDT
        edtcampoNombreCompleto = findViewById(R.id.edtcampoNombreCompleto);
        edtcampoTelefono = findViewById(R.id.edtcampoTelefono);
        edtcampoCorreoElectronico = findViewById(R.id.edtcampoCorreoElectronico);
        edtcampoContraseña = findViewById(R.id.edtcampoContraseña);
        //TextInputLayout
        textInputCorreoElectronico = findViewById(R.id.textInputCorreoElectronico);
        textInputNombreCompleto = findViewById(R.id.textInputNombreCompleto);
        textInputTelefono = findViewById(R.id.textInputTelefono);
        textInputContrasena = findViewById(R.id.textInputContrasena);

        btnSubirImagen.setOnClickListener(v -> {
            this.cargarImagen();
        });
        botonCrearCuenta.setOnClickListener(v -> {
            this.guardarDatos();
        });
        edtcampoNombreCompleto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputNombreCompleto.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtcampoCorreoElectronico.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().contains("@example")) {
                    textInputCorreoElectronico.setError("Solo se permite correo con @example");
                } else {
                    textInputCorreoElectronico.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtcampoTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 9) {
                    textInputTelefono.setError("Solo se permite 9 números");
                } else {
                    textInputTelefono.setErrorEnabled(false);
                }
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
                if (s.length() < 5) {
                    textInputContrasena.setError("La contraseña debe tener 5 caracteres mínimo");
                } else {
                    textInputContrasena.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Seleccione la Aplicación"), 10);
    }

    private void guardarDatos() {
        Cliente c;
        if (validar()) {
            c = new Cliente();
            try {
                c.setNombreCompleto(edtcampoNombreCompleto.getText().toString());
                c.setNumeroTelefonico(edtcampoTelefono.getText().toString());
                c.setId(0);
                LocalDateTime ldt = LocalDateTime.now();
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")), somedata;
                String filename = "" + ldt.getDayOfMonth() + (ldt.getMonthValue() + 1) +
                        ldt.getYear() + ldt.getHour()
                        + ldt.getMinute() + ldt.getSecond();
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                somedata = RequestBody.create("profilePh" + filename, MediaType.parse("text/plain"));
                this.fotoViewModel.save(part, somedata).observe(this, response -> {
                    if (response.getRpta() == 1) {
                        c.setFoto(new Picture());
                        c.getFoto().setId(response.getBody().getId());
                        this.clienteViewModel.guardarCliente(c).observe(this, cResponse -> {
                            if (cResponse.getRpta() == 1) {
                                Toast.makeText(this, response.getMessage() + ", ahora procederemos a registrar sus credenciales.", Toast.LENGTH_SHORT).show();
                                int idc = cResponse.getBody().getId();
                                Usuario u = new Usuario();
                                u.setCorreo(edtcampoCorreoElectronico.getText().toString());
                                u.setClave(edtcampoContraseña.getText().toString());
                                u.setCliente(new Cliente(idc));
                                this.usuarioViewModel.save(u).observe(this, uResponse -> {
                                    //Toast.makeText(this, uResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    if (uResponse.getRpta() == 1) {
                                        //Toast.makeText(this, "Sus Datos y credenciales fueron creados correctamente", Toast.LENGTH_SHORT).show();
                                        toastRegistrado("Bienvenido nuevo usuario");
                                        this.finish();
                                    } else {
                                        toastIncorrecto("No se ha podido guardar los datos, intentelo de nuevo");
                                    }
                                });
                            } else {
                                toastIncorrecto("Uy lo sentimos ya existe un usuario con ese DNI");
                            }
                        });
                    } else {
                        toastIncorrecto("Algo esta haciendo mal revise bien sus datos");
                    }
                });
            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        } else {
            errorMessage("Por favor, complete todos los campos del formulario");
        }
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.f = new File(realPath);
            this.fotoPerfil.setImageURI(uri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    private boolean validar() {
        boolean retorno = true;
        String nombrecompleto, telefono,correo,contrasena;
        nombrecompleto = edtcampoNombreCompleto.getText().toString();
        telefono = edtcampoTelefono.getText().toString();
        correo = edtcampoCorreoElectronico.getText().toString();
        contrasena = edtcampoContraseña.getText().toString();
        if (this.f == null) {
            errorMessage("debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombrecompleto.isEmpty()) {
            textInputNombreCompleto.setError("Ingresar nombres completos");
            retorno = false;
        } else {
            textInputNombreCompleto.setErrorEnabled(false);
        }
        if (telefono.isEmpty()) {
            textInputTelefono.setError("Ingresar numero de celular MIN Y MAX 9 DIGITOS");
            retorno = false;
        } else {
            textInputTelefono.setErrorEnabled(false);
        }
        if (contrasena.isEmpty()) {
            textInputContrasena.setError("Ingresar correo @example.com");
            retorno = false;
        } else {
            textInputContrasena.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            textInputCorreoElectronico.setError("Ingresar Contraseña +5 DIGITOS");
            retorno = false;
        } else {
            textInputCorreoElectronico.setErrorEnabled(false);
        }
        return retorno;
    }
    public void toastRegistrado(String msg) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.mensaje, (ViewGroup) findViewById(R.id.ll_custom_toast_registrado));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeRegistrado);
        txtMensaje.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


}