package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entities.*;
import com.example.demo.services.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultar")
public class CajeroController {

    @Autowired
    private TiendaServ tiendaServ;
    @Autowired
    private CajeroServ cajeroServ;
    @Autowired
    private CompraServ compraServ;
    @Autowired
    private DetallesCompraServ detallesCompraServ;

    @PostMapping("/{uuid}")
    public FacturaResponse consultarFactura(@PathVariable String uuid, @RequestBody ConsultaRequest consulta) {
        
        // Buscar tienda
        Tienda tienda = tiendaServ.findByUUID(uuid);
        if (tienda == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tienda no existe: " + uuid);
        }

        // Validar que el token no está vacío
        if (consulta.token == null || consulta.token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token del cajero es requerido");
        }

        // Buscar cajero por token
        Cajero cajero = cajeroServ.findByToken(consulta.token);
        if (cajero == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El token no corresponde a ningún cajero");
        }

        // Validar que el cajero pertenece a la tienda
        if (!cajero.getTienda().getUuid().equals(uuid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El cajero no tiene permisos para consultar facturas de esta tienda");
        }

        // Validar datos de entrada
        if (consulta.cliente == null || consulta.cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Documento del cliente es requerido");
        }

        if (consulta.factura == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número de factura es requerido");
        }

        // Buscar la compra/factura
        Compra compra = compraServ.findById(consulta.factura);
        if (compra == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La factura no existe: " + consulta.factura);
        }

        // Validar que la factura pertenece a la tienda
        if (!compra.getTienda().getUuid().equals(uuid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La factura no pertenece a esta tienda");
        }

        // Validar que la factura corresponde al cliente
        if (!compra.getCliente().getDocumento().equals(consulta.cliente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La factura no corresponde al cliente especificado");
        }

        // Obtener los detalles de la compra
        List<DetallesCompra> detalles = detallesCompraServ.findByCompra(compra);

        // Construir la respuesta
        FacturaResponse response = new FacturaResponse();
        response.total = compra.getTotal();
        response.impuestos = compra.getImpuestos();
        
        // Información del cliente
        response.cliente = new ClienteResponse();
        response.cliente.documento = compra.getCliente().getDocumento();
        response.cliente.nombre = compra.getCliente().getNombre();
        response.cliente.tipo_documento = compra.getCliente().getTipoDocumento().getNombre();

        // Información del cajero
        response.cajero = new CajeroResponse();
        response.cajero.documento = compra.getCajero().getDocumento();
        response.cajero.nombre = compra.getCajero().getNombre();

        // Información de los productos
        response.productos = detalles.stream().map(detalle -> {
            ProductoResponse producto = new ProductoResponse();
            producto.referencia = detalle.getProducto().getReferencia();
            producto.nombre = detalle.getProducto().getNombre();
            producto.cantidad = detalle.getCantidad();
            producto.precio = detalle.getPrecio();
            producto.descuento = detalle.getDescuento();
            producto.subtotal = (detalle.getPrecio() * detalle.getCantidad()) - detalle.getDescuento();
            return producto;
        }).collect(Collectors.toList());

        return response;
    }

    // DTOs para el request
    public static class ConsultaRequest {
        public String token;
        public String cliente;
        public Integer factura;
    }

    // DTOs para la respuesta
    public static class FacturaResponse {
        public Double total;
        public Double impuestos;
        public ClienteResponse cliente;
        public List<ProductoResponse> productos;
        public CajeroResponse cajero;
    }

    public static class ClienteResponse {
        public String documento;
        public String nombre;
        public String tipo_documento;
    }

    public static class ProductoResponse {
        public String referencia;
        public String nombre;
        public Integer cantidad;
        public Double precio;
        public Double descuento;
        public Double subtotal;
    }

    public static class CajeroResponse {
        public String documento;
        public String nombre;
    }
}
