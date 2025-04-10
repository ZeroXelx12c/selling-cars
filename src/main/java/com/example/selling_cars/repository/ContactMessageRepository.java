package com.example.selling_cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.selling_cars.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}