package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 

import com.example.demo.entities.*;
import com.example.demo.services.*;

@RestController
@RequestMapping("/crear")
public class FacturaController {

    @Autowired
    private TiendaServ tiendaServ;
    @Autowired
    private ClienteServ clienteServ;
    @Autowired
    private ProductoServ productoServ;
    @Autowired
    private TipoDocumentoServ tipoDocumentoServ;
    @Autowired
    private CompraServ compraServ;
    @Autowired
    private DetallesCompraServ detallesCompraServ;
    @Autowired
    private PagoServ pagoServ;
    @Autowired
    private VendedorServ vendedorServ;
    @Autowired
    private CajeroServ cajeroServ;
    @Autowired
    private TipoPagoServ tipoPagoServ;

    @PostMapping("/{uuid}")
    public String crearFactura(@PathVariable String uuid, @RequestBody FacturaRequest factura) {
        // Buscar tienda
        Tienda tienda = tiendaServ.findAll().stream()
                .filter(t -> uuid.equals(t.getUuid()))
                .findFirst()
                .orElse(null);
        if (tienda == null) return "Tienda no encontrada";

        // Buscar o crear cliente
        TipoDocumento tipoDoc = tipoDocumentoServ.findAll().stream()
                .filter(td -> td.getNombre().equalsIgnoreCase(factura.cliente.tipo_documento))
                .findFirst()
                .orElse(null);
        if (tipoDoc == null) return "Tipo de documento no encontrado";

        Cliente cliente = clienteServ.findAll().stream()
                .filter(c -> c.getDocumento().equals(factura.cliente.documento))
                .findFirst()
                .orElse(null);
        if (cliente == null) {
            cliente = new Cliente(null, factura.cliente.nombre, factura.cliente.documento, tipoDoc, new ArrayList<>());
            cliente = clienteServ.save(cliente);
        }

        // Buscar vendedor
        Vendedor vendedor = vendedorServ.findAll().stream()
                .filter(v -> v.getDocumento().equals(factura.vendedor.documento))
                .findFirst()
                .orElse(null);
        if (vendedor == null) return "Vendedor no encontrado";

        // Buscar cajero por token
        Cajero cajero = cajeroServ.findAll().stream()
                .filter(c -> factura.cajero.token.equals(c.getToken()))
                .findFirst()
                .orElse(null);
        if (cajero == null) return "Cajero no encontrado";

        // Crear compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setImpuestos(factura.impuesto);
        compra.setFecha(LocalDate.now());
        compra.setObservaciones("");
        compra.setTotal(0.0); // Se calcula abajo
        compra = compraServ.save(compra);

        double total = 0.0;
        // Procesar productos
        for (ProductoRequest prodReq : factura.productos) {
            Producto producto = productoServ.findAll().stream()
                    .filter(p -> p.getReferencia().equals(prodReq.referencia))
                    .findFirst()
                    .orElse(null);
            if (producto == null) return "Producto no encontrado: " + prodReq.referencia;

            double precio = producto.getPrecio() * prodReq.cantidad;
            double descuento = precio * (prodReq.descuento / 100.0);
            double precioFinal = precio - descuento;
            total += precioFinal;

            DetallesCompra detalle = new DetallesCompra(null, compra, producto, prodReq.cantidad, producto.getPrecio(), descuento);
            detallesCompraServ.save(detalle);
        }
        compra.setTotal(total + factura.impuesto);
        compraServ.save(compra);

        // Procesar pagos
        for (PagoRequest pagoReq : factura.medios_pago) {
            TipoPago tipoPago = tipoPagoServ.findAll().stream()
                    .filter(tp -> tp.getNombre().equalsIgnoreCase(pagoReq.tipo_pago))
                    .findFirst()
                    .orElse(null);
            if (tipoPago == null) return "Tipo de pago no encontrado: " + pagoReq.tipo_pago;

            Pago pago = new Pago(null, compra, tipoPago, pagoReq.tipo_tarjeta, pagoReq.cuotas, pagoReq.valor);
            pagoServ.save(pago);
        }

        return "Factura registrada correctamente";
    }

    // DTOs internos para mapear el JSON de entrada
    public static class FacturaRequest {
        public double impuesto;
        public ClienteRequest cliente;
        public List<ProductoRequest> productos;
        public List<PagoRequest> medios_pago;
        public VendedorRequest vendedor;
        public CajeroRequest cajero;
    }

    public static class ClienteRequest {
        public String documento;
        public String nombre;
        public String tipo_documento;
    }

    public static class ProductoRequest {
        public String referencia;
        public int cantidad;
        public double descuento;
    }

    public static class PagoRequest {
        public String tipo_pago;
        public String tipo_tarjeta;
        public Integer cuotas;
        public Double valor;
    }

    public static class VendedorRequest {
        public String documento;
    }

    public static class CajeroRequest {
        public String token;
    }
}
