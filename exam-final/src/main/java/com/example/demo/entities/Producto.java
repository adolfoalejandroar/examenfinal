package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_id_seq")
    @SequenceGenerator(name = "producto_id_seq", sequenceName = "producto_id_seq", allocationSize = 1)
	private Integer id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer cantidad;

    private String referencia;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;
    
    @OneToMany(mappedBy = "producto")
    private List<DetallesCompra> compras;
}
