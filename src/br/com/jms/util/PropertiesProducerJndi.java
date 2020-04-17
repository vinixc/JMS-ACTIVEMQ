package br.com.jms.util;

import java.util.Properties;

public class PropertiesProducerJndi {
	
	/**
	 * Gera properties para instanciação do Message Oriented Middleware
	 * @return
	 */
	public static Properties geraPropertiesMOMFila() {
		
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");
		
		return properties;
	}
	
	public static Properties geraPropertiesMOMTopic() {
		
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("topic.loja", "topico.loja");
		
		return properties;
	}

}
