package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Cajero;
import com.example.demo.repositories.CajeroRep;

@Service
public class CajeroServ {

    @Autowired
    private CajeroRep repository;

    public Cajero save(Cajero cajero) {
        return repository.save(cajero);
    }

    public Cajero findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Cajero> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
