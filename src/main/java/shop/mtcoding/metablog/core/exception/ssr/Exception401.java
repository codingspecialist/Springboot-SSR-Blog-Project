package shop.mtcoding.metablog.core.exception.ssr;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.metablog.dto.ResponseDTO;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
        return responseDto;
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}