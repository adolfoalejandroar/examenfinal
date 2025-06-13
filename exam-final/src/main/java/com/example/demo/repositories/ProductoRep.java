package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Producto;

public interface ProductoRep extends JpaRepository<Producto, Integer> {
}
