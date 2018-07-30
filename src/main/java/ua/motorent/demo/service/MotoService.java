package ua.motorent.demo.service;

import org.springframework.stereotype.Service;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.common.repository.MotoRepository;

import java.util.List;

@Service
public class MotoService {

    private final MotoRepository motoRepository;

    public MotoService(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    public List<Moto> getListAllMoto(){
        List<Moto> motoList = motoRepository.findAll();

        return motoList;
    }

}
