package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pago_id_seq")
    @SequenceGenerator(name = "pago_id_seq", sequenceName = "pago_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id")
    private TipoPago tipoPago;

    private String tarjetaTipo; // 'VISA' o 'MASTERCARD'

    private Integer cuotas;

    private Double valor;
}

