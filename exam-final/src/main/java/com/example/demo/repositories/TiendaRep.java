package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Tienda;

public interface TiendaRep extends JpaRepository<Tienda, Integer> {
}
