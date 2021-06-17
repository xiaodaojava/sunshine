package red.lixiang.tools.demo.thread;


import red.lixiang.tools.jdk.thread.ThreadTools;

public class VolatileDemo {

    private volatile  int a = 3;

    public void change(){
        a=4;
    }

    public void test(){

        Thread t1 = new Thread(()->{
            change();
        });

        t1.start();
        ThreadTools.sleep(100);
        while (a!=4){

        }
        System.out.println("main end");
    }



    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.test();

    }


}
