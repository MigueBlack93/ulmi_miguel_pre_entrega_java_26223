# Pre Entrega Ulmi Miguel Java 26223

# Sistema de Gestión TECHLAB

Aplicación de consola en Java para gestionar productos y pedidos.

## Requisitos
- JDK 17 o superior (se recomienda JDK 21).
- Visual Studio Code con la extensión **Extension Pack for Java**.

## Ejecutar desde Visual Studio Code
1. Descomprimí la carpeta `TechLab` en una ubicación de tu equipo.
2. Abrí Visual Studio Code y seleccioná **Archivo > Abrir carpeta...**. Elegí `TechLab`.
3. Asegurate de tener instalado el JDK y la extensión **Extension Pack for Java**.
4. Abrí `src/app/Main.java` y presioná **Run** sobre el método `main`.

## Ejecutar desde una terminal
Desde la carpeta `TechLab`, ejecutá:

```bash
javac -d out src/modelo/*.java src/servicio/*.java src/app/Main.java
java -cp out app.Main
```

En Windows PowerShell también funcionan esos comandos si `javac` y `java` están en el PATH.

## Funcionalidades
1. Agregar productos.
2. Listar productos.
3. Buscar por ID o nombre y actualizar precio o stock.
4. Eliminar productos con confirmación.
5. Crear pedidos con varios productos y cantidades.
6. Listar pedidos y consultar subtotales y total.
7. Salir.

## Conceptos de POO aplicados
- **Encapsulamiento:** atributos privados y métodos públicos de acceso/modificación.
- **Herencia:** `ProductoFisico` extiende `Producto`.
- **Polimorfismo:** `getTipo()` se redefine con `@Override` y se invoca desde una referencia de tipo `Producto`.
- **Composición/colaboración:** `Pedido` contiene líneas `LineaPedido`, cada una asociada a un `Producto`.
- **Colecciones:** `ArrayList` mediante `List` para productos, líneas y pedidos.
- **Excepciones y validaciones:** control de datos inválidos y mensajes de error.

## Importante
Los datos se guardan en memoria: al cerrar el programa se pierden. No se utiliza base de datos ni persistencia en archivos porque no forman parte de los requisitos iniciales.
