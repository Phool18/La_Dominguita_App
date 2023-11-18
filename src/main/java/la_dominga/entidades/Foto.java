package la_dominga.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombreFoto;
    private String nombreArchivo;
    private String tipoFoto;
    private String vigencia;
    private boolean eliminado;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile file;

    @Transient
    private String urlFile;

    public Foto() {
        id = 0;
        nombreFoto = "";
        nombreArchivo = "";
        tipoFoto = "";
        vigencia = "A";
        eliminado = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombre) {
        this.nombreFoto = nombre;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String fileName) {
        this.nombreArchivo = fileName;
    }

    public String getTipoFoto() {
        return tipoFoto;
    }

    public void setTipoFoto(String extension) {
        this.tipoFoto = extension;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String estado) {
        this.vigencia = estado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getCompleteFileName() {
        return nombreArchivo + tipoFoto;
    }
}