package com.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger SIGUIENTE_ID = new AtomicInteger(1);
    private final int id;
    private final LocalDateTime fecha;
    private final List<LineaPedido> lineas;

    public Pedido(List<LineaPedido> lineas) {
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un producto.");
        }
        this.id = SIGUIENTE_ID.getAndIncrement();
        this.fecha = LocalDateTime.now();
        this.lineas = new ArrayList<>(lineas);
    }

    public int getId() { return id; }
    public List<LineaPedido> getLineas() { return Collections.unmodifiableList(lineas); }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) total += linea.getSubtotal();
        return total;
    }

    public void mostrarInformacion() {
        System.out.printf("Pedido #%d | Fecha: %s%n", id, fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        for (LineaPedido linea : lineas) linea.mostrarInformacion();
        System.out.printf("  TOTAL: $%.2f%n", calcularTotal());
    }
}
