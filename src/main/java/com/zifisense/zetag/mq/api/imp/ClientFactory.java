package com.zifisense.zetag.mq.api.imp;

import java.security.Provider;

import javax.security.sasl.SaslException;

import com.zifisense.zetag.mq.api.RegionEnum;
import com.zifisense.zetag.mq.api.ZiFiClient;

public class ClientFactory {
	
	private static Object loadImplement(String className)
	        {
	        try {
	            ClassLoader cl = ClientFactory.class.getClassLoader();
	            Class<?> implClass;
	            implClass = Class.forName(className, true, cl);
	            return implClass.newInstance();
	        } catch (ClassNotFoundException e) {
	            throw new RuntimeException("Cannot load class " + className, e);
	        } catch (InstantiationException e) {
	            throw new RuntimeException("Cannot instantiate class " + className, e);
	        } catch (IllegalAccessException e) {
	            throw new RuntimeException("Cannot access class " + className, e);
	        } catch (SecurityException e) {
	            throw new RuntimeException("Cannot access class " + className, e);
	        }
	    }
	
	public static ZiFiClient createClient(ClientType type,RegionEnum region, String apiKey, String apiSecret, String companyCode){
		ZiFiClient client =  (ZiFiClient)loadImplement(type.getName());
		client.init(region, apiKey, apiSecret, companyCode);
		return client;
	}
}
