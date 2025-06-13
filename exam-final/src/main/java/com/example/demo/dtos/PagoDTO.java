package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer id;
    private Integer compraId;
    private Integer tipoPagoId;
    private String tarjetaTipo;
    private Integer cuotas;
    private Double valor;
}