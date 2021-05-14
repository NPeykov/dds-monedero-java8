package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MontoNegativoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositoTests {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void puedoDepositarUnMontoPositivo() {
    cuenta.realizarDeposito(1500);
    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void lanzaErrorAlDepositarUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.realizarDeposito(-5));
  }

  @Test
  void tiraErrorAlDepositarUnMontoNulo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.realizarDeposito(0));
  }

  @Test
  void noPuedoRealizarMasDe3DepositosEnUnDia() {
    cuenta.realizarDeposito(200);
    cuenta.realizarDeposito(300);
    cuenta.realizarDeposito(50);
    assertThrows(MaximaCantidadDepositosException.class, () -> cuenta.realizarDeposito(130));
  }

  @Test
  void puedoRealizar3DepositosEnUnDia() {
    cuenta.realizarDeposito(200);
    cuenta.realizarDeposito(300);
    cuenta.realizarDeposito(350);
    assertEquals(850, cuenta.getSaldo());
  }

  @Test
  void losDepositosRealizadosAparecenEnLosMovimientos() {
    cuenta.realizarDeposito(200);
    cuenta.realizarDeposito(300);
    assertEquals(2, cuenta.getMovimientos().size());
  }

  @Test
  void puedoRealizarDepositosConMontoGrande() {
    cuenta.realizarDeposito(2000);
    cuenta.realizarDeposito(30022);
    assertEquals(2, cuenta.getMovimientos().size());
  }

}
