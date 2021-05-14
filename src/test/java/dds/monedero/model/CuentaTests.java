package dds.monedero.model;

import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuentaTests {

  private Cuenta cuenta;

  @BeforeEach
  void init(){
    cuenta = new Cuenta();
  }

  @Test
  void noPuedoExtraerMasDeLoQueDeposite(){
    cuenta.realizarDeposito(500);
    assertThrows(SaldoMenorException.class, () -> cuenta.realizarExtraccion(600));
  }

  @Test
  void siRealizaExtraccionYDepositoSeVenReflejadosEnSusMovimientos(){
    cuenta.realizarDeposito(500);
    cuenta.realizarExtraccion(350);
    assertEquals(2, cuenta.getMovimientos().size());
  }

  @Test
  void puedoSepararDepositosDeExtraccion(){
    cuenta.realizarDeposito(500);
    cuenta.realizarExtraccion(350);
    assertEquals(1, cuenta.getDepositos().size());
  }

  @Test
  void puedoSepararExtraccionDeDeposito(){
    cuenta.realizarDeposito(500);
    cuenta.realizarExtraccion(350);
    assertEquals(1, cuenta.getExtracciones().size());
  }

}
