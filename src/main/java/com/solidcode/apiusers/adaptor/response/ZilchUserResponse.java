package com.solidcode.apiusers.adaptor.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZilchUserResponse {

  private String paymentToken;
  private String cardNumber;
  private String expiryDate;
  private String cvv;
  private BigDecimal creditLimit;
}
