package org.money.transfers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.money.transfers.domain.Transfer;

@Getter @Setter
public class TransferDto
{
    @JsonProperty("account_debit")
    private String accountDebit;
    @JsonProperty("account_credit")
    private String accountCredit;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("date")
    private OffsetDateTime transactionDate;
    
    public TransferDto() {}

    public TransferDto( Transfer transfer ) {
        accountDebit = transfer.getAccountDebit().getCode();
        accountCredit = transfer.getAccountCredit().getCode();
        amount = transfer.getAmount();
        transactionDate = transfer.getTransactionDate();
    }
    
}
