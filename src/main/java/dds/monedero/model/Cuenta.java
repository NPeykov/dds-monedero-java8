package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void realizarDeposito(double monto) {
    validarMontoPositivo(monto);

    validarCantidadDepositosDiarios();

    concretarDeposito(monto);
  }

  public void realizarExtraccion(double monto) {
    validarMontoPositivo(monto);

    validarSaldoSuficiente(monto);

    validarLimiteExtraccionDiario(monto);

    concretarExtraccion(monto);
  }

  ///////////VALIDACIONES

  public void validarMontoPositivo(double monto){
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void validarCantidadDepositosDiarios(){
    if (depositosRealizadosEn(LocalDate.now()).size() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public void validarSaldoSuficiente(double monto){
    if (saldo - monto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void validarLimiteExtraccionDiario(double monto){
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (monto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  //////////////////////////////////////

  public List<Movimiento> depositosRealizadosEn(LocalDate fecha){
    return getDepositos().stream()
        .filter(m -> m.esDeLaFecha(fecha))
        .collect(Collectors.toList());
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getExtracciones().stream()
        .filter(movimiento -> movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public void concretarDeposito(double monto){
    saldo += monto;
    Deposito nuevoDeposito = new Deposito(LocalDate.now(), monto);
    movimientos.add(nuevoDeposito);
  }

  public void concretarExtraccion(double monto){
    saldo -= monto;
    Extraccion nuevaExtraccion = new Extraccion(LocalDate.now(), monto);
    movimientos.add(nuevaExtraccion);
  }

  public List<Movimiento> getDepositos(){
    return movimientos.stream().filter(m -> m instanceof Deposito).collect(Collectors.toList());
  }

  public List<Movimiento> getExtracciones(){
    return movimientos.stream().filter(m -> m instanceof Extraccion).collect(Collectors.toList());
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
