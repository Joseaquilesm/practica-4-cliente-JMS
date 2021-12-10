package controller;

import jms.Consumer;
import org.eclipse.jetty.websocket.api.Session;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import websockets.WebSockets;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;

public class Clientes {
    public static List<Session> sesiones = new ArrayList<>();

    public static void main(String[] args) throws JMSException {
        staticFileLocation("/static/");
        String queue = "sensores";
        webSocket("/sensores", WebSockets.class);
        get("/", (request, response) -> new ModelAndView(new HashMap<String, Object>(), "index"), new ThymeleafTemplateEngine());
        Consumer consumer = new Consumer(queue);
        consumer.connect();
    }

    public static void enviarMensaje(String mensaje) {
        for (Session sesion : sesiones) {
            try {
                sesion.getRemote().sendString(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
