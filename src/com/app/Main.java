package com.app;

import com.modelo.Pedido;
import com.modelo.Producto;
import com.servicio.GestorPedidos;
import com.servicio.GestorProductos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final GestorProductos gestorProductos = new GestorProductos();
    private static final GestorPedidos gestorPedidos = new GestorPedidos();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");
            try {
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarActualizarProducto();
                    case 4 -> eliminarProducto();
                    case 5 -> crearPedido();
                    case 6 -> listarPedidos();
                    case 7 -> System.out.println("Gracias por utilizar TECHLAB. ¡Hasta luego!");
                    default -> System.out.println("Opción inválida. Ingrese un número del 1 al 7.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
            System.out.println();
        } while (opcion != 7);
        SC.close();
    }

    private static void mostrarMenu() {
        System.out.println("============================================");
        System.out.println(" SISTEMA DE GESTIÓN - TECHLAB");
        System.out.println("============================================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
    }

    private static void agregarProducto() {
        System.out.println("\n--- Agregar producto ---");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDoubleNoNegativo("Precio: $");
        int stock = leerEnteroNoNegativo("Cantidad en stock: ");
        Producto producto = gestorProductos.agregarProducto(nombre, precio, stock);
        System.out.println("Producto agregado correctamente:");
        producto.mostrarInformacion();
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de productos ---");
        List<Producto> productos = gestorProductos.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto producto : productos) producto.mostrarInformacion();
    }

    private static void buscarActualizarProducto() {
        System.out.println("\n--- Buscar producto ---");
        System.out.println("1) Buscar por ID\n2) Buscar por nombre");
        int tipo = leerEntero("Opción: ");
        Producto producto = null;
        if (tipo == 1) {
            producto = gestorProductos.buscarPorId(leerEntero("ID del producto: "));
            if (producto != null) producto.mostrarInformacion();
        } else if (tipo == 2) {
            List<Producto> encontrados = gestorProductos.buscarPorNombre(leerTexto("Nombre o parte del nombre: "));
            if (encontrados.isEmpty()) System.out.println("No se encontraron productos.");
            else {
                for (Producto p : encontrados) p.mostrarInformacion();
                int id = leerEntero("Ingrese el ID del producto que desea actualizar (0 para cancelar): ");
                if (id != 0) producto = gestorProductos.buscarPorId(id);
                if (producto != null && !encontrados.contains(producto)) {
                    System.out.println("Ese ID no pertenece a los resultados de la búsqueda.");
                    producto = null;
                }
            }
        } else {
            System.out.println("Opción de búsqueda inválida.");
            return;
        }
        if (producto == null) {
            System.out.println("No se encontró el producto.");
            return;
        }
        System.out.println("¿Qué desea actualizar? 1) Precio  2) Stock  3) Cancelar");
        switch (leerEntero("Opción: ")) {
            case 1 -> { producto.setPrecio(leerDoubleNoNegativo("Nuevo precio: $")); System.out.println("Precio actualizado."); }
            case 2 -> { producto.setStock(leerEnteroNoNegativo("Nuevo stock: ")); System.out.println("Stock actualizado."); }
            case 3 -> System.out.println("Actualización cancelada.");
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- Eliminar producto ---");
        if (gestorProductos.estaVacio()) { System.out.println("No hay productos para eliminar."); return; }
        listarProductos();
        int id = leerEntero("ID del producto a eliminar (0 para cancelar): ");
        if (id == 0) { System.out.println("Operación cancelada."); return; }
        Producto producto = gestorProductos.buscarPorId(id);
        if (producto == null) { System.out.println("No existe un producto con ese ID."); return; }
        System.out.println("Se eliminará: " + producto.getNombre());
        String confirmar = leerTexto("¿Confirma? (s/n): ");
        if (confirmar.equalsIgnoreCase("s")) {
            gestorProductos.eliminarProducto(id);
            System.out.println("Producto eliminado.");
        } else System.out.println("Eliminación cancelada.");
    }

    private static void crearPedido() {
        System.out.println("\n--- Crear pedido ---");
        if (gestorProductos.estaVacio()) { System.out.println("Primero debe registrar productos."); return; }
        Map<Integer, Integer> cantidades = new LinkedHashMap<>();
        String continuar = "s";
        do {
            listarProductos();
            int id = leerEntero("ID del producto a agregar (0 para finalizar): ");
            if (id == 0) break;
            Producto producto = gestorProductos.buscarPorId(id);
            if (producto == null) { System.out.println("No existe un producto con ese ID."); continue; }
            int cantidad = leerEntero("Cantidad de '" + producto.getNombre() + "': ");
            if (cantidad <= 0) { System.out.println("La cantidad debe ser mayor que cero."); continue; }
            int acumulada = cantidades.getOrDefault(id, 0) + cantidad;
            if (acumulada > producto.getStock()) {
                System.out.println("Stock insuficiente. Disponible: " + producto.getStock() + ". Ya agregó: " + cantidades.getOrDefault(id, 0));
                continue;
            }
            cantidades.put(id, acumulada);
            continuar = leerTexto("¿Agregar otro producto? (s/n): ");
        } while (continuar.equalsIgnoreCase("s"));

        if (cantidades.isEmpty()) { System.out.println("No se creó ningún pedido."); return; }
        System.out.println("\nSe confirmará el siguiente pedido:");
        double total = 0;
        for (Map.Entry<Integer, Integer> e : cantidades.entrySet()) {
            Producto p = gestorProductos.buscarPorId(e.getKey());
            double subtotal = p.getPrecio() * e.getValue();
            System.out.printf("%s x %d = $%.2f%n", p.getNombre(), e.getValue(), subtotal);
            total += subtotal;
        }
        System.out.printf("TOTAL: $%.2f%n", total);
        if (!leerTexto("¿Confirmar pedido y descontar stock? (s/n): ").equalsIgnoreCase("s")) {
            System.out.println("Pedido cancelado. El stock no fue modificado.");
            return;
        }
        Pedido pedido = gestorPedidos.crearPedido(cantidades, gestorProductos);
        System.out.println("Pedido creado correctamente:");
        pedido.mostrarInformacion();
    }

    private static void listarPedidos() {
        System.out.println("\n--- Pedidos realizados ---");
        List<Pedido> pedidos = gestorPedidos.listarPedidos();
        if (pedidos.isEmpty()) { System.out.println("Todavía no hay pedidos registrados."); return; }
        for (Pedido pedido : pedidos) { pedido.mostrarInformacion(); System.out.println("--------------------------------------------"); }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SC.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("El valor no puede estar vacío.");
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try { return Integer.parseInt(SC.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Ingrese un número entero válido."); }
        }
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= 0) return valor;
            System.out.println("El valor no puede ser negativo.");
        }
    }

    private static double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SC.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (Double.isFinite(valor) && valor >= 0) return valor;
                System.out.println("Ingrese un precio válido y no negativo.");
            } catch (NumberFormatException e) { System.out.println("Ingrese un número válido. Puede usar coma o punto decimal."); }
        }
    }
}
