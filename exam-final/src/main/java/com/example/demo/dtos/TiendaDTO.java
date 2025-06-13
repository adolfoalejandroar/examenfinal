package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String uuid;
}