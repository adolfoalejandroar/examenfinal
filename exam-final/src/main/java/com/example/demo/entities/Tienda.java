package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tienda_id_seq")
    @SequenceGenerator(name = "tienda_id_seq", sequenceName = "tienda_id_seq", allocationSize = 1)
	private Integer id;
	
	private String nombre;
	
	private String direccion;
	
	private String uuid;
	
	@OneToMany(mappedBy = "tienda")
	private List<Cajero> cajeros;
	
	@OneToMany(mappedBy = "tienda")
	private List<Compra> compras;
}
