package com.client.graphics;

import com.client.graphics.builder.impl.GameModeInterface;

public class CustomInterfaces {

    private CustomInterfaces() {}

    public static void loadInterfaces() {
        new GameModeInterface().build();
    }

}
