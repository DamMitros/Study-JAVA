package marketplace.observer;

public interface Observer {
  void update(String message);
  void updateInflation(double newInflation);
}
