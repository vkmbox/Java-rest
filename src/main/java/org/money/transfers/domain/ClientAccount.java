package org.money.transfers.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Accessors(chain = true)
@Entity
@Table(name="client_account")
public class ClientAccount
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  
  @NotNull
  @Column(name = "code",nullable = false)
  private String code;

  @Column(name = "description",nullable = true)
  private String description;
  
  @NotNull
  @PositiveOrZero
  @Column(name = "overdraft",nullable = false)
  private BigDecimal overdraft = BigDecimal.ZERO;
  
}
