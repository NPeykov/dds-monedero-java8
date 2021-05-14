package dds.monedero.model;


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

  public void realizarDeposito(double monto) {
    new Deposito().evaluarConcrecion(this, monto);
  }

  public void realizarExtraccion(double monto) {
    new Extraccion().evaluarConcrecion(this, monto);
  }
  
  public void concretarExtraccion(Movimiento nuevaExtraccion, double monto){
    saldo -= monto;
    movimientos.add(nuevaExtraccion);
  }

  public void concretarDeposito(Movimiento nuevoDeposito, double monto){
    saldo += monto;
    movimientos.add(nuevoDeposito);
  }

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
