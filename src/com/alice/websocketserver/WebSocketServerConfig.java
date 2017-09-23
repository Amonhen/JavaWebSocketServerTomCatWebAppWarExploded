package com.alice.websocketserver;

import javax.websocket.server.ServerEndpointConfig;

public class WebSocketServerConfig extends ServerEndpointConfig.Configurator{

    private static ServerEndpoint serverEndpoint = new ServerEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) serverEndpoint;
    }
}
