package ua.motorent.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.common.repository.MotoRepository;
import ua.motorent.demo.exception.BusinessException;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getListAllMoto() {
        List<Moto> motoList = motoRepository.findAll();

        return motoList;
    }

    public Moto getMoto(Long id) throws BusinessException {
        return motoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Entity is not found. Id - " + id));
    }
}
