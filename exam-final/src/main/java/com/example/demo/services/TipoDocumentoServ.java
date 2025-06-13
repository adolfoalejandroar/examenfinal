package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.repositories.TipoDocumentoRep;

@Service
public class TipoDocumentoServ {

    @Autowired
    private TipoDocumentoRep repository;

    public TipoDocumento save(TipoDocumento tipoDocumento) {
        return repository.save(tipoDocumento);
    }

    public TipoDocumento findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<TipoDocumento> findAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
