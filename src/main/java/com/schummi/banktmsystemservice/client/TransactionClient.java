package com.schummi.banktmsystemservice.client;

import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import com.schummi.banktmsystemservice.exception.TransactionException;
import com.schummi.banktmsystemservice.service.ITMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口调用
 *
 * @author jiahui
 * @since 2025-03-08
 */
@RestController
@RequestMapping("/transactions")
@Tag(name = "用户交易管理API", description = "主要记录用户资金交易情况")
public class TransactionClient {
    @Autowired
    private ITMService tmService;

    @PostMapping("createTransaction")
    @Operation(summary = "创建用户交易记录", description = "创建一条用户交易记录")
    public ResponseEntity<String> createTransaction(@RequestBody @NotNull @Valid UserTMInfoBean userTMInfo) {
        try {
            tmService.createTransaction(userTMInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionException exp) {
            return exp.getHttpCode() == 400 ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    exp.getHttpCode() == 404 ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteTransaction/{userId}")
    @Operation(summary = "删除用户交易记录", description = "返回操作是否成功")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long userId) {
        try {
            tmService.deleteTransaction(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionException exp) {
            return exp.getHttpCode() == 400 ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    exp.getHttpCode() == 404 ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("modifyTransaction")
    @Operation(summary = "更新用户交易记录", description = "返回操作是否成功")
    public ResponseEntity<Void> modifyTransaction(@RequestBody @NotNull @Valid UserTMInfoBean userTMInfo) {
        try {
            tmService.modifyTransaction(userTMInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionException exp) {
            return exp.getHttpCode() == 400 ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    exp.getHttpCode() == 404 ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("queryTransactions/{userId}")
    @Operation(summary = "查询用户所有的交易记录", description = "返回操作是否成功")
    public ResponseEntity<List<UserTMInfoBean>> queryTransactions(@PathVariable long userId) {
        try {
            List<UserTMInfoBean> userTMInfoBeanList = tmService.queryTransactions(userId);
            return new ResponseEntity<>(userTMInfoBeanList, HttpStatus.OK);
        } catch (TransactionException exp) {
            return exp.getHttpCode() == 400 ? new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) :
                    exp.getHttpCode() == 404 ? new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND) :
                            new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/transactions")
    public String showTransactionsPage() {
        return "transactions";
    }
}
