package com.servicio;

import com.modelo.LineaPedido;
import com.modelo.Pedido;
import com.modelo.Producto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GestorPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();

    /**
     * Recibe ID de producto -> cantidad. Primero valida todas las cantidades y el stock;
     * solo si todo es válido descuenta stock y registra el pedido.
     */
    public Pedido crearPedido(Map<Integer, Integer> cantidades, GestorProductos gestorProductos) {
        if (cantidades == null || cantidades.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe incluir al menos un producto.");
        }

        Map<Producto, Integer> productosValidados = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entrada : cantidades.entrySet()) {
            Producto producto = gestorProductos.buscarPorId(entrada.getKey());
            Integer cantidad = entrada.getValue();
            if (producto == null) throw new IllegalArgumentException("No existe el producto con ID " + entrada.getKey() + ".");
            if (cantidad == null || cantidad <= 0) throw new IllegalArgumentException("Todas las cantidades deben ser mayores que cero.");
            if (cantidad > producto.getStock()) {
                throw new IllegalArgumentException("Stock insuficiente para '" + producto.getNombre() + "'. Disponible: " + producto.getStock());
            }
            productosValidados.put(producto, cantidad);
        }

        List<LineaPedido> lineas = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entrada : productosValidados.entrySet()) {
            lineas.add(new LineaPedido(entrada.getKey(), entrada.getValue()));
        }
        Pedido pedido = new Pedido(lineas);
        // La validación terminó; ahora se aplican los cambios de stock.
        for (Map.Entry<Producto, Integer> entrada : productosValidados.entrySet()) {
            entrada.getKey().reducirStock(entrada.getValue());
        }
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() { return new ArrayList<>(pedidos); }
    public boolean estaVacio() { return pedidos.isEmpty(); }
}
