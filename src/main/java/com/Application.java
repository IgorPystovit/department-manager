package com;

public class Application {
    public static void main(String[] args) {
        ApplicationInitializer initializer = new ApplicationInitializer();
        initializer.init();
        initializer.getMenu().launch();
    }
}
