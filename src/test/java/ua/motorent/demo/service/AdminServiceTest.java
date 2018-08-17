package ua.motorent.demo.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import ua.motorent.demo.DemoApplicationTests;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.common.repository.MotoRepository;
import ua.motorent.demo.exception.BusinessException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:db/fixture/moto.xml")
public class AdminServiceTest extends DemoApplicationTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MotoRepository motoRepository;

    @Test
    @Transactional
    public void addMoto() throws BusinessException {
        MotoDto motoDto = new MotoDto("Kawasaki", 1200, 120.00);

        Moto moto = adminService.addMoto(motoDto);

        Moto savedMoto = motoRepository.findById(moto.getId()).orElseThrow(
                () -> new BusinessException("Entity is not found."));

        Assert.assertEquals(moto.getName(), savedMoto.getName());
    }

    @Test
    public void getMoto() throws BusinessException {
        Long id = 101L;

        Moto moto = adminService.getMoto(id);

        Assert.assertEquals("Yamaha", moto.getName());
    }

    @Test
    @Transactional
    public void updateMoto() throws BusinessException {
        Long id = 101L;
        MotoDto motoDtoForUpdate = new MotoDto();
        motoDtoForUpdate.setVolume(500);
        Moto newMoto = adminService.updateMoto(id, motoDtoForUpdate);

        Assert.assertEquals(500, newMoto.getVolume());
    }

    @Test
    @Transactional
    public void deleteMoto() {
        Long id = 101L;
        adminService.deleteMoto(id);

        Moto moto = null;

        try {
            moto = motoRepository.findById(id).orElseThrow(() -> new BusinessException("Entity is not found. Id - " + id));
        } catch (BusinessException bEx) {
            Assert.assertEquals(bEx.getMessage(), "Entity is not found. Id - 101");
        }

        Assert.assertNull(moto);
    }
}