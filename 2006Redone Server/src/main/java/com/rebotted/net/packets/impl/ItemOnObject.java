package com.rebotted.net.packets.impl;

import com.rebotted.game.content.combat.range.DwarfCannon;
import com.rebotted.game.content.skills.SkillData;
import com.rebotted.game.content.skills.cooking.Cooking;
import com.rebotted.game.content.skills.cooking.CookingTutorialIsland;
import com.rebotted.game.content.skills.crafting.JewelryMaking;
import com.rebotted.game.content.skills.crafting.Pottery;
import com.rebotted.game.content.skills.crafting.Spinning;
import com.rebotted.game.items.UseItem;
import com.rebotted.game.items.impl.Fillables;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;
import com.rebotted.world.Boundary;
import com.rebotted.world.clip.Region;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		player.getInStream().readUnsignedWord();
		int objectId = player.getInStream().readSignedWordBigEndian();
		int objectY = player.getInStream().readSignedWordBigEndianA();
		player.getInStream().readUnsignedWord();
		int objectX = player.getInStream().readSignedWordBigEndianA();
		int itemId = player.getInStream().readUnsignedWord();
		player.turnPlayerTo(objectX, objectY);
		player.objectX = objectX;
		player.objectY = objectY;
		player.endCurrentTask();
		if (!player.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		if (!player.goodDistance(player.objectX, player.objectY, player.absX, player.absY, 3)) {
			return;
		}
		if (player.getPlayerRights() == 3) {
			player.getPacketSender().sendMessage(
					"Object Id:" + objectId + " ObjectX: " + objectX
							+ " ObjectY: " + objectY + ".");
		}
		switch (objectId) {
		case 3044:
			if (itemId == 438 || itemId == 436) {
				if (player.getItemAssistant().playerHasItem(438) && player.getItemAssistant().playerHasItem(436)) {
					if (player.tutorialProgress == 19) {
						player.startAnimation(899);
						player.getPacketSender().sendSound(352, 100, 1);
						player.getPacketSender().sendMessage("You smelt the copper and tin together in the furnace.");
						player.getItemAssistant().deleteItem(438, 1);
						player.getItemAssistant().deleteItem(436, 1);
						player.getPacketSender().sendMessage("You retrieve a bar of bronze.");
						player.getItemAssistant().addItem(2349, 1);
						player.getDialogueHandler().sendDialogues(3062, -1);
					} else if (player.tutorialProgress > 19) {
						player.startAnimation(899);
						player.getPacketSender().sendSound(352, 100, 1);
						player.getPacketSender().sendMessage("You smelt the copper and tin together in the furnace.");
						player.getItemAssistant().deleteItem(438, 1);
						player.getItemAssistant().deleteItem(436, 1);
						player.getPacketSender().sendMessage("You retrieve a bar of bronze.");
						player.getItemAssistant().addItem(2349, 1);
					}
				}
			}
		break;
		case 2645:
			if (itemId == 1925) {
				player.getItemAssistant().deleteItem(itemId, 1);
				player.getItemAssistant().addItem(1783, 1);
			} else {
				player.getPacketSender().sendMessage("You need a bucket of sand to do that!");
			}
		break;
		case 12269:
		case 2732:
		case 114:
		case 2727:
		case 385:
		case 14919:
		case 2728:
		case 9682:
			if (player.absX == 3014 && player.absY > 3235 && player.absY < 3238
					|| player.absX == 3012 && player.absY == 3239 || player.absX == 3020
					&& player.absY > 3236 && player.absY < 3239 || player.absX > 2805
					&& player.absX < 2813 || player.absY > 3437 && player.absY < 3442) {
				return;
			}
			if (player.tutorialProgress < 36 || Boundary.isIn(player, Boundary.TUTORIAL)) {
				CookingTutorialIsland.cookThisFood(player, itemId, objectId);
			} else {
				Cooking.startCooking(player, itemId, objectId);
			}
			break;

		case 14921:
		case 9390:
		case 2781:
		case 2785:
		case 2966:
		case 3294:
		case 3413:
		case 4304:
		case 4305:
		case 6189:
		case 6190:
		case 11009:
		case 11010:
		case 11666:
		case 12100:
		case 12809:
		if (itemId == 2357) {
			JewelryMaking.mouldInterface(player);
		/*} else if (itemId == SilverCrafting.SILVER_BAR) {
			Menus.sendSkillMenu(c, "silverCrafting");*/
		}
		if (itemId == 2353 || itemId == 4) {
			DwarfCannon.makeBall(player);
		}
		break;

		case 2452:
		case 2453:
		case 2454:
		case 2455:
		case 2456:
		case 2457:
		case 2458:
		case 2459:
		case 2460:
		case 2461:
		case 2462:
			player.getRC().enterAltar(objectId, itemId);
			break;

		case 3039:// tutorial island need to check if it needs break or not
			if (player.getItemAssistant().playerHasItem(2307)
					&& player.tutorialProgress == 8) {
				player.startAnimation(896);
				player.getPlayerAssistant().requestUpdates();
				player.getItemAssistant().deleteItem(2307, 1);
				player.getItemAssistant().addItem(2309, 1);
				player.getDialogueHandler().sendDialogues(3037, 0);
			}
			break;

		case 10093:
			if (player.getItemAssistant().playerHasItem(1927, 1)) {
				player.turnPlayerTo(player.objectX, player.objectY);
				player.startAnimation(883);
				player.getItemAssistant().addItem(2130, 1);
				player.getItemAssistant().deleteItem(1927, 1);
				player.getPlayerAssistant().addSkillXP(18, SkillData.COOKING.getId());
			} else {
				player.getPacketSender().sendMessage("You need a bucket of milk to do this.");
			}
			break;
		}

		if (itemId == 1710 || itemId == 1708 || itemId == 1706
				|| itemId == 1704 && objectId == 2638) { // glory
			int amount = player.getItemAssistant().getItemAmount(1710)
					+ player.getItemAssistant().getItemAmount(1708)
					+ player.getItemAssistant().getItemAmount(1706)
					+ player.getItemAssistant().getItemAmount(1704);
			int[] glories = { 1710, 1708, 1706, 1704 };
			for (int i : glories) {
				player.getItemAssistant().deleteItem(i,
						player.getItemAssistant().getItemAmount(i));
			}
			player.startAnimation(832);
			player.getItemAssistant().addItem(1712, amount);
		}

		if (itemId == 954 && objectId == 3827 && !player.rope) {
			player.getPacketSender().object(3828, 3227, 3108, 0, 0, 10);
			Region.addObject(3828, 3227, 3108, 0, 0, 0, false);
			player.rope = true;
		}

		if (itemId == 954 && objectId == 3830 && !player.rope2) {
			player.getPacketSender().object(3828, 3509, 9497, 2, 0, 10);
			Region.addObject(3828, 3509, 9497, 2, 0, 0, false);
			player.rope2 = true;
		}

		if (itemId == 1737 || itemId == 1779 && objectId == 2644) {
			Spinning.showSpinning(player);
		}

		if (itemId == 1761 && objectId == 2642) {
			Pottery.showUnfire(player);
		}

		if (itemId == 954 && objectId == 2327) {
			player.getPlayerAssistant().movePlayer(2505, 3087, 0);
			player.getItemAssistant().deleteItem(954, 1);
		} else if (objectId == 2327 && itemId != 954) {
			player.getPacketSender().sendMessage("You need a rope to swing across.");
		}

		if (objectId == 2327 && player.absX == 2511 && player.absY == 3092) {
			player.getPlayerAssistant().movePlayer(2510, 3096, 0);
		}

		if (Fillables.canFill(itemId, objectId) && player.getItemAssistant().playerHasItem(itemId)) {
			int amount = player.getItemAssistant().getItemAmount(itemId);
			player.getItemAssistant().deleteItem(itemId, amount);
			player.getItemAssistant().addItem(Fillables.counterpart(itemId), amount);
			player.getPacketSender().sendMessage(Fillables.fillMessage(itemId, objectId));
			player.startAnimation(832);
			return;
		}

		UseItem.itemOnObject(player, objectId, objectX, objectY, itemId);

	}

}
