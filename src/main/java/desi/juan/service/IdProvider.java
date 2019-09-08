package desi.juan.service;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdProvider {

  private static IdProvider instance;

  private final AtomicInteger idProvider = new AtomicInteger(0);

  private IdProvider() {}

  public static IdProvider get() {
    if (instance == null) {
      instance = new IdProvider();
    }
    return instance;
  }

  public String nextGameId() {
    return String.valueOf(idProvider.getAndIncrement());
  }
}
