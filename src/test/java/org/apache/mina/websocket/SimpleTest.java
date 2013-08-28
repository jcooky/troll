package org.apache.mina.websocket;

import org.apache.log4j.BasicConfigurator;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.http.api.HttpRequest;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Before;
import org.junit.Test;
import org.jwebsocket.api.WebSocketClient;
import org.jwebsocket.client.token.BaseTokenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: dennis
 * Date: 13. 8. 28
 * Time: 오후 12:29
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	class handler implements IoHandler {
		@Override
		public void messageReceived(IoSession session, Object message) {
			HttpRequest request = (HttpRequest)message;

			System.out.println(request.toString());
		}

		@Override
		public void sessionCreated(IoSession session) throws Exception {
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		}

		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
		}
	}

	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}

	@Test
	public void testMe() throws Exception {
		logger.debug("start");

		NioSocketAcceptor server = new NioSocketAcceptor();

		server.setHandler(new handler());
		server.setReuseAddress(true);
		server.getSessionConfig().setReadBufferSize(2048);
		server.getFilterChain().addLast("http", new HttpServerCodec());
		server.bind(new InetSocketAddress(11025));

		WebSocketClient client = new BaseTokenClient();
		client.open("ws://127.0.0.1:11025/");

		client.close();
	}
}