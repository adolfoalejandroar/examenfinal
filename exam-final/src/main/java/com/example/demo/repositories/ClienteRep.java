package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Cliente;

public interface ClienteRep extends JpaRepository<Cliente, Integer> {
}
