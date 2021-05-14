package dds.monedero.model.Validaciones;


import dds.monedero.model.Cuenta;

public interface Validacion {

  public void validar(Cuenta cuenta, double monto);

}
