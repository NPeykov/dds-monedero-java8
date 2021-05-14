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

    saldo += monto;
    Movimiento nuevoDeposito = new Movimiento(LocalDate.now(), monto, true);
    movimientos.add(nuevoDeposito);
  }

  public void realizarExtraccion(double monto) {
    validarMontoPositivo(monto);

    validarSaldoSuficiente(monto);

    validarLimiteExtraccionDiario(monto);

    saldo -= monto;
    Movimiento nuevaExtraccion = new Movimiento(LocalDate.now(), monto, false);
    movimientos.add(nuevaExtraccion);
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

  public List<Movimiento> depositosRealizadosEn(LocalDate fecha){
    return movimientos.stream()
        .filter(m -> m.esDeLaFecha(fecha) && m.isDeposito())
        .collect(Collectors.toList());
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

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
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
