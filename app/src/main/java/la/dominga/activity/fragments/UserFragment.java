package la.dominga.activity.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import la.dominga.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import la.dominga.activity.Inicio.EditarPerfilActivity;

public class UserFragment extends Fragment {

    private Button btnCerrarSesion, btnEditarPerfil;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Inicializar botones
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);

        // Escuchador para cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> mostrarDialogoSalida());

        // Escuchador para editar perfil
        btnEditarPerfil.setOnClickListener(v -> abrirEditarPerfilActivity());

        return view;
    }

    private void mostrarDialogoSalida() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Quieres cerrar la sesión?")
                .setContentText("Puedes iniciar sesión nuevamente más tarde.")
                .setCancelText("No, cancelar")
                .setConfirmText("Sí, cerrar sesión")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> sDialog.dismissWithAnimation())
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    borrarPreferenciasInicioSesion();
                    getActivity().finish();  // Cierra la actividad contenedora
                }).show();
    }

    private void borrarPreferenciasInicioSesion() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
    }
    private void abrirEditarPerfilActivity() {
        Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
        startActivity(intent);
    }
}

