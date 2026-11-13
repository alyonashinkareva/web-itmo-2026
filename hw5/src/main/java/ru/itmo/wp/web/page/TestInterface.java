package ru.itmo.wp.web.page;

public interface TestInterface {
    default void action() {
        System.out.println("interface");
    };
}
