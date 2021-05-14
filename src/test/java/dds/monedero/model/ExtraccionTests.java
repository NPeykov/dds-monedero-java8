package dds.monedero.model;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExtraccionTests{

  private Cuenta cuenta;

  @BeforeEach
  void init(){
    cuenta = new Cuenta();
  }

  @Test
  void tiraErrorAlExtraerUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.realizarExtraccion(-1));
  }

  @Test
  void tiraErrorAlExtraerUnMontoNulo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.realizarExtraccion(0));
  }

  @Test
  void noPuedeRealizarUnaExtraccionMayorASuSaldo() {
    cuenta.setSaldo(150);
    assertThrows(SaldoMenorException.class, () -> cuenta.realizarExtraccion(200));
  }

  @Test
  void puedeRealizarUnaExtraccionIgualASuSaldo() {
    cuenta.setSaldo(150);
    cuenta.realizarExtraccion(150);
    assertEquals(0.0, cuenta.getSaldo());
  }

  @Test
  void noPuedeRetirarMasDeMilPesosDiarios() {
    cuenta.setSaldo(11000);
    cuenta.realizarExtraccion(500);
    cuenta.realizarExtraccion(301);
    assertThrows(MaximoExtraccionDiarioException.class, () -> cuenta.realizarExtraccion(200));
  }

  @Test
  void puedeRetirar1000PesosEnUnDia() {
    cuenta.setSaldo(11000);
    cuenta.realizarExtraccion(500);
    cuenta.realizarExtraccion(500);
    assertEquals(10000, cuenta.getSaldo());
  }

  @Test
  void laExtraccionFiguraEnSusMovimientos() {
    cuenta.setSaldo(11000);
    cuenta.realizarExtraccion(500);
    assertEquals(1, cuenta.getExtracciones().size());
  }

  @Test
  void puedoRealizarMuchasExtraccionesSinPasarmeDe1000Pesos(){
    cuenta.setSaldo(11000);
    cuenta.realizarExtraccion(10);
    cuenta.realizarExtraccion(50);
    cuenta.realizarExtraccion(30);
    cuenta.realizarExtraccion(150);
    cuenta.realizarExtraccion(200);
    cuenta.realizarExtraccion(300);
    assertEquals(6, cuenta.getExtracciones().size());
  }

}
