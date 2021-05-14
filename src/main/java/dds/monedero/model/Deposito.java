package dds.monedero.model;

import dds.monedero.model.Validaciones.CantidadDepositosDiariosValidacion;
import dds.monedero.model.Validaciones.MontoPositivoValidacion;

import java.time.LocalDate;

public class Deposito extends Movimiento{

  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
    asignarValidacionesBase();
  }

  @Override
  public void asignarValidacionesBase(){
    getValidaciones().add(new MontoPositivoValidacion());
    getValidaciones().add(new CantidadDepositosDiariosValidacion());
  }

}
