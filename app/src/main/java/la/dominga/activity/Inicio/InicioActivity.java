package la.dominga.activity.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import la.dominga.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity {

    Button botonIniciarSesionIdc, botonRegistrarteIdc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_app);

        botonIniciarSesionIdc = findViewById(R.id.botonIniciarSesionIdc);
        botonRegistrarteIdc = findViewById(R.id.botonRegistrarteIdc);

        botonIniciarSesionIdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega a IniciarSesionActivity
                Intent intentIniciarSesion = new Intent(InicioActivity.this, IniciarSesionActivity.class);
                startActivity(intentIniciarSesion);
            }
        });
        botonRegistrarteIdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega a RegistrarUsuarioActivity
                Intent intentRegistrarUsuario = new Intent(InicioActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intentRegistrarUsuario);
            }
        });
    }
}
