package com.solidcode.apiusers.dto.request;

import com.solidcode.apiusers.dto.validator.ValidUUID;
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
public class UserRegisterRequest {

  @ValidUUID
  private String userKey;
}
