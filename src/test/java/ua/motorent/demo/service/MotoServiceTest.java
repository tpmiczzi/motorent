package ua.motorent.demo.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ua.motorent.demo.DemoApplicationTests;
import ua.motorent.demo.common.model.Moto;
import ua.motorent.demo.exception.BusinessException;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class MotoServiceTest extends DemoApplicationTests {

    @Autowired
    private MotoService motoService;

    @Test
    @DatabaseSetup("classpath:db/fixture/moto.xml")
    public void getListAllMoto() {
        List<Moto> motoList = motoService.getListAllMoto();
        Assert.assertTrue(motoList.size() > 1);
    }

    @Test
    @DatabaseSetup("classpath:db/fixture/moto.xml")
    public void getMoto() throws BusinessException {
        Long id = 101L;
        String checkName = "Yamaha";

        Moto moto = motoService.getMoto(id);

        Assert.assertEquals(checkName, moto.getName());
    }
}