package org.spring.netty.rpc.mina.client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.spring.netty.rpc.mina.client.lc.asyn.AsynResult;

public class MinaTemplate {

	private ConnectFactory connectFactory;
	
	public <T> T sengObject(T message) throws InterruptedException {
		
		ConnectFuture connection = connectFactory.getConnection();
		Result result = ResultUtil.getResult(connectFactory, connection);
		
		//提取连接中的回话对象
		IoSession session = connection.getSession();
		
		if (result instanceof AsynResult) {
			 
		} else {
			//向客户端自己的回话中插入值
			session.setAttribute(Result.RESULT, result);
			
			//在与服务端的回话中发送回话消息
			if (message instanceof byte[]) {
				byte[] msg = (byte[])message;
				session.write(IoBuffer.wrap(msg));
			} else {
				session.write(message);
			}
		}
		
		//同步阻塞获取响应
		T returnMsg = result.get();
		//此处并不是真正的关闭连接，而是将连接放回连接池
		connectFactory.close(connection);
		return returnMsg;
	}
	
	public void setConnectFactory(ConnectFactory connectFactory) {
		this.connectFactory = connectFactory;
	}
	
}
