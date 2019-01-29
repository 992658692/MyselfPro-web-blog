package org.spring.netty.rpc.mina.client.lc.asyn;

import org.spring.netty.rpc.mina.client.BaseResult;

public class AsynResult extends BaseResult{

	@Override
	public <T> T get() throws InterruptedException {
		T message = sybGet();
		return message;
	}

	@Override
	public void set(Object message) {
		synSet(message);
	}

}
