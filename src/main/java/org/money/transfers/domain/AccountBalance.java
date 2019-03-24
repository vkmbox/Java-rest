package org.money.transfers.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Entity
@Table(name="account_balance")
public class AccountBalance
{
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  
  @NotNull
  @Column(name = "balance",nullable = false)
  private BigDecimal balance = BigDecimal.ZERO;
  
  @OneToOne
  @JoinColumn(name = "id", updatable = false, nullable = false)
  private ClientAccount account;
  
}
