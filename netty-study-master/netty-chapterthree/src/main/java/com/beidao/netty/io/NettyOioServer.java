package com.beidao.netty.io;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.oio.OioSctpServerChannel;
import io.netty.util.CharsetUtil;

public class NettyOioServer {

	public void server(int port) throws Exception {
		final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n",CharsetUtil.UTF_8));
		//�¼�ѭ����
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//������������������
			ServerBootstrap bootstrap = new ServerBootstrap();
			//ʹ��OIO����ģʽ
			bootstrap.group(group).channel(OioSctpServerChannel.class).localAddress(new InetSocketAddress(port))
			//ָ��ChannelInitialer��ʼ��handlers
				.childHandler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						//���һ������վ��handle��ChannelPipline
						ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
							public void channelActive(ChannelHandlerContext ctx) throws Exception{
								//���Ӻ�д��Ϣ���ͻ��ˣ�д���ر�����
								ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
							}
						});
					}
					
				});
			//�󶨷�������������
			ChannelFuture future = bootstrap.bind().sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			//�ͷ�������Դ
			group.shutdownGracefully();
		}
	}
}
