package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.modelo.Pedido;
import br.com.jms.util.PropertiesProducerJndi;

public class TesteConsumidorFilaDLQ {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMFilaDLQ());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination fila = (Destination) context.lookup("dlq");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				ObjectMessage objMessage = (ObjectMessage) message;
				
				try {
					Pedido pedido = (Pedido) objMessage.getObject();
					
					System.out.println(pedido.toXML());
				
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
