package com.solidcode.apiusers.repository.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "user_key", nullable = false)
  private String userKey;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "payment_token")
  private String paymentToken;

  @Column(name = "debit_card_number", nullable = false, unique = true)
  private String debitCardNumber;

  @Column(name = "debit_cvv", nullable = false)
  private String debitCvv;

  @Column(name = "debit_expiry", nullable = false)
  private String debitExpiry;

  @Column(name = "zilch_card_number")
  private String zilchCardNumber;

  @Column(name = "zilch_cvv")
  private String zilchCvv;

  @Column(name = "zilch_expiry")
  private String zilchExpiry;

  @Column(name = "zilch_credit_limit")
  private BigDecimal zilchCreditLimit;

  @Column(name = "created_date", updatable = false, nullable = false)
  private Timestamp createdDate;
}
