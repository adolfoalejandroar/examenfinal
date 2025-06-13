package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cajero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cajero_id_seq")
    @SequenceGenerator(name = "cajero_id_seq", sequenceName = "cajero_id_seq", allocationSize = 1)
	private Integer id;
	
	private String nombre;
	
	private String documento;
	
	private String email;
	
	private String token;
	
	@ManyToOne
	@JoinColumn(name = "tienda_id")
	private Tienda tienda;
}
