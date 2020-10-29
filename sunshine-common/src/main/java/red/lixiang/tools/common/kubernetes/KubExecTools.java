//package red.lixiang.tools.common.kubernetes;
//
//import org.apache.commons.collections4.map.HashedMap;
//import red.lixiang.tools.common.kubernetes.models.Pod;
//
//import java.net.*;
//import java.net.http.HttpClient;
//import java.net.http.WebSocket;
//import java.nio.ByteBuffer;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.ArrayList;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CompletionStage;
//
//
//import static red.lixiang.tools.common.kubernetes.KuberTools.initCert;
//
///**
// * @author lixiang
// * @date 2020/5/31
// **/
//public class KubExecTools {
//
//
//    WebSocket webSocket;
//
//    private KubernetesConfig config;
//    private Pod pod;
//
//
//    public KubExecTools(KubernetesConfig config, Pod pod) {
//        this.config = config;
//        this.pod = pod;
//    }
//
//    public static void main(String[] args) {
//        KubernetesConfig config = new KubernetesConfig("/Users/lixiang/.sunflower/kube/pp-test");
//        List<Pod> allPods = KuberTools.getAllPods("forest-admin", config);
//        KubExecTools tools = new KubExecTools(config, allPods.get(0));
//        new Thread(()->{
//            tools.connect(new WebSocketListener());
//        }).start();
//        try {
//            Thread.sleep(40000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    private String connect(WebSocket.Listener listener) {
//        try {
//
//            StringBuilder builder = new StringBuilder().append(config.getServerApi().replace("https","wss"))
//                    .append("/api/v1/namespaces/")
//                    .append(pod.getNamespace())
//                    .append("/pods/").append(pod.getName())
//                    .append("/exec?command=bash&stderr=true&stdout=true&tty=true&stdin=true");
//            System.out.println(builder.toString());
//            Map<String, String> paramMap = new HashedMap<>();
////            paramMap.put("stdin", "true");
//            HttpClient httpClient = HttpClient.newBuilder()
//                    .sslContext(initCert(config.getUserClientKey(), config.getUserClientCertificate()))
//                    .connectTimeout(Duration.ofMinutes(1)) // connect timeout setting
//                    .followRedirects(HttpClient.Redirect.NORMAL) //redirect policy setting
//                    .build();
//
//            CompletableFuture<WebSocket> wsCompletableFuture = httpClient.newWebSocketBuilder()
//                    .header("X-Stream-Protocol-Version","v4.channel.k8s.io")
//                    .subprotocols("channel.k8s.io","v4.channel.k8s.io")
//                    .buildAsync(URI.create(builder.toString()), listener);
//             webSocket = wsCompletableFuture.join();
////            executor.scheduleAtFixedRate(()->{
////                // send ping msg  to keep alive every 5 second
////                if (webSocket!=null && !webSocket.isOutputClosed()) {
////                    System.out.println("send ping");
////                    var objectMapper = new ObjectMapper(); //jackson
////                    var map = new HashMap<String,Object>(1);
////                    map.put("ping",System.currentTimeMillis());
////                    try {
////                        webSocket.sendText(objectMapper.writeValueAsString(map),true);
////                    } catch (JsonProcessingException ignore) {
////                    }
////                }
////            },5,5, TimeUnit.SECONDS);
//
//            //webSocket.sendText("ls",true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void sendCommand(String command){
//        if(webSocket!=null){
//            webSocket.sendBinary(ByteBuffer.wrap(command.getBytes(StandardCharsets.ISO_8859_1)),true);
//        }
//    }
//
//
//    private static class WebSocketListener implements WebSocket.Listener {
//        private List<ByteBuffer> binaryParts = new ArrayList<>(16); //cache binary data
//
//        @Override
//        public void onOpen(WebSocket webSocket) {
//            System.out.println("websocket opened.");
//            webSocket.request(1);
//        }
//
//        @Override
//        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
//            webSocket.request(1);
//            System.out.println(data);
//            return null;
//        }
//
//
//        @Override
//        public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
//            webSocket.request(1);
//            binaryParts.add(data);
//            if (last) {
//                int size = 0;
//                for (var binaryPart : binaryParts) {
//                    size+=binaryPart.array().length;
//                }
//                ByteBuffer allocate = ByteBuffer.allocate(size);
//                binaryParts.forEach(allocate::put);
//                binaryParts.clear();
//                System.out.println(new String(allocate.array()));
//            }
//            return null;
//        }
//
//        @Override
//        public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
//            webSocket.request(1);
//            System.out.println("ping");
//            System.out.println(message.asCharBuffer().toString());
//            return null;
//        }
//        @Override
//        public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
//            webSocket.request(1);
//            System.out.println("pong");
//            return null;
//        }
//        @Override
//        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
//            System.out.println("closed, status(" + statusCode + "), cause:"+reason);
//            webSocket.sendClose(statusCode, reason);
//            return null;
//        }
//        @Override
//        public void onError(WebSocket webSocket, Throwable error) {
//            System.out.println("error: " + error.getLocalizedMessage());
//            error.printStackTrace();
//            webSocket.abort();
//        }
//
//
//    }
//
//}
