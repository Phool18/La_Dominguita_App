package la.dominga.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHandler {
    private Activity activity;
    private String permission;
    private int requestCode;

    public PermissionHandler(Activity activity, String permission, int requestCode) {
        this.activity = activity;
        this.permission = permission;
        this.requestCode = requestCode;
    }

    public boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }
}
