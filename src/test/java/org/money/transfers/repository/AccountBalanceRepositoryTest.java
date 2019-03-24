package org.money.transfers.repository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class AccountBalanceRepositoryTest
{
    @Autowired
    private AccountBalanceRepository repository;
    
    @Autowired
    private Tasks tasks;
    
    @Test(expected = PessimisticLockingFailureException.class)
    public void testAccountLock1() throws Throwable {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(2);
            Future<?> submit1 = service.submit(tasks::lockAccount1);
            Future<?> submit2 = service.submit(tasks::lockAccount1);
            try {
                submit1.get(10, TimeUnit.SECONDS);
                submit2.get(10, TimeUnit.SECONDS);
            } catch ( ExecutionException ex ) {
                throw ex.getCause();
            }
        } finally {
            if (service != null) service.shutdown();
        }
    }
    
    @Test
    public void testAccountLock12() throws Throwable {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(2);
            Future<?> submit1 = service.submit(tasks::lockAccount1);
            Future<?> submit2 = service.submit(tasks::lockAccount2);
            try {
                submit1.get(10, TimeUnit.SECONDS);
                submit2.get(10, TimeUnit.SECONDS);
            } catch ( ExecutionException ex ) {
                throw ex.getCause();
            }
        } finally {
            if (service != null) service.shutdown();
        }
    }
    
}
