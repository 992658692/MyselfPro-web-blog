package org.spring.netty.rpc.mina.client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.springframework.beans.factory.InitializingBean;

public interface ConnectFactory extends InitializingBean{
	
	int DEFUALT_READ_BUFFER_SIZE = 2048;

	void shutdown();

	void close(ConnectFuture connection);

	ConnectFuture getConnection();

	void build();

	void setProtocolCodecFactory(ProtocolCodecFactory protocolCodecFactory);

	void setReadBufferSize(int readBufferSize);

	void setPort(int port);

	void setHost(String host);

}
