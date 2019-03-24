package org.money.transfers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TransfersApplication
{
    public static void main(String[] args) {
        SpringApplication.run(TransfersApplication.class, args);
    }
}
