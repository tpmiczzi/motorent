package ua.motorent.demo.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ua.motorent.demo.DemoApplicationTests;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.common.repository.MotoRepository;
import ua.motorent.demo.exception.BusinessException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminServiceTest extends DemoApplicationTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MotoRepository motoRepository;

    @Test
    @DatabaseSetup("classpath:db/fixture/moto.xml")
    public void addMoto() throws BusinessException {
        MotoDto motoDto = new MotoDto("Kawasaki", 1200, 120.00);

        Moto moto = adminService.addMoto(motoDto);

        Moto savedMoto = motoRepository.findById(moto.getId()).orElseThrow(
                () -> new BusinessException("Entity is not found."));

        Assert.assertEquals(moto.getName(), savedMoto.getName());

    }

    @Test
    public void getMoto() {
    }

    @Test
    public void updateMoto() {
    }

    @Test
    public void deleteMoto() {
    }
}