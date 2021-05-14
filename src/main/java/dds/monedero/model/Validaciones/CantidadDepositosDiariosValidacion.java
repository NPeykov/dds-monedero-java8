package dds.monedero.model.Validaciones;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class CantidadDepositosDiariosValidacion implements Validacion{

  @Override
  public void validar(Cuenta cuenta, double monto) {
      if (cuenta.depositosRealizadosEn(LocalDate.now()).size() >= 3) {
        throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
      }
  }
}
