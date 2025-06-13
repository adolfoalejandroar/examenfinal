package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Vendedor;

public interface VendedorRep extends JpaRepository<Vendedor, Integer> {
}
