package ua.motorent.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.dto.ResponseDto;
import ua.motorent.demo.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/moto", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> addMoto(@RequestBody MotoDto motoDto) {

        return sendSuccess(adminService.addMoto(motoDto));
    }

    @RequestMapping(value = "/moto/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> updateMoto(@PathVariable Long id,
                                                  @RequestBody MotoDto motoDto) {

        return sendSuccess(adminService.updateMoto(id, motoDto));
    }


}
