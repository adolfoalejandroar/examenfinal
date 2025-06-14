package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Compra;
import com.example.demo.entities.DetallesCompra;

public interface DetallesCompraRep extends JpaRepository<DetallesCompra, Integer> {

	List<DetallesCompra> findByCompra(Compra compra);
}
