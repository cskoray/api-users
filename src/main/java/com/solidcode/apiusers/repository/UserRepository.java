package com.solidcode.apiusers.repository;


import com.solidcode.apiusers.repository.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserKey(String userKey);

  Optional<User> findByPaymentToken(String paymentToken);
}
