package dds.monedero.model;

import dds.monedero.model.Validaciones.Validacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Movimiento {
  private LocalDate fecha;
  private double monto;
  public List<Validacion> validaciones;

  public Movimiento(){
    this.validaciones = new ArrayList<>();
  }

  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public abstract void asignarValidacionesBase();

  public void evaluarValidaciones(Cuenta cuenta, double monto){
    validaciones.forEach(v -> v.validar(cuenta, monto));
  }

  public double getMonto() {
    return monto;
  }

  public List<Validacion> getValidaciones(){
    return validaciones;
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

}
