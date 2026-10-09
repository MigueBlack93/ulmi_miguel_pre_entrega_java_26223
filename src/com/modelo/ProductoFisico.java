package com.modelo;

/** Ejemplo de herencia: un producto físico es un tipo particular de Producto. */
public class ProductoFisico extends Producto {
    public ProductoFisico(String nombre, double precio, int stock) {
        super(nombre, precio, stock);
    }

    @Override
    public String getTipo() {
        return "Producto físico";
    }
}
