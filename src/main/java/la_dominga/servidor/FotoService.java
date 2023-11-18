package la_dominga.servidor;


import la_dominga.configuraciones.RespuestaServidor;
import la_dominga.entidades.Foto;
import la_dominga.repositorio.FotoRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

import static la_dominga.configuraciones.Global.*;

@Service
@Transactional
public class FotoService {
    private FotoRepository repo;
    private FileStorageService storageService;

    public FotoService(FotoRepository repo, FileStorageService storageService) {
        this.repo = repo;
        this.storageService = storageService;
    }

    public RespuestaServidor<Iterable<Foto>> list() {
        return new RespuestaServidor<Iterable<Foto>>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repo.list());
    }


    public RespuestaServidor find(Long aLong) {
        return null;
    }


    public RespuestaServidor save(Foto obj) {
        String fileName = (repo.findById(obj.getId())).orElse(new Foto()).getNombreArchivo();

        String originalFilename = obj.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        fileName = storageService.storeFile(obj.getFile(), fileName);

        obj.setNombreArchivo(fileName);
        obj.setTipoFoto(extension);

        return new RespuestaServidor(TIPO_DATA, RPTA_OK,OPERACION_CORRECTA,repo.save(obj));
    }

    public ResponseEntity<Resource> download(String completefileName, HttpServletRequest request) {
        Resource resource = storageService.loadResource(completefileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request) {
        Foto doc = repo.findByFileName(fileName).orElse(new Foto());
        return download(doc.getCompleteFileName(), request);
    }


    public RespuestaServidor delete(Long aLong) {
        return null;
    }

    public HashMap<String, Object> validate(Foto obj) {
        return null;
    }
}