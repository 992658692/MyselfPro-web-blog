package org.spring.netty.rpc.mina.client;

import org.apache.mina.core.future.ConnectFuture;
import org.spring.netty.rpc.mina.client.lc.asyn.AsynResult;
import org.spring.netty.rpc.mina.client.sc.ShortConnectFactory;
import org.spring.netty.rpc.mina.client.sc.ShortResult;

public class ResultUtil {

	public static Result getResult(ConnectFactory connectFactory,ConnectFuture connection) {
		Result result = null;
		if (connectFactory instanceof ShortConnectFactory) {
			result = new ShortResult();
		} else {
			result = new AsynResult();
		}
		
		if (result != null) {
			result.setConnectFactory(connectFactory);
			result.setConnection(connection);
		}
		return result;
	}
}
