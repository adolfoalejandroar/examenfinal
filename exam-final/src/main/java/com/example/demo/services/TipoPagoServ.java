package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.TipoPago;
import com.example.demo.repositories.TipoPagoRep;

@Service
public class TipoPagoServ {

    @Autowired
    private TipoPagoRep repository;

    public TipoPago save(TipoPago tipoPago) {
        return repository.save(tipoPago);
    }

    public TipoPago findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<TipoPago> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
