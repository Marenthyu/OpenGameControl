package de.marenthyu.ogc.connectors;

public interface GameEventHandler {
    void eventMatched(int amount);
    void eventMatched(String input);
}
