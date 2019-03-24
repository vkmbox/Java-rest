package org.money.transfers.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Tasks {
    String accountName1 = "Account-1";
    String accountName2 = "Account-2";

    @Autowired
    private AccountBalanceRepository repository;

    public void lockAccount1() {
        try {
            repository.getBalanceByAccountCodeForWrite(accountName1);
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void lockAccount2() {
        try {
            repository.getBalanceByAccountCodeForWrite(accountName2);
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
    }
    
}