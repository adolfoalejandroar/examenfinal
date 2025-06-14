package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Tienda;
import com.example.demo.repositories.TiendaRep;

@Service
public class TiendaServ {

    @Autowired
    private TiendaRep repository;

    public Tienda save(Tienda tienda) {
        return repository.save(tienda);
    }

    public Tienda findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    public Tienda findByUUID(String uuid) {
    	return repository.findAll().stream()
                .filter(t -> uuid.equals(t.getUuid()))
                .findFirst()
                .orElse(null);
    }

    public List<Tienda> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
