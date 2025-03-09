package com.schummi.banktmsystemservice;


import com.alibaba.fastjson.JSON;
import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest(classes = TransactionMainApplication.class)
@AutoConfigureMockMvc
public class TransactionServiceTest {
    @Autowired
    private MockMvc mockMvc;

    private UserTMInfoBean getUserTMInfo() {
        UserTMInfoBean userTMInfo = new UserTMInfoBean();
        userTMInfo.setUserId(123L);
        userTMInfo.setUserAmount(BigDecimal.valueOf(200.0));
        userTMInfo.setIsSaveMoney("TRUE");
        userTMInfo.setUserTransactionTime(System.currentTimeMillis());
        return userTMInfo;
    }

    @Test
    public void testCreateTransactionNormal() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(userTMInfoBean)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateTransactionIllegalParam() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        userTMInfoBean.setUserAmount(BigDecimal.valueOf(90));
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(userTMInfoBean)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    public void testDeleteTransaction() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userTMInfoBean)));
        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/deleteTransaction/{userId}", userTMInfoBean.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUnExistTransaction() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userTMInfoBean)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/deleteTransaction/{userId}", userTMInfoBean.getUserId() - 1))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testModifyTransaction() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userTMInfoBean)));
        userTMInfoBean.setUserAmount(BigDecimal.valueOf(600.0));
        mockMvc.perform(MockMvcRequestBuilders.put("/transactions/modifyTransaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(userTMInfoBean)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testQueryTransactions() throws Exception {
        UserTMInfoBean userTMInfoBean = getUserTMInfo();
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/createTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userTMInfoBean)));
        // 执行 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("queryTransactions/{userId}", userTMInfoBean.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}