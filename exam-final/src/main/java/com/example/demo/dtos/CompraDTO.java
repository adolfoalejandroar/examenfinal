package com.example.demo.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    private Integer id;
    private Integer clienteId;
    private Integer tiendaId;
    private Integer vendedorId;
    private Integer cajeroId;
    private Double total;
    private Double impuestos;
    private LocalDate fecha;
    private String observaciones;
}