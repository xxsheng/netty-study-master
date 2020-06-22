package com.beidao.netty.demo.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class EchoClientFilter extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		  ChannelPipeline ph = ch.pipeline();
	        /*
	         * ����ͱ��룬Ӧ�ͷ����һ��
	         * */
	        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
	        ph.addLast("decoder", new StringDecoder());
	        ph.addLast("encoder", new StringEncoder());
	        ph.addLast("handler", new EchoClientHandler()); //�ͻ��˵��߼�
	}

}
