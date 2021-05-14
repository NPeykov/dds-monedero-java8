package dds.monedero.model;

import dds.monedero.model.Validaciones.*;

import java.time.LocalDate;

public class Extraccion extends Movimiento{

  public Extraccion(){
    super();
    asignarValidacionesBase();
  }

  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public void asignarValidacionesBase(){
    getValidaciones().add(new MontoPositivoValidacion());
    getValidaciones().add(new SaldoSuficienteValidacion());
    getValidaciones().add(new LimiteExtraccionDiarioValidacion(1000));
  }

  public void evaluarConcrecion(Cuenta cuenta, double monto){
    evaluarValidaciones(cuenta, monto);
    Extraccion nuevaExtraccion = new Extraccion(LocalDate.now(), monto);
    cuenta.concretarExtraccion(nuevaExtraccion, monto);
  }

}
