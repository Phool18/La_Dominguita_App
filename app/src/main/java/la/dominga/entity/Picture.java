package la.dominga.entity;

import okhttp3.MultipartBody;

public class Picture {

    private long id;
    private String nombreFoto;
    private String nombreArchivo;
    private String tipoFoto;
    private String vigencia;
    private boolean eliminado;

    private MultipartBody.Part file;

    private String urlFile;

    public Picture() {
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

    public MultipartBody.Part getFile() {
        return file;
    }

    public void setFile(MultipartBody.Part file) {
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