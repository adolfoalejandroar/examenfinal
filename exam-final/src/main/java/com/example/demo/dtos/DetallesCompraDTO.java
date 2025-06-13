package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesCompraDTO {
    private Integer id;
    private Integer compraId;
    private Integer productoId;
    private Integer cantidad;
    private Double precio;
    private Double descuento;
}
