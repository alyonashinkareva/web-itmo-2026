package ru.itmo.wp.web.page.misc;

public interface IInfoPage {
    default void action(){
        System.out.println("Hello");
    }
}
