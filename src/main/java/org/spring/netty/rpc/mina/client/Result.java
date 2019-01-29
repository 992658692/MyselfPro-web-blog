package org.spring.netty.rpc.mina.client;

import org.apache.mina.core.future.ConnectFuture;

public interface Result {
	
	public static final String RESULT = "result";

	public static final String RESULT_MAP = "resultMap";
	
	public static final String COUNTER = "counter";
	
	<T> T get() throws InterruptedException;
	
	void set(Object message);
	
	void setConnectFactory(ConnectFactory connectFactory);
	
	void setConnection(ConnectFuture connection);
	
	ConnectFactory getConnectFactory();
}
