package com.modelo;

import java.util.concurrent.atomic.AtomicInteger;

public class Producto {
    private static final AtomicInteger SIGUIENTE_ID = new AtomicInteger(1);

    private final int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = SIGUIENTE_ID.getAndIncrement();
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio debe ser un número válido y no negativo.");
        }
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (cantidad > stock) {
            throw new IllegalArgumentException("Stock insuficiente para " + nombre + ". Disponible: " + stock);
        }
        stock -= cantidad;
    }

    public String getTipo() {
        return "Producto";
    }

    public void mostrarInformacion() {
        System.out.printf("ID: %d | Tipo: %s | Nombre: %s | Precio: $%.2f | Stock: %d%n",
                id, getTipo(), nombre, precio, stock);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Precio: $%.2f | Stock: %d", id, nombre, precio, stock);
    }
}
