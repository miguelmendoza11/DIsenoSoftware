package paqueteCarro;

import java.util.ArrayList;
import java.util.List;

public class Administrador {
    private String nombre;
    private String usuario;
    private String contraseña;
    private List<Propietario> propietarios;
    private List<Vehiculo> vehiculos;
    private List<Servicio> servicios;
    
    public Administrador() {
        this.propietarios = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }
    
    public Administrador(String nombre, String usuario, String contraseña) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.propietarios = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    
    public void registrarPropietario(Propietario propietario) {
        if (propietario != null) {
            this.propietarios.add(propietario);
            System.out.println("✅ Propietario registrado: " + propietario.getNombreCompleto());
        }
    }
    
    public void registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !existePlaca(vehiculo.getPlaca())) {
            this.vehiculos.add(vehiculo);
            System.out.println("✅ Vehículo registrado: " + vehiculo.getPlaca());
        } else {
            System.out.println("❌ Error: La placa ya existe o el vehículo es nulo");
        }
    }
    
    public void registrarServicio(Servicio servicio) {
        if (servicio != null && servicio.getVehiculo() != null && 
            existeVehiculo(servicio.getVehiculo().getPlaca()) && 
            servicio.validarCosto()) {
            this.servicios.add(servicio);
            System.out.println("✅ Servicio registrado: " + servicio.getTipoServicio());
        } else {
            System.out.println("❌ Error: No se puede registrar el servicio");
        }
    }
    
    public void generarReporteAtencion() {
        System.out.println("\n📊 === REPORTE DE ATENCIÓN ===");
        System.out.println("Total de propietarios: " + propietarios.size());
        System.out.println("Total de vehículos: " + vehiculos.size());
        System.out.println("Total de servicios: " + servicios.size());
        
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println("\n🚗 Vehículo: " + vehiculo.getPlaca() + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
            if (vehiculo.getPropietario() != null) {
                System.out.println("👤 Propietario: " + vehiculo.getPropietario().getNombreCompleto());
            }
            System.out.println("💰 Total gastado: $" + String.format("%.0f", vehiculo.calcularTotalServicios()));
        }
    }
    
    public List<Servicio> mostrarHistorialServicios(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo != null) {
            System.out.println("\n📋 === HISTORIAL DE SERVICIOS - " + placa + " ===");
            if (vehiculo.getServicios().isEmpty()) {
                System.out.println("No hay servicios registrados para este vehículo");
            } else {
                for (Servicio servicio : vehiculo.getServicios()) {
                    System.out.println("🔧 " + servicio.getTipoServicio() + " | $" + String.format("%.0f", servicio.getCosto()) + " | " + servicio.getFecha());
                }
            }
            return vehiculo.getServicios();
        } else {
            System.out.println("❌ Vehículo no encontrado");
            return new ArrayList<>();
        }
    }
    
    public float calcularTotalGastado(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo != null) {
            float total = vehiculo.calcularTotalServicios();
            System.out.println("💰 Total gastado por vehículo " + placa + ": $" + String.format("%.0f", total));
            return total;
        } else {
            System.out.println("❌ Vehículo no encontrado");
            return 0;
        }
    }
    
    private boolean existePlaca(String placa) {
        return vehiculos.stream().anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
    }
    
    private boolean existeVehiculo(String placa) {
        return buscarVehiculo(placa) != null;
    }
    
    private Vehiculo buscarVehiculo(String placa) {
        return vehiculos.stream().filter(v -> v.getPlaca().equalsIgnoreCase(placa)).findFirst().orElse(null);
    }
    
    public Propietario buscarPropietario(String nombre) {
        return propietarios.stream().filter(p -> p.getNombreCompleto().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }
}