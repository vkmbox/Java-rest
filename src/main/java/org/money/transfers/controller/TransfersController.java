package org.money.transfers.controller;

import org.money.transfers.dto.BalanceDto;
import org.springframework.http.HttpStatus;
import org.money.transfers.dto.TransferDto;
import org.money.transfers.service.TransfersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1.0.0")
public class TransfersController
{
    private final TransfersService service;
    
    public TransfersController( TransfersService service ) {
        this.service = service;
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<BalanceDto> getBalances() {
        return service.getBalances();
    }
    
    @GetMapping("/accounts/{code}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDto getBalance( @PathVariable("code") String accountCode ) {
        BalanceDto dto = service.getBalance(accountCode);
        if ( dto != null ) {
            return dto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND
                , String.format(service.ACCOUNT_NOT_FOUND, accountCode));
        }
    }
  
    @PostMapping("/transfers/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addTransaction( @RequestBody TransferDto dto ) {
        return service.saveTransaction(dto);
    }
    
    @GetMapping("/transfers")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferDto> getTransactions() {
        return service.getTransactions();
    }
    
  
}
