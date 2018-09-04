package ua.motorent.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.dto.ResponseDto;
import ua.motorent.demo.exception.BusinessException;
import ua.motorent.demo.service.MotoService;


@RestController
@RequestMapping("/api")
@Validated
@Api(value = "application", description = "Application controller")
public class ApplicationController extends BaseController {

    @Autowired
    private MotoService motoService;

    @ApiOperation(value = "Get list with all motos", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List all motos", response = MotoDto[].class)
    })
    @RequestMapping(value = "/allList", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getAllListMoto() {

        return sendSuccess(motoService.getListAllMoto());
    }

    @ApiOperation(value = "Get moto by id", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Moto by id", response = MotoDto.class),
            @ApiResponse(code = 500, message = "Entity is not found. Id ", response = MotoDto.class)
    })
    @RequestMapping(value = "/moto/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getMotoById(@PathVariable Long id) throws BusinessException {

        return sendSuccess(motoService.getMoto(id));
    }
}
