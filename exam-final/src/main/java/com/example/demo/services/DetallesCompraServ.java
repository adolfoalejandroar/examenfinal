package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.DetallesCompra;
import com.example.demo.repositories.DetallesCompraRep;

@Service
public class DetallesCompraServ {

    @Autowired
    private DetallesCompraRep repository;

    public DetallesCompra save(DetallesCompra detallesCompra) {
        return repository.save(detallesCompra);
    }

    public DetallesCompra findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<DetallesCompra> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
