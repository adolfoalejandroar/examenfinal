package com.example.demo.entities;

import java.time.LocalDate;
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
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_id_seq")
    @SequenceGenerator(name = "compra_id_seq", sequenceName = "compra_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "cajero_id")
    private Cajero cajero;
    
    @OneToMany(mappedBy = "compra")
    private List<DetallesCompra> detallesCompras;
    
    @OneToMany(mappedBy = "compra")
    private List<Pago> pagos;

    private Double total;

    private Double impuestos;

    private LocalDate fecha;

    private String observaciones;
}

