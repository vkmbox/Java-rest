package org.money.transfers.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Entity
@Table(name="transaction_log")
public class Transfer
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotNull
  @OneToOne
  @JoinColumn(name = "account_debit", updatable = false, nullable = false)
  private ClientAccount accountDebit;
  
  @NotNull
  @OneToOne
  @JoinColumn(name = "account_credit", updatable = false, nullable = false)
  private ClientAccount accountCredit;
  
  @NotNull
  @Column(name = "transaction_date", updatable = false, nullable = false)
  private OffsetDateTime transactionDate;
  
  @Column(name = "amount",nullable = false)
  private BigDecimal amount = BigDecimal.ZERO;
  
}
