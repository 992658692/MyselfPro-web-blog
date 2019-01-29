package org.spring.netty.rpc.mina.client.sc;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.spring.netty.rpc.mina.client.ConnectFactory;

import test.ObjectHandler;

public class ShortConnectFactory implements ConnectFactory{
	
	private IoConnector connector;
	
	private String host;
	
	private int port;
	
	private int readBufferSize = DEFUALT_READ_BUFFER_SIZE;
	
	private ProtocolCodecFactory protocolCodecFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init () {
		//设置连接参数
		connector = new NioSocketConnector();
		//设置连接的拦截器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		
		//设置读缓冲， 传输的内容必须小于此缓冲
		connector.getSessionConfig().setReadBufferSize(readBufferSize);
		
		if (protocolCodecFactory != null) {
			//设置连接的拦截器
			connector.getFilterChain().addLast("codec", 
					new ProtocolCodecFilter(protocolCodecFactory));
		}
		//设置客户端的处理器
		//如果服务端用的也是这个处理器，那么就是2个处理器对象 只有消息的通讯可以发送，至于处理器中的其他参数信息则是单独独立的
		ShortClientHandler clientHandler = new ShortClientHandler();
		connector.setHandler(clientHandler);
		
	}
	
	/**
	 * 获取连接
	 */
	@Override
	public ConnectFuture getConnection() {
		//连接服务端
		ConnectFuture connection = 
				connector.connect(new InetSocketAddress(host, port));
		//等待建立连接
		/**
		 * 客户端等待与服务端连接成功
		 * */
		connection.awaitUninterruptibly();
		return connection;
	}
	
	/**
	 * 关闭连接
	 */
	@Override
	public void close(ConnectFuture connection) {
		connection.getSession().getCloseFuture().awaitUninterruptibly();
	}
	
	/**
	 * 关闭资源
	 */
	@Override
	public void shutdown() {
		connector.dispose();
	}
	
	@Override
	public void build() {
		init();
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}
	
	@Override
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}
	
	@Override
	public void setProtocolCodecFactory(ProtocolCodecFactory protocolCodecFactory) {
		this.protocolCodecFactory = protocolCodecFactory;
	}
}
