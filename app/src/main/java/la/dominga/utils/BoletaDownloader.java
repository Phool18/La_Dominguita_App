package la.dominga.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import okhttp3.ResponseBody;

public class BoletaDownloader {
    private Context context;

    public BoletaDownloader(Context context) {
        this.context = context;
    }

    public void descargarYMostrarBoleta(ResponseBody body, String fileName) {
        try {
            // Crear un archivo en el directorio de descargas
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(body.bytes());
            fos.close();

            Toast.makeText(context, "Boleta descargada en: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Abrir el archivo PDF automáticamente
            abrirArchivoPdf(file);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al escribir el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirArchivoPdf(File file) {
        try {
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "No se pudo abrir el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}
