package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.TipoPago;

public interface TipoPagoRep extends JpaRepository<TipoPago, Integer> {
}
