package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.TipoProducto;
import com.example.demo.repositories.TipoProductoRep;

@Service
public class TipoProductoServ {

    @Autowired
    private TipoProductoRep repository;

    public TipoProducto save(TipoProducto tipoProducto) {
        return repository.save(tipoProducto);
    }

    public TipoProducto findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<TipoProducto> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
