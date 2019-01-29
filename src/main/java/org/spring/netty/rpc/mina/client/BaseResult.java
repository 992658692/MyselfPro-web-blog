package org.spring.netty.rpc.mina.client;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;

public abstract class BaseResult implements Result{
	
	protected ConnectFactory connectFactory;
	
	protected ConnectFuture connection;
	
	protected Object message;
	
	protected boolean done;
	
	/**
	 * 当某个线程在执行前，等待n个线程执行完毕，将CountDownLatch的计数器初始化为n
	 * CountDownLatch(n)
	 * 
	 * 每当一个线程执行完毕就将计数器减1，countdownlatch.countDown()
	 * 当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒
	 * 一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
	 * 
	 * 缺点:CountDownLatch是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用
	 */
	private CountDownLatch latch = new CountDownLatch(1);
	
	@SuppressWarnings("unchecked")
	protected <T> T sybGet() throws InterruptedException {
		if (!done) {
			latch.await();
		}
		
		T result = null;
		if (message instanceof IoBuffer) {
			IoBuffer buf = (IoBuffer) message;
			ByteBuffer bf = buf.buf();
			byte[] data = new byte[bf.limit()];
			bf.get(data);
			result = (T) data;
		} else {
			result = (T)message;
		}
		return result;
	}
	
	protected void synSet(Object message) {
		this.message = message;
		this.done = true;
		latch.countDown();
	}

	@Override
	public abstract <T> T get() throws InterruptedException;

	@Override
	public abstract void set(Object message);

	@Override
	public void setConnectFactory(ConnectFactory connectFactory) {
		this.connectFactory = connectFactory;
	}

	@Override
	public void setConnection(ConnectFuture connection) {
		this.connection = connection;
	}

	@Override
	public ConnectFactory getConnectFactory() {
		return connectFactory;
	}

}
