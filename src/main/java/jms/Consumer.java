package jms;

import controller.Clientes;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Topic topic;
    MessageConsumer consumer;
    String topicName;

    public Consumer(String topicName) {
        this.topicName = topicName;
    }

    public void connect() throws JMSException {

        factory = new ActiveMQConnectionFactory("failover:tcp://localhost:61616");
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicName);
        consumer = session.createConsumer(topic);
        System.out.println("Listo para recibir.");
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
                Clientes.enviarMensaje(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}