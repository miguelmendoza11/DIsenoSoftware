package paqueteCarro;

import java.util.Date;

public class Servicio {
    private String tipoServicio;
    private String descripcion;
    private float costo;
    private Date fecha;
    private Vehiculo vehiculo;
    
    public Servicio() {}
    
    public Servicio(String tipoServicio, float costo, Date fecha) {
        this.tipoServicio = tipoServicio;
        this.costo = costo;
        this.fecha = fecha;
    }
    
    // Getters y Setters
    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    
    public boolean validarCosto() {
        return this.costo > 0;
    }
    
    @Override
    public String toString() {
        return "Servicio{" + "tipoServicio='" + tipoServicio + '\'' + 
               ", descripcion='" + descripcion + '\'' + ", costo=" + costo + 
               ", fecha=" + fecha + ", vehiculo=" + 
               (vehiculo != null ? vehiculo.getPlaca() : "Sin vehículo") + '}';
    }
}