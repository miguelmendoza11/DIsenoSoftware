package paqueteCarro;

import java.util.ArrayList;
import java.util.List;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private Propietario propietario;
    private List<Servicio> servicios;
    
    public Vehiculo() {
        this.servicios = new ArrayList<>();
    }
    
    public Vehiculo(String placa, String marca, String modelo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.servicios = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Propietario getPropietario() { return propietario; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }
    public List<Servicio> getServicios() { return servicios; }
    
    public void agregarServicio(Servicio servicio) {
        if (servicio != null && servicio.validarCosto()) {
            this.servicios.add(servicio);
            servicio.setVehiculo(this);
        }
    }
    
    public float calcularTotalServicios() {
        float total = 0;
        for (Servicio servicio : servicios) {
            total += servicio.getCosto();
        }
        return total;
    }
    
    @Override
    public String toString() {
        return "Vehiculo{" + "placa='" + placa + '\'' + ", marca='" + marca + '\'' + 
               ", modelo='" + modelo + '\'' + ", propietario=" + 
               (propietario != null ? propietario.getNombreCompleto() : "Sin propietario") + 
               ", servicios=" + servicios.size() + '}';
    }
}