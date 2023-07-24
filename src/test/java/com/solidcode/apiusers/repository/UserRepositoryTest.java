package com.solidcode.apiusers.repository;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.solidcode.apiusers.repository.entity.User;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

  private static final String KEY = UUID.randomUUID().toString();

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    User user = User.builder()
        .userKey(KEY)
        .name("name")
        .email("a@a.com")
        .debitCardNumber("1111222233334444")
        .debitCvv("123")
        .debitExpiry("1228")
        .createdDate(Timestamp.valueOf(now()))
        .build();
    userRepository.save(user);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  public void findByUserKey() {

    Optional<User> byUserKey = userRepository.findByUserKey(KEY);

    assertThat(byUserKey, is(not(Optional.empty())));
    assertEquals(byUserKey.get().getUserKey(), KEY);
  }
}