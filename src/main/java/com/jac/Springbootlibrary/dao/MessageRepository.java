package com.jac.Springbootlibrary.dao;

import com.jac.Springbootlibrary.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{
}
