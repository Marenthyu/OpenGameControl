package de.marenthyu.ogc.connectors;

public interface ServiceConnector<EventType> {
    void registerEventHandler(EventType event, GameEventHandler handler);
}
