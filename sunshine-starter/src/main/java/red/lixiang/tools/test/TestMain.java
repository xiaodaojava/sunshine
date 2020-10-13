package red.lixiang.tools.test;

import org.springframework.beans.BeanUtils;

/**
 * @author lixiang
 * @date 2019/12/20
 **/
public class TestMain {
    public static void main(String[] args) {
        A a = new A();
        a.setName("111");

        B b = new B();
        b.setTt("ttt");

        C c = new C();
        BeanUtils.copyProperties(a,c);
        BeanUtils.copyProperties(b,c);
        System.out.println(c);

    }


    static class A {
        String name;

        public String getName() {
            return name;
        }

        public A setName(String name) {
            this.name = name;
            return this;
        }
    }

   static class B {
        String tt;

        public String getTt() {
            return tt;
        }

        public B setTt(String tt) {
            this.tt = tt;
            return this;
        }
    }

    static class C {
        String name;
        String tt;

        public String getName() {
            return name;
        }

        public C setName(String name) {
            this.name = name;
            return this;
        }

        public String getTt() {
            return tt;
        }

        public C setTt(String tt) {
            this.tt = tt;
            return this;
        }
    }
}
