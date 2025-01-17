package com.rebotted.game.content.random;

import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.util.Misc;

/**
 * Holiday Drops
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Holidays {

	public static void startDropping(Client c) {
		for (final HolidayDrops holiday : HolidayDrops.values()) {
			for (Player player : PlayerHandler.players) {
				if (player != null) {
					Client p1 = (Client) player;
					if (holiday.getHoliday()) {
						if (p1.getPlayerRights() > 2) {
							p1.getPacketSender().sendMessage("Currently dropping " + HolidayDrops.dropAmount() + " items.");
						}
						p1.getPacketSender().sendMessage("The " + holiday.getName() + " event has started, goodluck!");
						dropItems(c);
					}
				}
			}
		}
	}

	public static void dropItems(Client client) {
		   CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p1 = (Client) PlayerHandler.players[j];
						for (HolidayDrops holiday : HolidayDrops.values()) {
							if (holiday.count >= HolidayDrops.dropAmount() && holiday.getHoliday()) {
								stop();
								p1.getPacketSender().sendMessage("The " + holiday.getName() + " event has ended, good luck finding the rest of the items!");
							} else if (holiday.count < HolidayDrops.dropAmount() && holiday.getHoliday()) {
								switch (Misc.random(holiday.drops)) {
								case 0:// Varrock
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3214 + Misc.random(holiday.DROP_DISTANCE), 3424 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 1:// Lumbridge
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3222 + Misc.random(holiday.DROP_DISTANCE), 3218 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 2:// Falador
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 2964 + Misc.random(holiday.DROP_DISTANCE), 3378 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 3:// Barb Village
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3082 + Misc.random(holiday.DROP_DISTANCE), 3419 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 4:// Draynor
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3082 + Misc.random(holiday.DROP_DISTANCE), 3249 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 5:// Al Kharid
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3293 + Misc.random(holiday.DROP_DISTANCE), 3180 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								case 6:// Rimmington
									GameEngine.itemHandler.createGroundItem(p1, holiday.getItem(), 3034 + Misc.random(holiday.DROP_DISTANCE), 3246 - Misc.random(holiday.DROP_DISTANCE), 1, j);
									holiday.count++;
									break;
								}
							}
						}
					}
				}
			}
			@Override
				public void stop() {
					// TODO Auto-generated method stub
					
				}
		}, 2);
	}
}
