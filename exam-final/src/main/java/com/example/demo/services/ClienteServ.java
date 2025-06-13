package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Cliente;
import com.example.demo.repositories.ClienteRep;

@Service
public class ClienteServ {
	
	@Autowired
	private ClienteRep repository;

	// Create or update a Cliente
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	// Read a Cliente by ID
	public Cliente findById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	// Read all Cliente
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// Delete a Cliente by ID
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
