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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProducto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_producto_id_seq")
    @SequenceGenerator(name = "tipo_producto_id_seq", sequenceName = "tipo_producto_id_seq", allocationSize = 1)
	private Integer id;

    private String nombre;
    
    @OneToMany(mappedBy = "tipoProducto")
    private List<Producto> productos;
}

