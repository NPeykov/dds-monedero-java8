package dds.monedero.model;

import dds.monedero.model.Validaciones.CantidadDepositosDiariosValidacion;
import dds.monedero.model.Validaciones.MontoPositivoValidacion;

import java.time.LocalDate;

public class Deposito extends Movimiento{

  public Deposito(){
    super();
    asignarValidacionesBase();
  }

  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public void asignarValidacionesBase(){
    getValidaciones().add(new MontoPositivoValidacion());
    getValidaciones().add(new CantidadDepositosDiariosValidacion());
  }

  public void evaluarConcrecion(Cuenta cuenta, double monto){
    evaluarValidaciones(cuenta, monto);
    Deposito nuevoDeposito = new Deposito(LocalDate.now(), monto);
    cuenta.concretarDeposito(nuevoDeposito, monto);
  }

}
