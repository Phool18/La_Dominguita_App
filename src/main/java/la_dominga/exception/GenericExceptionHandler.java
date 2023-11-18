package la_dominga.exception;



import la_dominga.configuraciones.RespuestaServidor;
import la_dominga.configuraciones.Global;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespuestaServidor genericException(Exception ex) {
        return new RespuestaServidor("exception", -1, Global.OPERACION_ERRONEA, ex.getMessage());
    }
}
