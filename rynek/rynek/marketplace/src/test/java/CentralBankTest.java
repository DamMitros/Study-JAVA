import marketplace.*;
import marketplace.observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CentralBankTest {
  private CentralBank centralBank;
  private TestObserver observer;

  static class TestObserver implements Observer {
    boolean updateCalled = false;
    boolean inflationUpdateCalled = false;
    double receivedInflation = -1;
    String receivedMessage = "";

    @Override
    public void update(String message) {
      updateCalled = true;
      receivedMessage = message;
    }

    @Override
    public void updateInflation(double newInflation) {
      inflationUpdateCalled = true;
      receivedInflation = newInflation;
      }
    }

  @BeforeEach
  void setUp() {
    centralBank = new CentralBank(0.05); 
    observer = new TestObserver();
  }

  @Test
  void testInitialInflationRate() {
    assertEquals(0.05, centralBank.getInflationRate());
  }

  @Test
  void testSetInflationRate() {
    centralBank.registerObserver(observer);
    centralBank.setInflationRate(0.08);
    assertEquals(0.08, centralBank.getInflationRate());
    assertTrue(observer.inflationUpdateCalled);
    assertEquals(0.08, observer.receivedInflation);
  }

  @Test
  void testRegisterAndNotifyObserver() {
    centralBank.registerObserver(observer);
    centralBank.notifyObservers("Test message");
    assertTrue(observer.updateCalled);
    assertEquals("Test message", observer.receivedMessage);
  }

  @Test
  void testRemoveObserver() {
    centralBank.registerObserver(observer);
    centralBank.removeObserver(observer);
    centralBank.notifyObservers("Should not be received");
    assertFalse(observer.updateCalled);
  }

  @Test
  void testRecordTransactionIncreasesTurnover() {
    centralBank.recordTransaction(100.0);
    centralBank.recordTransaction(50.0);
    centralBank.registerObserver(observer);
    centralBank.endTurnUpdate();
    assertTrue(observer.inflationUpdateCalled || centralBank.getInflationRate() != 0.05);
  }

  @Test
  void testEndTurnUpdateLowTaxRevenueChangesInflationUpward() {
  	centralBank.recordTransaction(50.0); 
    centralBank.registerObserver(observer);
    double oldInflation = centralBank.getInflationRate();
    centralBank.endTurnUpdate();
    double newInflation = centralBank.getInflationRate();
    assertTrue(newInflation >= oldInflation);
  }

  @Test
  void testEndTurnUpdateHighTaxRevenueChangesInflationDownward() {
    centralBank.recordTransaction(4000.0); 
    centralBank.registerObserver(observer);
    double oldInflation = centralBank.getInflationRate();
    centralBank.endTurnUpdate();
    double newInflation = centralBank.getInflationRate();
    assertTrue(newInflation <= oldInflation);
  }

  @Test
  void testEndTurnUpdateZeroTurnoverDoesNotChangeInflation() {
    double oldInflation = centralBank.getInflationRate();
    centralBank.endTurnUpdate();  
    assertEquals(oldInflation, centralBank.getInflationRate(), 0.01);
  }

  @Test
  void testInflationNotifiesAllObservers() {
    TestObserver observer2 = new TestObserver();
    centralBank.registerObserver(observer);
    centralBank.registerObserver(observer2);
    centralBank.setInflationRate(0.1);
    assertTrue(observer.inflationUpdateCalled);
    assertTrue(observer2.inflationUpdateCalled);
  }
}
