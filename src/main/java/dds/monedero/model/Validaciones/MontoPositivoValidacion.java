package dds.monedero.model.Validaciones;

import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.model.Cuenta;
import dds.monedero.model.Validaciones.Validacion;

public class MontoPositivoValidacion implements Validacion {

  public MontoPositivoValidacion(){}

  @Override
  public void validar(Cuenta cuenta, double monto){
      if (monto <= 0) {
        throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
      }
    }
}
