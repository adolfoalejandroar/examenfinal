package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	private Integer id;

	private String nombre;

	private String documento;

	@ManyToOne
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;
}
