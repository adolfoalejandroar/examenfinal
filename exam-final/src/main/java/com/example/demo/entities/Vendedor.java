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
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendedor_id_seq")
    @SequenceGenerator(name = "vendedor_id_seq", sequenceName = "vendedor_id_seq", allocationSize = 1)
    private Integer id;

    private String nombre;

    private String documento;

    private String email;
    
    @OneToMany(mappedBy = "vendedor")
    private List<Compra> compras;
}

