package dds.monedero.model.Validaciones;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class LimiteExtraccionDiarioValidacion implements Validacion{
  double limiteMaximoPermitido;

  public LimiteExtraccionDiarioValidacion(double limite){
    this.limiteMaximoPermitido = limite;
  }

  @Override
  public void validar(Cuenta cuenta, double monto){

    double montoExtraidoHoy = cuenta.getMontoExtraidoA(LocalDate.now());
    double limiteDisponible = limiteMaximoPermitido - montoExtraidoHoy;

    if (monto > limiteDisponible) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + limiteMaximoPermitido
          + " diarios, límite: " + limiteDisponible);
    }
  }
}
