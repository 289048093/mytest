package nettytest;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * User:  Sed.Lee(李朝)
 * Date: 14-9-3
 * Time: 下午2:44
 */
public class HelloClient {

    public static void main(String args[]) throws Exception {

        conect();
    }

    static void socket() throws Exception {
        Socket socket = new Socket("127.0.0.1", 8000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello client".getBytes());
        outputStream.close();
        socket.close();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("thread start");
                return "adsf";
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        System.out.println(future.isDone());
        Executors.callable(future).call();

//                                 Executors.newFixedThreadPool(1).execute(future);
        System.out.println(future.isDone());
//        System.out.println();


        System.out.println("start get");

        System.out.println(future.get());
        System.out.println("end..");
    }

    static void conect() {
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        NioClientSocketChannelFactory factory = new NioClientSocketChannelFactory(boss, worker);
        // Client服务启动器
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        // 设置一个处理服务端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        new ObjectEncoder(),
                        new HelloClientHandler());
            }
        });
        // 连接到本地的8000端口的服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(
                "127.0.0.1", 8000));

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String com = scanner.nextLine();
            if(com.equals("end")){
                boss.shutdown();
                worker.shutdown();
                factory.shutdown();
                break;
            }
            if(com.equals("add")){
                bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                    @Override
                    public ChannelPipeline getPipeline() throws Exception {
                        return Channels.pipeline(
                                new ObjectEncoder(),
                                new HelloClientHandler(), new HelloClientHandler(), new HelloClientHandler(), new HelloClientHandler(), new HelloClientHandler());
                    }
                });
//

            }
            if(com.equals("conect")){
                bootstrap.connect(new InetSocketAddress(
                        "127.0.0.1", 8000));
            }
        }

    }

    private static class HelloClientHandler extends SimpleChannelHandler {


        /**
         * 当绑定到服务端的时候触发，打印"Hello world, I'm client."
         *
         * @alia OneCoder
         * @author lihzh
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx,
                                     ChannelStateEvent e) {
            System.out.println("Hello world, I'm client.");
////            ctx.getPipeline().get
//            ObjectEncoder encoder = new ObjectEncoder();
            User user = new User();
            user.setUsername("zs");
            user.setRealname("张三");
            Channel channel = e.getChannel();
            channel.write(user);
            channel.close();


        }


    }
}
