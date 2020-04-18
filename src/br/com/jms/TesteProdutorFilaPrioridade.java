package br.com.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.util.PropertiesProducerJndi;

public class TesteProdutorFilaPrioridade {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMFilaLog());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination fila = (Destination) context.lookup("log");
		
		MessageProducer producer = session.createProducer(fila);
		
		
//		for(int i = 0;i <1000;i++) {
			Message message = session.createTextMessage("INFO | LOGANDO NO SISTEMA");
			producer.send(message,DeliveryMode.NON_PERSISTENT,3,5000);
//		}
		
		
//		new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
