package com.metrics.linebot.repository;

import org.springframework.stereotype.Repository;

import com.metrics.linebot.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
