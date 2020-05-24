package red.lixiang.tools.jdk.simple;

import red.lixiang.tools.base.exception.BusinessException;
import red.lixiang.tools.jdk.ToolsLogger;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @Author https://www.javastudy.cloud
 * @CreateTime 2019/11/6
 **/
public class SimpleStack<E> {
    /** 底层使用一个泛型数组做存储 */
    private E[] items;
    private int top;
    private int capacity;

    public SimpleStack() {
        //会调用下面一个构造方法,初始化一个长度为10的数组
        this(10);
    }

    public SimpleStack(int i){
        this.capacity=i;
        this.items=(E[])new Object [this.capacity];
        this.top=0;
    }

    /**
     * 入栈操作
     */
    public void push(E e){
        if(top!=capacity){
            items[top++]=e;
            //判断数据是不是已经满了,看需不需要扩容
            resize();
        }else{
            throw new BusinessException("栈已满");
        }
    }

    /**
     * 获取现在在栈顶的元素
     * @return
     */
    public E top(){
        return items[top-1];
    }

    public List<E> toList(){
        return Arrays.asList(items);
    }

    public String showString(Function<E,String> function){
        StringBuilder stringBuilder = new StringBuilder();
        for (E item : items) {
            if(item == null){
                break;
            }
            String s = function.apply(item);
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    /**
     * 出栈操作
     */
    public E pop() {
        if(top==0){
            throw new BusinessException("栈已空");
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
        SimpleStack<String> stack = new SimpleStack<>(2);
        try {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            stack.push("four");
            String pop = stack.pop();
            String pop1 = stack.pop();
            String pop2 = stack.pop();
            String pop3= stack.pop();
            ToolsLogger.out(pop,pop1,pop2,pop3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
