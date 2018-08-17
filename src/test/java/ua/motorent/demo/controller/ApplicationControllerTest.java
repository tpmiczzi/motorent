package ua.motorent.demo.controller;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import ua.motorent.demo.DemoApplicationTests;
import ua.motorent.demo.util.JsonHelper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@AutoConfigureMockMvc
@DatabaseSetup("classpath:db/fixture/moto.xml")
public class ApplicationControllerTest extends DemoApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JsonHelper jsonHelper;

    private MockMvc mockMvc;

    private final String AUTH_HEADER = "Authorization";

    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void getAllListMoto() throws Exception {
        String user = "user";
        String password = "Qwerty";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/allList")
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));
    }

    @Test
    public void getMotoById() throws Exception {
        String user = "user";
        String password = "Qwerty";

        Long motoId = 101L;
        String checkName = "Yamaha";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/moto/" + motoId)
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));

        String resultName = jsonHelper.readPath(resultActions.andReturn().getResponse().getContentAsString(), "$.info.name").toString();

        Assert.assertEquals(checkName, resultName);
    }
}