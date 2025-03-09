package com.schummi.banktmsystemservice.client;

import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class TransactionController {

    @Autowired
    private TransactionClient transactionClient;

    @GetMapping("/transactions")
    public String showTransactionsPage(Model model) {
        ResponseEntity<List<UserTMInfoBean>> res = transactionClient.queryTransactions(1L);
        List<UserTMInfoBean> transactions = Objects.requireNonNull(res.getBody());
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}
