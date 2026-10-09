package com.modelo;

public class LineaPedido {
    private final Producto producto;
    private final int cantidad;
    private final double precioUnitario;

    public LineaPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo.");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        this.producto = producto;
        this.cantidad = cantidad;
        // Conservamos el precio al momento de crear la línea, aunque luego cambie el precio del producto.
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return precioUnitario * cantidad; }

    public void mostrarInformacion() {
        System.out.printf("  - %s | Cantidad: %d | Unitario: $%.2f | Subtotal: $%.2f%n",
                producto.getNombre(), cantidad, precioUnitario, getSubtotal());
    }
}
