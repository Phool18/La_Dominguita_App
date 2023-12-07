package la.dominga.activity.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import la.dominga.Connector.Connector;
import la.dominga.R;
import la.dominga.activity.Inicio.EditarPerfilActivity;
import la.dominga.activity.Inicio.HistorialDeComprasActivity;
import la.dominga.entity.Cliente;
import la.dominga.entity.Usuario;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserFragment extends Fragment {

    private static final int EDITAR_PERFIL_REQUEST_CODE = 1;

    private TextView tvUserName, tvUserEmail, tvUserPhone;
    private ImageView imgFotoPerfil;
    private Button btnCerrarSesion, btnEditarPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Inicializar componentes de UI
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserPhone = view.findViewById(R.id.tvUserPhone);
        imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        cargarDatosUsuario();
        btnCerrarSesion.setOnClickListener(v -> mostrarDialogoSalida());
        btnEditarPerfil.setOnClickListener(v -> abrirEditarPerfilActivity());
        Button btnHistorial = view.findViewById(R.id.btnHistorial);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirHistorialDeCompras();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String usuarioJson = preferences.getString("UsuarioJson", "");
        if (!usuarioJson.isEmpty()) {
            Usuario usuario = new Gson().fromJson(usuarioJson, Usuario.class);
            actualizarVistaConDatosUsuario(usuario);
        }
    }

    private void actualizarVistaConDatosUsuario(Usuario usuario) {
        Cliente cliente = usuario.getCliente();
        tvUserEmail.setText(usuario.getCorreo());
        if (cliente != null) {
            tvUserName.setText(cliente.getNombreCompleto());
            tvUserPhone.setText(cliente.getNumeroTelefonico());
            cargarFotoPerfil(cliente);
        }
    }

    private void cargarFotoPerfil(Cliente cliente) {
        if (cliente.getFoto() != null && cliente.getFoto().getNombreArchivo() != null) {
            String url = Connector.baseUrlE + "/foto/download/" + cliente.getFoto().getNombreArchivo();
            Picasso.get().load(url)
                    .error(R.drawable.image_not_found)
                    .into(imgFotoPerfil);
        } else {
            imgFotoPerfil.setImageResource(R.drawable.image_not_found);
        }
    }

    private void mostrarDialogoSalida() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Quieres cerrar la sesión?")
                .setContentText("Puedes iniciar sesión nuevamente más tarde.")
                .setCancelText("No, cancelar")
                .setConfirmText("Sí, cerrar sesión")
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    borrarPreferenciasInicioSesion();
                    getActivity().finish();
                })
                .show();
    }
    private void abrirHistorialDeCompras() {
        Intent intent = new Intent(getActivity(), HistorialDeComprasActivity.class);
        startActivity(intent);
    }
    private void borrarPreferenciasInicioSesion() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
    }

    private void abrirEditarPerfilActivity() {
        startActivityForResult(new Intent(getActivity(), EditarPerfilActivity.class), EDITAR_PERFIL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITAR_PERFIL_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            cargarDatosUsuario();
        }
    }
}
