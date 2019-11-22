package cn.net.pwai.framework.ioc.entity;

public class Mouth {

    private String name;

    public Mouth(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(this.name + " say: Hello world");
    }
}
