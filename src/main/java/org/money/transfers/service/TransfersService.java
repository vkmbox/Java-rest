package org.money.transfers.service;

import org.money.transfers.domain.AccountBalance;
import org.money.transfers.dto.BalanceDto;
import org.money.transfers.dto.TransferDto;
import org.money.transfers.repository.AccountBalanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.money.transfers.domain.Transfer;
import org.money.transfers.repository.TransferRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PessimisticLockException;

@Service
public class TransfersService
{
    public static final String UNABLE_LOCK_ACCOUNT = "Unable to lock account %s";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds in the account %s";
    public static final String ACCOUNT_NOT_FOUND = "Account %s not found";
    
    private final AccountBalanceRepository balances;
    private final TransferRepository transfers;
    
    public TransfersService(AccountBalanceRepository balances, TransferRepository transfers) {
        this.balances = balances;
        this.transfers = transfers;
    }
    
    @Transactional
    public Long saveTransaction(TransferDto dto) {
        AccountBalance debit, credit;
        if ( dto.getAccountDebit().compareTo(dto.getAccountCredit()) < 0 ) {
            debit = lockAccount(dto.getAccountDebit());
            credit = lockAccount(dto.getAccountCredit());
        } else {
            credit = lockAccount(dto.getAccountCredit());
            debit = lockAccount(dto.getAccountDebit());
        }
        if ( debit.getBalance().subtract(dto.getAmount())
            .compareTo(debit.getAccount().getOverdraft().negate()) < 0 ) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY
              , String.format(INSUFFICIENT_FUNDS, dto.getAccountDebit()));
        }
        
        Transfer transfer = new Transfer().setAccountDebit(debit.getAccount())
            .setAccountCredit(credit.getAccount()).setAmount(dto.getAmount())
            .setTransactionDate(OffsetDateTime.now());
        debit.setBalance(debit.getBalance().subtract(dto.getAmount()));
        credit.setBalance(credit.getBalance().add(dto.getAmount()));
        
        Transfer result = transfers.save(transfer);
        balances.save(debit);
        balances.save(credit);
        return result.getId();
    }
    
    private AccountBalance lockAccount( String accountCode ) {
        try {
            AccountBalance balance = balances.getBalanceByAccountCodeForWrite(accountCode);
            if (balance != null) {
                return balance;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND
                    , String.format(ACCOUNT_NOT_FOUND, accountCode));
            }
        } catch (PessimisticLockException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT
              , String.format(UNABLE_LOCK_ACCOUNT, accountCode));
        }
    }
    
    public List<TransferDto> getTransactions() {
        return transfers.findAll().stream().map(TransferDto::new).collect(Collectors.toList());
    }
    
    public BalanceDto getBalance( String accountCode ) {
        return balances.getBalanceByAccountCode(accountCode).map(BalanceDto::new).orElse(null);
    }

    public List<BalanceDto> getBalances() {
        return balances.findAll().stream().map(BalanceDto::new).collect(Collectors.toList());
    }
}
