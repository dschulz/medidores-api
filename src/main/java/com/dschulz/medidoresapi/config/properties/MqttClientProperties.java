package com.dschulz.medidoresapi.config.properties;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttClientProperties {
	
	private final Broker broker = new Broker();
	
	public Broker getBroker() {
		return broker;
	}

	
	public static class Broker {
		
		@NotBlank(message = "MQTT broker host address required")
		private String host;
		
		@Min(1)
		@Max(65535)
		private Integer port;
		
		private String user;
		private String password;
		
		private String clientId;
		
		private List<String> topics;
		
		
		public String getClientId() {
			return clientId;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public List<String> getTopics() {
			return topics;
		}
		public void setTopics(List<String> topics) {
			this.topics = topics;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
			
	}
}
