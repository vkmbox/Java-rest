package org.money.transfers.dto;

import lombok.Getter;
import lombok.Setter;
import org.money.transfers.domain.AccountBalance;

import java.math.BigDecimal;

@Getter @Setter
public class BalanceDto
{
  private String code;
  private String description;
  private BigDecimal overdraft;
  private BigDecimal balance;
  
  public BalanceDto( AccountBalance entity ) {
    code = entity.getAccount().getCode();
    description = entity.getAccount().getDescription();
    overdraft = entity.getAccount().getOverdraft();
    balance = entity.getBalance();
  }
}
