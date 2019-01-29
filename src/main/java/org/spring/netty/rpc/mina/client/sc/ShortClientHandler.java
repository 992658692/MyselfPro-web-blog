package org.spring.netty.rpc.mina.client.sc;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.netty.rpc.mina.client.Result;

public class ShortClientHandler extends IoHandlerAdapter{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sessionCreated(IoSession session) {
		System.out.println("sessionCreated: session 创建成功!");
	}
	
	@Override
	public void sessionClosed(IoSession session) {
		System.out.println("sessionClosed: 一个连接关闭!");
		session.close(true);
	}
	
	@Override
	public void sessionOpened(IoSession session) {
		System.out.println("sessionOpended: session 开启成功！");
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		System.out.println("sessionIdle: sessionIdle");
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.info("exceptionCaught");
		cause.printStackTrace();
		session.close(true);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		Result result = (Result)session.getAttribute(Result.RESULT);
		session.close(true);
		result.set(message);
	}
}
