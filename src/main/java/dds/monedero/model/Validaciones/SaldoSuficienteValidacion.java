package dds.monedero.model.Validaciones;

import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.model.Cuenta;

public class SaldoSuficienteValidacion implements Validacion{


  @Override
  public void validar(Cuenta cuenta, double monto){
    if (cuenta.getSaldo() - monto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + cuenta.getSaldo() + " $");
    }
  }
}
