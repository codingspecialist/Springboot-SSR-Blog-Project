package shop.mtcoding.metablog.core.exception.csr;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.metablog.dto.ResponseDTO;

// 권한 없음
@Getter
public class ExceptionApi403 extends RuntimeException {
    public ExceptionApi403(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.FORBIDDEN, "forbidden", getMessage());
        return responseDto;
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}