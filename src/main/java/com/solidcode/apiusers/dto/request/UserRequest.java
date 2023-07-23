package com.solidcode.apiusers.dto.request;

import com.solidcode.apiusers.dto.validator.ValidCardNumber;
import com.solidcode.apiusers.dto.validator.ValidCvv;
import com.solidcode.apiusers.dto.validator.ValidEmail;
import com.solidcode.apiusers.dto.validator.ValidExpiryDate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserRequest {

  @NotNull
  @Size(min = 3, max = 35)
  private String name;

  @ValidEmail
  @NotNull
  @NotEmpty
  private String email;

  @ValidCardNumber
  @NotNull
  @NotEmpty
  private String cardNumber;

  @NotNull
  @ValidExpiryDate
  private String expiryDate;

  @NotNull
  @ValidCvv
  private String cvv;
}
