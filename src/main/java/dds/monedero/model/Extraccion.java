package dds.monedero.model;

import dds.monedero.model.Validaciones.*;

import java.time.LocalDate;

public class Extraccion extends Movimiento{

  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
    asignarValidacionesBase();
  }

  @Override
  public void asignarValidacionesBase(){
    getValidaciones().add(new MontoPositivoValidacion());
    getValidaciones().add(new SaldoSuficienteValidacion());
    getValidaciones().add(new LimiteExtraccionDiarioValidacion(1000));
  }

}
