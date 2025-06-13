package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Pago;
import com.example.demo.repositories.PagoRep;

@Service
public class PagoServ {

    @Autowired
    private PagoRep repository;

    public Pago save(Pago pago) {
        return repository.save(pago);
    }

    public Pago findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Pago> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
