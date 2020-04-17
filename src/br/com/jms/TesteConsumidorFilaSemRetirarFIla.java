package br.com.jms;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import br.com.jms.util.PropertiesProducerJndi;

public class TesteConsumidorFilaSemRetirarFIla {
	
	@SuppressWarnings({ "resource", "rawtypes" })
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMFila());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination fila = (Destination) context.lookup("financeiro");
		
		QueueBrowser browser = session.createBrowser((Queue) fila);
		
		Enumeration msgs = browser.getEnumeration();
		
		while(msgs.hasMoreElements()) {
			TextMessage msg = (TextMessage) msgs.nextElement();
			
			System.out.println("Message: " + msg.getText());
		}
		
		
		new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
