package red.lixiang.tools.algorithm.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(3);
        deque.push(5);
        Integer poll = deque.pop();
        System.out.println(deque);
    }
}
