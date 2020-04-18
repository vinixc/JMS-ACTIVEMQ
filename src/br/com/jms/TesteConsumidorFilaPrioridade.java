package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import br.com.jms.util.PropertiesProducerJndi;

public class TesteConsumidorFilaPrioridade {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMFilaLog());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user","senha");
		connection.start();
		
		/**
		 * UTILIZANDO CONCEITO DE TRANSAÇÃO.
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination fila = (Destination) context.lookup("log");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
				
				TextMessage textMessage = (TextMessage) message;
					
					/**
					 * (Session.CLIENT_ACKNOWLEDGE)
					 * UTILIZANDO CLIENT ACKNOWLEDGE PRECISAMOS CONFIRMAR
					 * O RECEBIMENTO DA MENSAGEM.
					 */
					//message.acknowledge();
				
					System.out.println(textMessage.getText());
				
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
