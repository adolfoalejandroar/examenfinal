package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CajeroDTO {
    private Integer id;
    private String nombre;
    private String documento;
    private String email;
    private String token;
    private Integer tiendaId;
}
