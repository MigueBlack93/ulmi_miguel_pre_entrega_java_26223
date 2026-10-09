package com.servicio;

import com.modelo.Producto;
import com.modelo.ProductoFisico;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {
    private final List<Producto> productos = new ArrayList<>();

    public Producto agregarProducto(String nombre, double precio, int stock) {
        Producto producto = new ProductoFisico(nombre, precio, stock);
        productos.add(producto);
        return producto;
    }

    public List<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        for (Producto producto : productos) if (producto.getId() == id) return producto;
        return null;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        if (nombre == null) return encontrados;
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.trim().toLowerCase())) encontrados.add(producto);
        }
        return encontrados;
    }

    public boolean eliminarProducto(int id) {
        Producto producto = buscarPorId(id);
        return producto != null && productos.remove(producto);
    }

    public boolean estaVacio() { return productos.isEmpty(); }
}
