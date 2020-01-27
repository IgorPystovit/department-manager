package com.view;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class Menu {
    protected abstract Map<Integer, String> initializeItems();

    protected abstract Map<Integer, Runnable> initializeActions();

    public void launch() {
        Map<Integer, String> menuItems = initializeItems();
        Map<Integer, Runnable> menuActions = initializeActions();

        menuItems.put(0, "EXIT");
        menuActions.put(0, () -> {
        });

        int requestID;
        do {
            printMenuItems(menuItems);
            requestID = ReaderUtils.readInt();
            try {
                menuActions.get(requestID).run();
            } catch (NullPointerException e) {
                log.error("Bad request id");
            }
        } while (requestID != 0);
    }

    private void printMenuItems(Map<Integer, String> menuItems) {
        log.info("Menu Items:");
        menuItems.forEach((key, value) -> log.info("{} - {}", key, value));
    }
}
