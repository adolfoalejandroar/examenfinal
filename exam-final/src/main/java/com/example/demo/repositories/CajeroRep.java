package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Cajero;

public interface CajeroRep extends JpaRepository<Cajero, Integer> {
}
