package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.TipoDocumento;

public interface TipoDocumentoRep extends JpaRepository<TipoDocumento, Integer> {
}
