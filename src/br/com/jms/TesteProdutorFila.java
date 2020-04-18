package br.com.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.util.PropertiesProducerJndi;

public class TesteProdutorFila {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMFila());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		
		
//		for(int i = 0;i <1000;i++) {
			Message message = session.createTextMessage("<pedido><id>"+12+"</id></pedido>");
			producer.send(message);
//		}
		
		
//		new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
