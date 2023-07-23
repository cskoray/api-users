package com.solidcode.apiusers.adaptor;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.solidcode.apiusers.adaptor.request.ZilchUserRequest;
import com.solidcode.apiusers.adaptor.response.ZilchUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("ZILCH-PAY")
public interface ZilchClient {

  @PostMapping(value = "/v1/api/zilch/users/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  ZilchUserResponse register(ZilchUserRequest request);
}
