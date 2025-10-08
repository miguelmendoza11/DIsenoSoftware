package paqueteCarro;

import java.util.Date;
import java.util.Scanner;

public class SistemaTaller {
    private static Administrador admin;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        admin = new Administrador("Admin Taller", "admin", "12345");
        scanner = new Scanner(System.in);
        
        System.out.println("🚗 ¡Bienvenido al Sistema de Gestión de Vehículos de Taller! 🔧");
        System.out.println("═══════════════════════════════════════════════════════════════");
        
        inicializarDatosPrueba();
        
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1: registrarPropietario(); break;
                    case 2: registrarVehiculo(); break;
                    case 3: registrarServicio(); break;
                    case 4: admin.generarReporteAtencion(); break;
                    case 5: mostrarHistorialServicios(); break;
                    case 6: calcularTotalGastado(); break;
                    case 0: System.out.println("¡Hasta luego! Gracias por usar el sistema 👋"); break;
                    default: System.out.println("❌ Opción inválida. Por favor seleccione una opción del 0 al 6.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: Por favor ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer en caso de error
                opcion = -1; // Para que continúe el loop
            }
        } while (opcion != 0);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n🛠️ ═══ SISTEMA DE GESTIÓN DE VEHÍCULOS DE TALLER ═══ 🛠️");
        System.out.println("1. 👤 Registrar Propietario");
        System.out.println("2. 🚙 Registrar Vehículo");
        System.out.println("3. 🔧 Registrar Servicio");
        System.out.println("4. 📊 Generar Reporte de Atención");
        System.out.println("5. 📋 Mostrar Historial de Servicios");
        System.out.println("6. 💰 Calcular Total Gastado");
        System.out.println("0. 🚪 Salir");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void registrarPropietario() {
        System.out.println("\n👤 ═══ REGISTRAR NUEVO PROPIETARIO ═══");
        System.out.print("📝 Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("📞 Número de contacto: ");
        String contacto = scanner.nextLine();
        
        if (nombre.trim().isEmpty() || contacto.trim().isEmpty()) {
            System.out.println("❌ Error: Todos los campos son obligatorios");
            return;
        }
        
        Propietario propietario = new Propietario(nombre.trim(), contacto.trim());
        admin.registrarPropietario(propietario);
    }
    
    private static void registrarVehiculo() {
        System.out.println("\n🚙 ═══ REGISTRAR NUEVO VEHÍCULO ═══");
        System.out.print("🏷️ Placa: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        System.out.print("🏭 Marca: ");
        String marca = scanner.nextLine().trim();
        System.out.print("📋 Modelo: ");
        String modelo = scanner.nextLine().trim();
        System.out.print("👤 Nombre del propietario: ");
        String nombrePropietario = scanner.nextLine().trim();
        
        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || nombrePropietario.isEmpty()) {
            System.out.println("❌ Error: Todos los campos son obligatorios");
            return;
        }
        
        Propietario propietario = admin.buscarPropietario(nombrePropietario);
        if (propietario != null) {
            Vehiculo vehiculo = new Vehiculo(placa, marca, modelo);
            propietario.agregarVehiculo(vehiculo);
            admin.registrarVehiculo(vehiculo);
        } else {
            System.out.println("❌ Propietario no encontrado. Verifique el nombre exacto.");
        }
    }
    
    private static void registrarServicio() {
        System.out.println("\n🔧 ═══ REGISTRAR NUEVO SERVICIO ═══");
        System.out.print("🏷️ Placa del vehículo: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        
        if (placa.isEmpty()) {
            System.out.println("❌ Error: La placa es obligatoria");
            return;
        }
        
        System.out.println("🔧 Tipos de servicio sugeridos:");
        System.out.println("   • Cambio de aceite");
        System.out.println("   • Sistema de frenos");
        System.out.println("   • Revisión general");
        System.out.println("   • Alineación y balanceo");
        System.out.println("   • Cambio de llantas");
        System.out.print("🔧 Tipo de servicio: ");
        String tipo = scanner.nextLine().trim();
        
        System.out.print("📝 Descripción: ");
        String descripcion = scanner.nextLine().trim();
        
        System.out.print("💰 Costo: $");
        try {
            float costo = scanner.nextFloat();
            scanner.nextLine(); // Limpiar buffer
            
            if (tipo.isEmpty()) {
                System.out.println("❌ Error: El tipo de servicio es obligatorio");
                return;
            }
            
            if (costo <= 0) {
                System.out.println("❌ Error: El costo debe ser mayor que 0");
                return;
            }
            
            Servicio servicio = new Servicio(tipo, costo, new Date());
            if (!descripcion.isEmpty()) {
                servicio.setDescripcion(descripcion);
            }
            
            // Buscar vehículo y asociar
            boolean vehiculoEncontrado = false;
            for (Vehiculo vehiculo : admin.getVehiculos()) {
                if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                    servicio.setVehiculo(vehiculo);
                    vehiculo.agregarServicio(servicio);
                    vehiculoEncontrado = true;
                    break;
                }
            }
            
            if (!vehiculoEncontrado) {
                System.out.println("❌ Vehículo con placa " + placa + " no encontrado");
                return;
            }
            
            admin.registrarServicio(servicio);
            
        } catch (Exception e) {
            System.out.println("❌ Error: Por favor ingrese un costo válido");
            scanner.nextLine(); // Limpiar buffer
        }
    }
    
    private static void mostrarHistorialServicios() {
        System.out.println("\n📋 ═══ HISTORIAL DE SERVICIOS ═══");
        System.out.print("🏷️ Placa del vehículo: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        
        if (placa.isEmpty()) {
            System.out.println("❌ Error: La placa es obligatoria");
            return;
        }
        
        admin.mostrarHistorialServicios(placa);
    }
    
    private static void calcularTotalGastado() {
        System.out.println("\n💰 ═══ CALCULAR TOTAL GASTADO ═══");
        System.out.print("🏷️ Placa del vehículo: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        
        if (placa.isEmpty()) {
            System.out.println("❌ Error: La placa es obligatoria");
            return;
        }
        
        admin.calcularTotalGastado(placa);
    }
    
    private static void inicializarDatosPrueba() {
        System.out.println("⏳ Inicializando datos de prueba...");
        
        // Crear propietarios de prueba
        Propietario prop1 = new Propietario("María González", "3001234567");
        Propietario prop2 = new Propietario("Carlos Rodríguez", "3007654321");
        Propietario prop3 = new Propietario("Ana Martínez", "3159876543");
        
        admin.registrarPropietario(prop1);
        admin.registrarPropietario(prop2);
        admin.registrarPropietario(prop3);
        
        // Crear vehículos de prueba
        Vehiculo vehiculo1 = new Vehiculo("ABC123", "Toyota", "Corolla");
        Vehiculo vehiculo2 = new Vehiculo("XYZ789", "Chevrolet", "Spark");
        Vehiculo vehiculo3 = new Vehiculo("DEF456", "Nissan", "Sentra");
        
        prop1.agregarVehiculo(vehiculo1);
        prop2.agregarVehiculo(vehiculo2);
        prop3.agregarVehiculo(vehiculo3);
        
        admin.registrarVehiculo(vehiculo1);
        admin.registrarVehiculo(vehiculo2);
        admin.registrarVehiculo(vehiculo3);
        
        // Crear servicios de prueba
        Servicio servicio1 = new Servicio("Cambio de aceite", 80000, new Date());
        servicio1.setDescripcion("Cambio de aceite sintético y filtro");
        servicio1.setVehiculo(vehiculo1);
        vehiculo1.agregarServicio(servicio1);
        admin.registrarServicio(servicio1);
        
        Servicio servicio2 = new Servicio("Revisión de frenos", 150000, new Date());
        servicio2.setDescripcion("Revisión completa del sistema de frenos");
        servicio2.setVehiculo(vehiculo2);
        vehiculo2.agregarServicio(servicio2);
        admin.registrarServicio(servicio2);
        
        Servicio servicio3 = new Servicio("Alineación y balanceo", 120000, new Date());
        servicio3.setDescripcion("Alineación y balanceo de las 4 llantas");
        servicio3.setVehiculo(vehiculo3);
        vehiculo3.agregarServicio(servicio3);
        admin.registrarServicio(servicio3);
        
        System.out.println("✅ Datos de prueba inicializados correctamente");
        System.out.println("📝 Propietarios disponibles:");
        System.out.println("   • María González (ABC123 - Toyota Corolla)");
        System.out.println("   • Carlos Rodríguez (XYZ789 - Chevrolet Spark)");
        System.out.println("   • Ana Martínez (DEF456 - Nissan Sentra)");
    }
}