package org.spring.netty.rpc.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class NewTextLineCodecFactory extends TextLineCodecFactory{

	/** 创建一个与当前默认值一致的实例*/
	public NewTextLineCodecFactory() {
		super();
	}
	
	/** 创建一个与指定指一致的实例
	 * 	编码器使用UNIX, 解码器使用AUTO
	 * @param charset 该字符集在编码解码中使用
	 */
	public NewTextLineCodecFactory(String charset) {
		super(Charset.forName(charset));
	}
	
	/**
	 * 创建一个TextLineCodecFactory 新实例 
	 * 这个构造方法为开发人员提供更灵活的服务
	 *  @param encodeDelimiter 编码行定界符
	 *  @param decodeDelimiter 解码行定界符
	 */
	public NewTextLineCodecFactory (String charset, String encodeDelimiter, String decodeDelimiter) {
		super(Charset.forName(charset), encodeDelimiter, decodeDelimiter);
	}
}
