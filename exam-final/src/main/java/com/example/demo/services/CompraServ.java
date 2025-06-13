package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Compra;
import com.example.demo.repositories.CompraRep;

@Service
public class CompraServ {

    @Autowired
    private CompraRep repository;

    public Compra save(Compra compra) {
        return repository.save(compra);
    }

    public Compra findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Compra> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
