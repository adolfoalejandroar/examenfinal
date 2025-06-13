package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductoRep;

@Service
public class ProductoServ {

    @Autowired
    private ProductoRep repository;

    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    public Producto findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Producto> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
