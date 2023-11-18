package la_dominga.configuraciones;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class PropiedadesAlmacenamientoArchivo {
    private String uploadDir;

    public PropiedadesAlmacenamientoArchivo() {
    }

    public PropiedadesAlmacenamientoArchivo(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}