package desi.juan.service;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import desi.juan.model.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service {

  private static final IdProvider ID_PROVIDER = IdProvider.get();

  private final Map<String, Game> games = new ConcurrentHashMap<>();

  @RequestMapping(value = "/game", method = POST)
  public String newGame() {
    String id = ID_PROVIDER.nextGameId();
    return id;
  }
}
