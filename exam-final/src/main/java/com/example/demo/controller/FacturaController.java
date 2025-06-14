package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
		Tienda tienda = tiendaServ.findByUUID(uuid);
		if (tienda == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tienda no existe: " + uuid);
		}

		// Validar que el vendedor no está vacío
		if (factura.vendedor == null || factura.vendedor.documento == null) {
			String message = "No hay información del vendedor";
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		}

		// Buscar vendedor
		Vendedor vendedor = vendedorServ.findByDocument(factura.vendedor.documento);
		if (vendedor == null) {
			String message = "El vendedor no existe en la tienda: " + factura.vendedor.documento;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
		}

		// Validar que el cajero no está vacío
		if (factura.cajero == null || factura.cajero.token == null) {
			String message = "No hay información del cajero";
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		}

		// Buscar cajero por token
		Cajero cajero = cajeroServ.findByToken(factura.cajero.token);
		if (cajero == null) {
			String message = "El token no corresponde a ningún cajero en la tienda";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
		}

		// El cajero debe estar asignado a la tienda
		if (cajero.getTienda().getUuid() != uuid) {
			String message = "El cajero no está asignado a esta tienda";
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, message);
		}

		// Validar que el cliente no está vacío
		if (factura.cliente == null || factura.cliente.documento == null || factura.cliente.nombre == null) {
			String message = "No hay información del cliente";
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		}

		// Validar si el documento del cliente existe. Si el cliente no existe se crea
		Cliente cliente = clienteServ.findByDocument(factura.cliente.documento);
		if (cliente == null) {
			cliente = new Cliente();
			cliente.setDocumento(factura.cliente.documento);
			cliente.setNombre(factura.cliente.nombre);
			cliente.setTipoDocumento(tipoDocumentoServ.findByType(factura.cliente.tipo_documento));
			cliente.setId(clienteServ.nextClienteId());
			cliente.setCompras(new ArrayList<Compra>());

			// Guardar al cliente
			clienteServ.save(cliente);
		}

		// Crear un listado de detalles para procesarlos por producto
		List<DetallesCompra> detalles = new ArrayList<DetallesCompra>();

		// Procesar productos
		Double total = 0.0;

		if (factura.productos == null || factura.productos.isEmpty()) {
			String message = "No hay productos asignados para esta compra";
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		}

		for (ProductoRequest prodReq : factura.productos) {

			// Busca cada producto
			Producto producto = productoServ.findByReference(prodReq.referencia);

			// Si no lo encuentra, error 404
			if (producto == null) {
				String message = "La referencia del producto " + prodReq.referencia
						+ " no existe, por favor revisar los datos" + prodReq.referencia;
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
			}

			if (prodReq.cantidad > producto.getCantidad()) {
				String message = "La cantidad a comprar supera el máximo del producto en tienda";
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, message);
			}

			// Si lo encuentra, multiplicar (precio * cantidad) - descuento
			double precio = producto.getPrecio() * prodReq.cantidad;
			double descuento = precio * (prodReq.descuento / 100.0);
			double precioFinal = precio - descuento;
			total += precioFinal;

			// Crear detalle (sin asignar compra aún)
			DetallesCompra detalle = new DetallesCompra();
			detalle.setProducto(producto);
			detalle.setCantidad(prodReq.cantidad);
			detalle.setPrecio(producto.getPrecio());
			detalle.setDescuento(descuento);
			detalles.add(detalle);
		}

		// Sumamos el impuesto a la factura si no es negativo ni nulo
		Double impuesto = factura.impuesto;
		if (impuesto != null && impuesto >= 0) {
			total += impuesto;
		}

		// Crear un listado de pagos para procesarlos
		List<Pago> pagos = new ArrayList<Pago>();

		// Procesar pagos
		Double tributo = 0.0;
		for (PagoRequest pagoReq : factura.medios_pago) {
			TipoPago tipoPago = tipoPagoServ.findByType(pagoReq.tipo_pago);
			if (tipoPago == null) {
				String message = "Tipo de pago no permitido en la tienda: " + pagoReq.tipo_pago;
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, message);
			}

			Pago pago = new Pago();
			pago.setTipoPago(tipoPago);
			pago.setTarjetaTipo(pagoReq.tipo_tarjeta);
			pago.setCuotas(pagoReq.cuotas);
			pago.setValor(pagoReq.valor);
			// NO asignar compra aún
			pagos.add(pago);

			tributo += pago.getValor();
		}

		// Si la cantidad de dinero pagado es inferior a la cantidad que se debe pagar
		String nocoincidence = "El valor de la factura no coincide con el valor total de los pagos";
		if (tributo != total) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, nocoincidence);
		}

		// Crear compra
		Compra compra = new Compra();
		compra.setCliente(cliente);
		compra.setTienda(tienda);
		compra.setVendedor(vendedor);
		compra.setCajero(cajero);
		compra.setImpuestos(impuesto);
		compra.setFecha(LocalDate.now());
		compra.setObservaciones("");
		compra.setTotal(total);

		// *** CAMBIO IMPORTANTE: Guardar la compra PRIMERO ***
		compra = compraServ.save(compra);

		// Ahora asignar la compra guardada a los pagos y guardarlos
		for (Pago pago : pagos) {
			pago.setCompra(compra);
			pagoServ.save(pago);
		}

		// Asignar la compra guardada a los detalles y guardarlos
		for (DetallesCompra detalle : detalles) {
			detalle.setCompra(compra);
			detallesCompraServ.save(detalle);
		}

		return """
				{
					"status": "success",
					"message": "La factura se ha creado correctamente con el número: %s",
					"data": {
					"numero": "%s",
					"total": "%.0f",
					"fecha": "%s"
					}
				}
				""".formatted(compra.getId(), compra.getId(), total, compra.getFecha());
	}

	// DTOs internos para mapear el JSON de entrada
	public static class FacturaRequest {
		public Double impuesto;
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