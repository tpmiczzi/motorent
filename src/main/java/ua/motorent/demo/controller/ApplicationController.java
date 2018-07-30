package ua.motorent.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.motorent.demo.common.dto.ResponseDto;
import ua.motorent.demo.service.MotoService;

@RestController
@RequestMapping("/api")
@Validated
public class ApplicationController extends BaseController {

    @Autowired
    private MotoService motoService;

    @RequestMapping(value = "/allList", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getAllListMoto() {

        return sendSuccess(motoService.getListAllMoto());
    }
}
