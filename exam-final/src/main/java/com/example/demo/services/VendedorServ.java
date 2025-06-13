package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Vendedor;
import com.example.demo.repositories.VendedorRep;

@Service
public class VendedorServ {

    @Autowired
    private VendedorRep repository;

    public Vendedor save(Vendedor vendedor) {
        return repository.save(vendedor);
    }

    public Vendedor findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vendedor> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
