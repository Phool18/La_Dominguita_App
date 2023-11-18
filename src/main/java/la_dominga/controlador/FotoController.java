package la_dominga.controlador;


import la_dominga.configuraciones.RespuestaServidor;
import la_dominga.entidades.Foto;
import la_dominga.servidor.FotoService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("foto")
public class FotoController {
    private FotoService service;

    public FotoController(FotoService service) {
        this.service = service;
    }

    @GetMapping
    public RespuestaServidor list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public RespuestaServidor find(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return service.downloadByFileName(fileName, request);
    }

    @PostMapping
    public RespuestaServidor save(@ModelAttribute Foto obj) {
        return service.save(obj);
    }

    public RespuestaServidor update(Long aLong, Foto obj) {
        return null;
    }

    public RespuestaServidor delete(Long aLong) {
        return null;
    }
}