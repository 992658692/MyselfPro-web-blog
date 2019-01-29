package org.spring.netty.rpc.mina.client.sc;

import org.spring.netty.rpc.mina.client.BaseResult;

public class ShortResult extends BaseResult{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get() throws InterruptedException {
		connectFactory.close(connection);
		return (T) message;
	}

	@Override
	public void set(Object message) {
		this.message = message;
		
	}

}
