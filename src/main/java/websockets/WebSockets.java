package websockets;

import controller.Clientes;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@org.eclipse.jetty.websocket.api.annotations.WebSocket
public class WebSockets {
    @OnWebSocketConnect
    public void crearSesion(Session session) {
        System.out.println("Connection from " + session.getLocalAddress().getAddress().toString());
        Clientes.sesiones.add(session);
    }

    @OnWebSocketClose
    public void removeSession(Session session, int statusCode, String reason) {
        Clientes.sesiones.remove(session);
    }
}
