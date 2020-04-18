package br.com.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.jms.modelo.Pedido;
import br.com.jms.modelo.PedidoFactory;
import br.com.jms.util.PropertiesProducerJndi;

public class TesteProdutorTopic {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext(PropertiesProducerJndi.geraPropertiesMOMTopic());
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin","admin");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//boolean sem transação
		Destination topic = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topic);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
//		
//		StringWriter writer = new StringWriter();
//		JAXB.marshal(pedido, writer);
//		
//		String xml = writer.toString();
		System.out.println(pedido);
		Message message = session.createObjectMessage(pedido);
		message.setBooleanProperty("ebook", false);
		producer.send(message);
		
//		new Scanner(System.in).hasNextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
