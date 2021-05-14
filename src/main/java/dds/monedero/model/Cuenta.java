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
    Deposito nuevoDeposito = new Deposito(LocalDate.now(), monto);
    nuevoDeposito.evaluarValidaciones(this, monto);
    concretarDeposito(nuevoDeposito, monto);
  }

  public void realizarExtraccion(double monto) {
    Extraccion nuevaExtraccion = new Extraccion(LocalDate.now(), monto);
    nuevaExtraccion.evaluarValidaciones(this, monto);
    concretarExtraccion(nuevaExtraccion, monto);
  }
  
  public void concretarDeposito(Movimiento deposito, double monto){
    saldo += monto;
    movimientos.add(deposito);
  }

  public void concretarExtraccion(Movimiento extraccion, double monto){
    saldo -= monto;
    movimientos.add(extraccion);
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
