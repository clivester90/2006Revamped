package com.client.graphics.builder.impl;

import com.client.graphics.builder.InterfaceBuilder;

public class GameModeInterface extends InterfaceBuilder {
    public GameModeInterface() {
        super(20_000);
    }

    @Override
    public void build() {
        addBackgroundImage(nextInterface(), 450, 300, true);
        child(25, 20);

        addTitleText(nextInterface(), "Game Mode Selection");
        child(250, 30);
    }
}
