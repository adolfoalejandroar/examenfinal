package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String status;
	private String message;
	private Object data;

	public ErrorResponse(String message) {
		this.status = "error";
		this.message = message;
		this.data = null;
	}
}
