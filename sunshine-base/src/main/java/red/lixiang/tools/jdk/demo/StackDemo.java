package red.lixiang.tools.jdk.demo;

/**
 * @Author https://www.javastudy.cloud
 * @CreateTime 2019/11/6
 **/
public class StackDemo<E> {
    /** 底层使用一个泛型数组做存储 */
    private E[] items;
    private int top;
    private int capacity;

    public StackDemo() {
        //会调用下面一个构造方法,初始化一个长度为10的数组
        this(10);
    }

    public StackDemo(int i){
        this.capacity=i;
        this.items=(E[])new Object [this.capacity];
        this.top=0;
    }

    /**
     * 入栈操作
     */
    public void push(E e) throws Exception{
        if(top!=capacity){
            items[top++]=e;
            //判断数据是不是已经满了,看需不需要扩容
            resize();
        }else{
            throw new Exception();

        }
    }

    /**
     * 出栈操作
     */
    public E pop() throws Exception{
        if(top==0){
            throw new Exception();
        }else{
            E ret=items[--top];
            items[top]=null;
            return ret;
        }
    }

    /**
     * resize操作,把数组扩大两倍
     */
    public void resize(){
        // 如果top和capacity相等的话,说明满了
        if(top==capacity){
            // 把范围括大2倍
            this.capacity = this.capacity*2;
            // new一个新数组
            E[] newItems = (E[])new Object [this.capacity];
            // 转移老数据
            System.arraycopy(items,0,newItems,0,items.length);
            // 把新的数组当成底层的存储
            this.items = newItems;
        }
    }

    public static void main(String[] args) {
        StackDemo<String> stack = new StackDemo<>(2);
        try {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            stack.push("four");
            String pop = stack.pop();
            System.out.println(pop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
