package marketplace;

import marketplace.observer.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CentralBank implements Subject {
  private List<Observer> observers;
  private double inflationRate;
  private double marketTurnover; 
  private double taxRevenue;

  public CentralBank(double initialInflation) {
    this.observers = new ArrayList<>();
    this.inflationRate = initialInflation;
    this.marketTurnover = 0;
    this.taxRevenue = 0;
  }

  public void setInflationRate(double inflationRate) {
    this.inflationRate = inflationRate;
    System.out.println("Central Bank: Inflation updated to " + this.inflationRate);
    notifyInflationChange(this.inflationRate);
  }

  public double getInflationRate() {
    return inflationRate;
  }

  public void recordTransaction(double amount) {
    this.marketTurnover += amount;
  }
    
  public void endTurnUpdate() {
    this.taxRevenue = this.inflationRate * this.marketTurnover;
    System.out.println("Central Bank: End of turn. Market Turnover: " + marketTurnover + ", Tax Revenue: " + taxRevenue);
    Random random = new Random();
    double currentInflation = this.inflationRate;
    double newInflationRate = currentInflation; 

    if (this.marketTurnover > 0 && this.taxRevenue < 100) { 
      double factor;
      if (currentInflation >= 0.2) { 
        factor = 0.90 + random.nextDouble() * (0.99 - 0.90); 
      } else {
        factor = 1.01 + random.nextDouble() * (1.05 - 1.01); 
      }
      newInflationRate = currentInflation * factor;
    } else if (this.taxRevenue > 150) { 
      double factor = 0.90 + random.nextDouble() * (0.99 - 0.90); 
      newInflationRate = currentInflation * factor;
    } else { 
      double factor = 0.98 + random.nextDouble() * (1.02 - 0.98); 
      newInflationRate = currentInflation * factor;
    }
    
    setInflationRate(newInflationRate);
    this.marketTurnover = 0; 
  }

  @Override
  public void registerObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(String message) {
    for (Observer observer : observers) {
      observer.update(message);
    }
  }
    
  @Override
  public void notifyInflationChange(double newInflation) {
    for (Observer observer : observers) {
      observer.updateInflation(newInflation);
    }
  }
}