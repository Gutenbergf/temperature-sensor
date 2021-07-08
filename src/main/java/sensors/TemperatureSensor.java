package sensors;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import utils.Constants;

public class TemperatureSensor {
	
	private static MqttClient mqttClient;

	public static void main(String[] args) throws MqttException, InterruptedException {
		

		mqttClient = new MqttClient(Constants.broker,String.valueOf(System.nanoTime()));
		

		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setKeepAliveInterval(1000);
		
		
		mqttClient.connect(connOpts);
		MqttTopic topic = mqttClient.getTopic(Constants.topicName);
		
		System.out.println("Temperature Sensor is ON!!");
	    
	    while(true) {
	    	Thread.sleep(60000);
	    	MqttMessage message = new MqttMessage(Double.toString(getCurrentyTemperature()).getBytes());
			message.setQos(Constants.qos);
		    message.setRetained(true);
		    topic.publish(message);    
	    }   
		
		
	}
	
	public static Double getCurrentyTemperature() {		
		Double minTemp = 0.0;
		Double maxTemp = 2000.0;
		return ThreadLocalRandom.current().nextDouble(minTemp, maxTemp + 1);
	}
}
