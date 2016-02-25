package com.c1000k.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.http.websocketx.client.WebSocketClientHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class NettyWebClient {

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		URI uri = new URI("ws://127.0.0.1:8080/websocket");
		try {
			final WebSocketClientHandler handler = new WebSocketClientHandler(
					WebSocketClientHandshakerFactory.newHandshaker(uri,
							WebSocketVersion.V13, null, false,
							new DefaultHttpHeaders()));

			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ChannelPipeline pipe = ch.pipeline();
							pipe.addLast(new HttpClientCodec(),
									new HttpObjectAggregator(8192), handler);
						}
					});
			Channel channel = boot.connect(uri.getHost(), uri.getPort()).sync()
					.channel();
			handler.handshakeFuture().sync();

			BufferedReader console = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				String msg = console.readLine();
				if (msg == null) {
					break;
				} else if ("bye".equals(msg.toLowerCase())) {
					// bye
					channel.writeAndFlush(new CloseWebSocketFrame());
					channel.closeFuture().sync();
				} else if ("ping".equals(msg.toLowerCase())) {
					// ping
					WebSocketFrame frame = new PingWebSocketFrame(
							Unpooled.wrappedBuffer(new byte[] { 8, 1, 8, 1 }));
					channel.writeAndFlush(frame);
				} else {
					WebSocketFrame frame = new TextWebSocketFrame(msg);
					channel.writeAndFlush(frame);
				}
			}

		} finally {
			group.shutdownGracefully();
		}

	}

}
