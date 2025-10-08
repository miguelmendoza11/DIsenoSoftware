package paqueteCarro;

import java.util.ArrayList;
import java.util.List;

public class Propietario {
    private String nombreCompleto;
    private String numeroContacto;
    private List<Vehiculo> vehiculos;
    
    public Propietario() {
        this.vehiculos = new ArrayList<>();
    }
    
    public Propietario(String nombreCompleto, String numeroContacto) {
        this.nombreCompleto = nombreCompleto;
        this.numeroContacto = numeroContacto;
        this.vehiculos = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNumeroContacto() { return numeroContacto; }
    public void setNumeroContacto(String numeroContacto) { this.numeroContacto = numeroContacto; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            this.vehiculos.add(vehiculo);
            vehiculo.setPropietario(this);
        }
    }
    
    @Override
    public String toString() {
        return "Propietario{" + "nombreCompleto='" + nombreCompleto + '\'' + 
               ", numeroContacto='" + numeroContacto + '\'' + ", vehiculos=" + vehiculos.size() + '}';
    }
}