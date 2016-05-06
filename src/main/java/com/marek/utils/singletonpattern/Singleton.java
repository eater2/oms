package com.marek.utils.singletonpattern;

//enum singleton class
enum SingletonEnum {
    INSTANCE;
    String url;

    SingletonEnum() {
        url = "url";
    }

    String showUrl() {
        return url;
    }

    public static SingletonEnum getInstance() {
        return INSTANCE;
    }
}

/**
 * Created by marek.papis on 2016-04-20.
 */
public class Singleton {
    private  Singleton(){}
}

//lazy singleton enum
class SingletonLazy {
    private static volatile SingletonLazy instance;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance() {
        if (instance != null) {
            return instance;
        } else {
            synchronized (SingletonLazy.class) {
                instance = new SingletonLazy();
            }
            return instance;
        }

    }
}