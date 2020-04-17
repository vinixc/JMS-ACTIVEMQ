package br.com.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteConsumidor {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transa��o
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		Message message = consumer.receive();
		
		System.out.println("Recebendo Message concatenando " + message);
		//new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
