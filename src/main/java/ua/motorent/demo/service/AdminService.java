package ua.motorent.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.common.repository.MotoRepository;

@Service
public class AdminService {

    @Autowired
    private MotoRepository motoRepository;

    public Moto addMoto(MotoDto motoDto){
        Moto moto = new Moto(motoDto.getName(), motoDto.getVolume(), motoDto.getPrice());

        return motoRepository.save(moto);
    }

    public Moto updateMoto(Long id, MotoDto motoDto){
        Moto moto = motoRepository.getOne(id);

        if (motoDto.getName() != null){
            moto.setName(motoDto.getName());
        }

        if (motoDto.getPrice() != null){
            moto.setPrice(motoDto.getPrice());
        }

        if (motoDto.getVolume() != 0){
            moto.setVolume(motoDto.getVolume());
        }

        return motoRepository.save(moto);
    }
}
