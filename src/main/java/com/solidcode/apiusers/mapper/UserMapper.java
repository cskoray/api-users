package com.solidcode.apiusers.mapper;

import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.adaptor.request.ZilchUserRequest;
import com.solidcode.apiusers.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "debitCardNumber", source = "cardNumber")
  @Mapping(target = "debitCvv", source = "cvv")
  @Mapping(target = "debitExpiry", source = "expiryDate")
  User toUser(UserRequest userRequest);

  @Mapping(target = "cardNumber", source = "debitCardNumber")
  @Mapping(target = "cvv", source = "debitCvv")
  @Mapping(target = "expiryDate", source = "debitExpiry")
  ZilchUserRequest toZilchUserRequest(User user);
}
