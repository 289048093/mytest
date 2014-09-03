package nettytest;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String args[]) {
        // Server服务启动器
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        // 设置一个处理客户端消息和各种消息事件的类(Handler)
        bootstrap
                .setPipelineFactory(new ChannelPipelineFactory() {
                    @Override
                    public ChannelPipeline getPipeline()
                            throws Exception {
                        return Channels
                                .pipeline(new ObjectDecoder(
                                        ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),
                                        new HelloServerHandler());
                    }
                });
//        bootstrap.setParentHandler(new HelloServerHandler());
        // 开放8000端口供客户端访问。
        bootstrap.bind(new InetSocketAddress(8000));

        System.out.println("aaaaaaaaaaa");
    }

    private static class HelloServerHandler extends
            SimpleChannelHandler {

        /**
         * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
         *
         * @alia OneCoder
         * @author lihzh
         */
//        @Override
//        public void channelConnected(
//                ChannelHandlerContext ctx,
//                ChannelStateEvent e) {
//            System.out.println("Hello world, I'm server.");
//            System.out.println(e.getValue());
//            System.out.println(e.getState());
//
//        }
//
//        @Override
//        public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
//            Channel ch = ctx.getChannel();
//            System.out.println(ch.getPipeline().toMap());
//            System.out.println(e.getState());
//            System.out.println(e.getValue());
//
//        }
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            User user = (User) e.getMessage();
            System.out.println(user.getUsername());
            System.out.println(user.getRealname());
        }


    }

}
