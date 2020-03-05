package com.dschulz.medidoresapi.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import com.dschulz.medidoresapi.service.MailService;

@Configuration
@Profile("mqtt")
public class MqttIntegrationConfiguration {

	@Autowired
	MailService ms;
	
	@Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

	
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { "ssl://mqtt.8bit.com.py:8883" });
		options.setUserName("mondongo");
		options.setPassword("mondongo".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }
	
	
    @Bean
    public MessageProducer inbound() {
    	
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("testClient", mqttClientFactory(),
                                                 "topic1", "topic2");
       
        
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler handler() {
//        return new MessageHandler() {
//
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println("\n-----------\n" + message.getPayload() + "\n-----------\n" + message.getHeaders());
//          
//            }
//            
//            @Transformer
//            Coso generarCoso(String nombre, String mensaje ) {
//            	
//            	return new Coso(nombre, mensaje);
//            }
//            
//
//        };
//    }
    
    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from("mqttInputChannel")
            .transform(Transformers.fromJson(Coso.class))
            .handle("coso", "printCoso")
            .get();
    }
    
    
    @Bean
    public Coso coso() {
    	return new Coso();
    }
    

    public static class Coso {
    	
    	public void printCoso(Coso c) {
    		System.err.println("Coso: " + c);
    	}
    	
    	@Override
		public String toString() {
			return "Coso [nombre=" + nombre + ", mensaje=" + mensaje + "]";
		}
    	
		public Coso() {
			super();
			this.nombre = null;
			this.mensaje = null;
		}
		
		public Coso(String nombre, String mensaje) {
			super();
			this.nombre = nombre;
			this.mensaje = mensaje;
		}
    	
		String nombre;
    	String mensaje;

    	public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
    }
	
	  
    
    
	
}
