// Clase Plato
class Plato {
    private String nombre;
    private String tipo; // entrada, plato fuerte, bebida, postre
    private double precio;

    public Plato(String nombre, String tipo, double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%,.0f COP", nombre, tipo, precio);
    }
}

// Clase Cliente
class Cliente {
    private String nombre;
    private String numeroContacto;

    public Cliente(String nombre, String numeroContacto) {
        this.nombre = nombre;
        this.numeroContacto = numeroContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    @Override
    public String toString() {
        return String.format("%s (Tel: %s)", nombre, numeroContacto);
    }
}

// Clase ItemPedido
class ItemPedido {
    private Plato plato;
    private int cantidad;

    public ItemPedido(Plato plato, int cantidad) {
        this.plato = plato;
        this.cantidad = cantidad;
    }

    public Plato getPlato() {
        return plato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return plato.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return String.format("%dx %s - $%,.0f COP", cantidad, plato.getNombre(), getSubtotal());
    }
}

// Clase Pedido
class Pedido {
    private static int contadorPedidos = 1;
    private int numeroPedido;
    private Cliente cliente;
    private java.util.ArrayList<ItemPedido> items;
    private java.util.Date fecha;

    public Pedido(Cliente cliente) {
        this.numeroPedido = contadorPedidos++;
        this.cliente = cliente;
        this.items = new java.util.ArrayList<>();
        this.fecha = new java.util.Date();
    }

    public void agregarItem(Plato plato, int cantidad) {
        items.add(new ItemPedido(plato, cantidad));
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void mostrarResumen() {
        System.out.println("\n========== RESUMEN DEL PEDIDO ==========");
        System.out.println("Pedido #" + numeroPedido);
        System.out.println("Fecha: " + fecha);
        System.out.println("Cliente: " + cliente);
        System.out.println("\n--- Items del Pedido ---");
        for (ItemPedido item : items) {
            System.out.println(item);
        }
        System.out.println("\n----------------------------------------");
        System.out.println("TOTAL A PAGAR: $" + String.format("%,.0f COP", calcularTotal()));
        System.out.println("========================================\n");
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }
}

// Clase principal del Sistema
public class SistemaPedidosRestaurante {
    private java.util.ArrayList<Plato> menuPlatos;
    private java.util.ArrayList<Cliente> clientes;
    private java.util.ArrayList<Pedido> pedidos;
    private java.util.Scanner scanner;

    public SistemaPedidosRestaurante() {
        menuPlatos = new java.util.ArrayList<>();
        clientes = new java.util.ArrayList<>();
        pedidos = new java.util.ArrayList<>();
        scanner = new java.util.Scanner(System.in);
        inicializarMenu();
    }

    private void inicializarMenu() {
        // Menú de ejemplo con precios en COP
        menuPlatos.add(new Plato("Ensalada César", "entrada", 18000));
        menuPlatos.add(new Plato("Sopa del día", "entrada", 12000));
        menuPlatos.add(new Plato("Arepas con queso", "entrada", 8000));
        menuPlatos.add(new Plato("Bistec a la parrilla", "plato fuerte", 35000));
        menuPlatos.add(new Plato("Pasta Alfredo", "plato fuerte", 28000));
        menuPlatos.add(new Plato("Pollo al horno", "plato fuerte", 32000));
        menuPlatos.add(new Plato("Bandeja Paisa", "plato fuerte", 38000));
        menuPlatos.add(new Plato("Coca Cola", "bebida", 5000));
        menuPlatos.add(new Plato("Jugo natural", "bebida", 7000));
        menuPlatos.add(new Plato("Agua", "bebida", 3000));
        menuPlatos.add(new Plato("Tiramisú", "postre", 12000));
        menuPlatos.add(new Plato("Helado", "postre", 10000));
        menuPlatos.add(new Plato("Tres leches", "postre", 13000));
    }

    public void registrarPlato() {
        System.out.println("\n--- Registrar Nuevo Plato ---");
        System.out.print("Nombre del plato: ");
        String nombre = scanner.nextLine();
        
        System.out.println("Tipo (1-Entrada, 2-Plato Fuerte, 3-Bebida, 4-Postre): ");
        int tipoOpcion = scanner.nextInt();
        String tipo = "";
        switch(tipoOpcion) {
            case 1: tipo = "entrada"; break;
            case 2: tipo = "plato fuerte"; break;
            case 3: tipo = "bebida"; break;
            case 4: tipo = "postre"; break;
            default: tipo = "otro"; break;
        }
        
        System.out.print("Precio: $");
        double precio = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        
        menuPlatos.add(new Plato(nombre, tipo, precio));
        System.out.println("✓ Plato registrado exitosamente!");
    }

    public void registrarCliente() {
        System.out.println("\n--- Registrar Nuevo Cliente ---");
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Número de contacto: ");
        String numero = scanner.nextLine();
        
        clientes.add(new Cliente(nombre, numero));
        System.out.println("✓ Cliente registrado exitosamente!");
    }

    public void crearPedido() {
        if (clientes.isEmpty()) {
            System.out.println("⚠ No hay clientes registrados. Registre un cliente primero.");
            return;
        }

        System.out.println("\n--- Crear Nuevo Pedido ---");
        System.out.println("Clientes disponibles:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i));
        }
        
        System.out.print("Seleccione cliente (número): ");
        int clienteIdx = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpiar buffer
        
        if (clienteIdx < 0 || clienteIdx >= clientes.size()) {
            System.out.println("⚠ Cliente no válido.");
            return;
        }
        
        Cliente cliente = clientes.get(clienteIdx);
        Pedido pedido = new Pedido(cliente);
        
        boolean agregarMasPlatos = true;
        while (agregarMasPlatos) {
            mostrarMenu();
            System.out.print("\nSeleccione plato (número, 0 para terminar): ");
            int platoIdx = scanner.nextInt() - 1;
            
            if (platoIdx == -1) {
                agregarMasPlatos = false;
                continue;
            }
            
            if (platoIdx < 0 || platoIdx >= menuPlatos.size()) {
                System.out.println("⚠ Plato no válido.");
                continue;
            }
            
            System.out.print("Cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            pedido.agregarItem(menuPlatos.get(platoIdx), cantidad);
            System.out.println("✓ Plato agregado al pedido.");
        }
        
        pedidos.add(pedido);
        System.out.println("\n✓ Pedido creado exitosamente!");
        pedido.mostrarResumen();
    }

    public void mostrarMenu() {
        System.out.println("\n========== MENÚ DEL RESTAURANTE ==========");
        for (int i = 0; i < menuPlatos.size(); i++) {
            System.out.println((i + 1) + ". " + menuPlatos.get(i));
        }
        System.out.println("==========================================");
    }

    public void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("\n⚠ No hay pedidos registrados.");
            return;
        }
        
        System.out.println("\n========== TODOS LOS PEDIDOS ==========");
        for (Pedido pedido : pedidos) {
            pedido.mostrarResumen();
        }
    }

    public void menuPrincipal() {
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║  SISTEMA DE PEDIDOS - RESTAURANTE      ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Registrar Plato");
            System.out.println("2. Registrar Cliente");
            System.out.println("3. Crear Pedido");
            System.out.println("4. Mostrar Menú");
            System.out.println("5. Mostrar Todos los Pedidos");
            System.out.println("6. Salir");
            System.out.print("\nSeleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    registrarPlato();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    crearPedido();
                    break;
                case 4:
                    mostrarMenu();
                    break;
                case 5:
                    mostrarPedidos();
                    break;
                case 6:
                    continuar = false;
                    System.out.println("\n¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("⚠ Opción no válida.");
            }
        }
        
        scanner.close();
    }

    public static void main(String[] args) {
        SistemaPedidosRestaurante sistema = new SistemaPedidosRestaurante();
        sistema.menuPrincipal();
    }
}