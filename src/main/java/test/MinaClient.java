package test;

import org.spring.netty.rpc.mina.client.MinaTemplate;
import org.spring.netty.rpc.mina.client.sc.ShortConnectFactory;
import org.spring.netty.rpc.mina.codec.NewTextLineCodecFactory;

public class MinaClient {
	
	public static void main(String[] args) throws InterruptedException {
		MinaTemplate minaTimplate = new MinaTemplate();
		ShortConnectFactory factory = new ShortConnectFactory();
		factory.setHost("localhost");
		factory.setPort(8888);
		//客户端与服务端2变的编码器得相同，否则会报错
		factory.setProtocolCodecFactory(new NewTextLineCodecFactory());
		factory.build();
		minaTimplate.setConnectFactory(factory);
		String s = minaTimplate.sengObject("xxx");
		System.out.println(s);
	}

}
