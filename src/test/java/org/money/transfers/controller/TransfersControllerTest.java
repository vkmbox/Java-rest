package org.money.transfers.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransfersControllerTest
{
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void test1GetClientAccount() throws Exception {
        String content = "{\"code\":\"Account-2\",\"description\":\"Account 2\",\"overdraft\":10.0,\"balance\":-5}";
        mvc.perform(get("/v1.0.0/accounts/Account-2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().json(content));
    }
    
    @Test
    public void test2SaveTransferSuccessfuly() throws Exception {
        String payload = "{\"account_debit\": \"Account-2\",\"account_credit\": \"Account-3\",\"amount\": 5}"
             , content2 = "{\"code\":\"Account-2\",\"description\":\"Account 2\",\"overdraft\":10.0,\"balance\":-10}"
             , content3 = "{\"code\":\"Account-3\",\"description\":\"Account 3\",\"overdraft\":0,\"balance\":5}";
        
        //Account-2 has balance -5 and overdraft 10
        //Account-3 has balance 0 and overdraft 0
        //Sending 5 from Account-2 to Account-3
        mvc.perform(post("/v1.0.0/transfers/add").contentType(MediaType.APPLICATION_JSON)
            .content(payload)).andExpect(status().isCreated());
        //Account-2 has balance -10
        mvc.perform(get("/v1.0.0/accounts/Account-2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().json(content2));
        //Account-3 has balance 5
        mvc.perform(get("/v1.0.0/accounts/Account-3").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(content().json(content3));
    }
    
    @Test
    public void test3SaveTransferErrorInsufficientFunds() throws Exception {
        //Account-2 has balance -10 and overdraft 10
        //Account-4 has balance 0 and overdraft 0
        //Sending 0.1 from Account-2 to Account-4
        String payload = "{\"account_debit\": \"Account-2\",\"account_credit\": \"Account-4\",\"amount\": 0.1}";
        String expectedError = "Insufficient funds in the account Account-2";
        
        MvcResult mvcResult = mvc.perform(post("/v1.0.0/transfers/add")
            .contentType(MediaType.APPLICATION_JSON).content(payload))
            .andExpect(status().isUnprocessableEntity()).andReturn();
        
        String actualError = mvcResult.getResponse().getErrorMessage();
        assertEquals(expectedError, actualError);
    }
}
