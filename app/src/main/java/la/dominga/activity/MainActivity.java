package la.dominga.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import la.dominga.R;
import la.dominga.activity.fragments.PanFragment;
import la.dominga.activity.fragments.SombreroFragment;
import la.dominga.activity.fragments.UserFragment;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar con PanFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PanFragment())
                    .commit();
        }

        BottomNavigationView nav = findViewById(R.id.nav_bar);
        nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.pan:
                    selectedFragment = new PanFragment();
                    break;
                case R.id.sombrero:
                    selectedFragment = new SombreroFragment();
                    break;
                case R.id.user:
                    selectedFragment = new UserFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        });
    }
}


