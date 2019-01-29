package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.spring.netty.rpc.mina.codec.NewTextLineCodecFactory;

public class MinaServer {
	
	private IoAcceptor acceptor;
	
	public MinaServer() throws IOException {
		acceptor = new NioSocketAcceptor();
		//设置日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		//设置编码器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new NewTextLineCodecFactory()));
		
		
		acceptor.getFilterChain().addLast("threadPool",new ExecutorFilter(Executors.newCachedThreadPool()));
		//设置读缓冲
        acceptor.getSessionConfig().setReadBufferSize(2048*2048);
        //设置心跳频率
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 30);
		//设置Handler
		acceptor.setHandler(new ObjectHandler());
		acceptor.bind(new InetSocketAddress("localhost", 8888));
		System.out.println("--------------------------------------------------");
		System.out.println("Server Started");
		System.out.println("--------------------------------------------------");
	}
	
	public static void main(String[] args) throws IOException {
		MinaServer m = new MinaServer();
	}

}
