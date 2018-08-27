package ua.motorent.demo.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.dto.ResponseDto;
import ua.motorent.demo.exception.BusinessException;
import ua.motorent.demo.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@Validated
@Api(value = "admin", description = "Admin controller")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "Add moto to DB", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Moto added successfully", response = MotoDto.class)
    })
    @RequestMapping(value = "/moto", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> addMoto(@RequestBody MotoDto motoDto) {

        return sendSuccess(adminService.addMoto(motoDto));
    }

    @ApiOperation(value = "Get moto by id", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Moto got successfully", response = MotoDto.class),
            @ApiResponse(code = 500, message = "Entity is not found. Id ", response = MotoDto.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Moto id", required = true, paramType = "path", dataType = "long")
    })
    @RequestMapping(value = "/moto/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getMoto(@PathVariable Long id) throws BusinessException {

        return sendSuccess(adminService.getMoto(id));
    }

    @ApiOperation(value = "Update moto by id", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Moto updated successfully", response = MotoDto.class),
            @ApiResponse(code = 500, message = "Entity is not found. Id ", response = MotoDto.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Moto id", required = true, paramType = "path", dataType = "long")
    })
    @RequestMapping(value = "/moto/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> updateMoto(@PathVariable Long id,
                                                  @RequestBody MotoDto motoDto) throws BusinessException {

        return sendSuccess(adminService.updateMoto(id, motoDto));
    }

    @ApiOperation(value = "Remove moto by id", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Moto removed successfully", response = MotoDto.class),
            @ApiResponse(code = 500, message = "Entity is not found. Id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Moto id", required = true, paramType = "path", dataType = "long")
    })
    @RequestMapping(value = "/moto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDto> deleteMoto(@PathVariable Long id) {
        adminService.deleteMoto(id);

        return sendSuccess("Moto #" + id + " delete successful");
    }
}
