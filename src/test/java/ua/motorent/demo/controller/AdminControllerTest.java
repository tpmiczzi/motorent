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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ua.motorent.demo.DemoApplicationTests;
import ua.motorent.demo.common.dto.MotoDto;
import ua.motorent.demo.util.JsonHelper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@AutoConfigureMockMvc
@DatabaseSetup("classpath:db/fixture/moto.xml")
public class AdminControllerTest extends DemoApplicationTests {

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
    @Transactional
    public void addMoto() throws Exception {
        String user = "admin";
        String password = "Qwerty";

        MotoDto motoDto = new MotoDto("Kawasaki", 1200, 120.00);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/admin/moto")
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .content(jsonHelper.asJsonString(motoDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));

        String resultName = jsonHelper.readPath(resultActions.andReturn().getResponse().getContentAsString(), "$.info.name").toString();

        Assert.assertEquals(motoDto.getName(), resultName);
    }

    @Test
    public void getMoto() throws Exception {
        String user = "admin";
        String password = "Qwerty";

        Long motoId = 101L;
        String checkName = "Yamaha";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/moto/" + motoId)
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));

        String resultName = jsonHelper.readPath(resultActions.andReturn().getResponse().getContentAsString(), "$.info.name").toString();

        Assert.assertEquals(checkName, resultName);
    }

    @Test
    @Transactional
    public void updateMoto() throws Exception {
        String user = "admin";
        String password = "Qwerty";

        Long motoId = 101L;
        MotoDto motoDto = new MotoDto("Kawasaki", 1200, 120.00);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/admin/moto/" + motoId)
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .content(jsonHelper.asJsonString(motoDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));

        String resultName = jsonHelper.readPath(resultActions.andReturn().getResponse().getContentAsString(), "$.info.name").toString();

        Assert.assertEquals(motoDto.getName(), resultName);
    }

    @Test
    @Transactional
    public void deleteMoto() throws Exception {
        String user = "admin";
        String password = "Qwerty";

        Long motoId = 101L;
        String answer = "Moto #" + motoId + " delete successful";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/admin/moto/" + motoId)
                        .header(AUTH_HEADER, getJwtTokenActivUser(user, password))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.info", Matchers.notNullValue()));

        String resultName = jsonHelper.readPath(resultActions.andReturn().getResponse().getContentAsString(), "$.info").toString();

        Assert.assertEquals(answer, resultName);
    }
}