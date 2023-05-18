package com.messenger.publisher.repositoty;

import com.messenger.publisher.entety.UserMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<UserMessage, String> {
}