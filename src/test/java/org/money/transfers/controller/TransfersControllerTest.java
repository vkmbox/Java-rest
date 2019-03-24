package org.money.transfers.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
//@WebMvcTest(TransfersController.class)
public class TransfersControllerTest
{
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void getClientAccount() throws Exception {
        
        String content = "{\"code\":\"Account-2\",\"description\":\"Account 2\",\"overdraft\":10.0,\"balance\":-5}";
        
        mvc.perform(get("/v1.0.0/accounts/Account-2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().json(content));
    }
}
