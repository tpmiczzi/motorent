package ua.motorent.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.motorent.demo.common.dto.ResponseDto;

@Component
public class BaseController {

    public ResponseEntity<ResponseDto> sendSuccess(Object result){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setInfo(result);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> sendError(Object message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setErrorCode();
        responseDto.setInfo(message);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
