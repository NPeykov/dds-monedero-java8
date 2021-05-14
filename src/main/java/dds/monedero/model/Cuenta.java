package dds.monedero.model;

import dds.monedero.model.Validaciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();
  private List<Validacion> validacionesDeposito = new ArrayList<>();
  private List<Validacion> validacionesExtraccion = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
    asignarValidacionesBaseDeposito();
    asignarValidacionesBaseExtraccion();
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
    asignarValidacionesBaseDeposito();
    asignarValidacionesBaseExtraccion();
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void realizarDeposito(double monto) {
    evaluarValidaciones(validacionesDeposito, monto);

    saldo += monto;
    movimientos.add(new Deposito(LocalDate.now(), monto));
  }

  public void realizarExtraccion(double monto) {
    evaluarValidaciones(validacionesExtraccion, monto);

    saldo -= monto;
    movimientos.add(new Extraccion(LocalDate.now(), monto));
  }

  ///////////VALIDACIONES
  
  public void asignarValidacionesBaseDeposito(){
    validacionesDeposito.add(new MontoPositivoValidacion());
    validacionesDeposito.add(new CantidadDepositosDiariosValidacion());
  }
  public void asignarValidacionesBaseExtraccion(){
    validacionesExtraccion.add(new MontoPositivoValidacion());
    validacionesExtraccion.add(new SaldoSuficienteValidacion());
    validacionesExtraccion.add(new LimiteExtraccionDiarioValidacion(1000));
  }

  public void evaluarValidaciones(List<Validacion> validaciones, double monto){
    validaciones.forEach(v -> v.validar(this, monto));
  }

  //////////////////////////////////////

  public List<Movimiento> depositosRealizadosEn(LocalDate fecha){
    return getDepositos().stream()
        .filter(m -> m.esDeLaFecha(fecha))
        .collect(Collectors.toList());
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getExtracciones().stream()
        .filter(movimiento -> movimiento.esDeLaFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
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
