package com.rebotted.game.dialogues.impl;

import com.rebotted.game.dialogues.DialogueBuilder;
import com.rebotted.game.dialogues.DialogueExpression;
import com.rebotted.game.players.Player;

public class ManAndWoman extends DialogueBuilder {
    public ManAndWoman(Player player, int npcId) {
        super(player);

        setNpcId(npcId)
                .npc(DialogueExpression.SPEAKING_CALMLY, "Please leave me alone.")
                .player(DialogueExpression.DISTRESSED, "I'm so sorry, I didn't meant to interrupt.")
                .npc("Well no problem then, best of luck with your day, Adventurer!");
    }
}
