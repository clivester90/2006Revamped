package com.client.graphics;

import com.*;
import com.client.constants.ColourConstants;
import com.client.definition.ItemDef;
import com.client.model.Model;
import com.client.model.frames.Frames;
import com.google.common.base.Preconditions;
import com.client.constants.ClientSettings;
import com.util.MiscUtils;

public class RSInterface {

    public void swapInventoryItems(int i, int j) {
        int k = inv[i];
        inv[i] = inv[j];
        inv[j] = k;
        k = invStackSizes[i];
        invStackSizes[i] = invStackSizes[j];
        invStackSizes[j] = k;
    }

    public static void unpack(StreamLoader streamLoader, TextDrawingArea[] textDrawingAreas, StreamLoader streamLoader_1) {
        try {
            aMRUNodes_238 = new MRUNodes(50000);
            Stream stream = new Stream(streamLoader.getDataForName("data"));
            int i = -1;
            int j = stream.readUnsignedWord();
            interfaceCache = new RSInterface[200000];
            while (stream.currentOffset < stream.buffer.length) {
                int k = stream.readUnsignedWord();
                if (k == 65535) {
                    i = stream.readUnsignedWord();
                    k = stream.readUnsignedWord();
                }
                RSInterface rsInterface = interfaceCache[k] = new RSInterface();
                rsInterface.id = k;
                rsInterface.parentID = i;
                rsInterface.type = stream.readUnsignedByte();
                //if(rsInterface.type == 0)
                //	System.out.println(rsInterface.parentID+":"+k);
                rsInterface.atActionType = stream.readUnsignedByte();
                rsInterface.contentType = stream.readUnsignedWord();
                rsInterface.width = stream.readUnsignedWord();
                rsInterface.height = stream.readUnsignedWord();
                rsInterface.aByte254 = (byte) stream.readUnsignedByte();
                rsInterface.mOverInterToTrigger = stream.readUnsignedByte();
                if (rsInterface.mOverInterToTrigger != 0) {
                    rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8) + stream.readUnsignedByte();
                } else {
                    rsInterface.mOverInterToTrigger = -1;
                }
                int i1 = stream.readUnsignedByte();
                if (i1 > 0) {
                    rsInterface.anIntArray245 = new int[i1];
                    rsInterface.anIntArray212 = new int[i1];
                    for (int j1 = 0; j1 < i1; j1++) {
                        rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
                        rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
                    }

                }
                int k1 = stream.readUnsignedByte();
                if (k1 > 0) {
                    rsInterface.valueIndexArray = new int[k1][];
                    for (int l1 = 0; l1 < k1; l1++) {
                        int i3 = stream.readUnsignedWord();
                        rsInterface.valueIndexArray[l1] = new int[i3];
                        for (int l4 = 0; l4 < i3; l4++) {
                            rsInterface.valueIndexArray[l1][l4] = stream.readUnsignedWord();
                        }

                    }

                }
                if (rsInterface.type == 0) {
                    rsInterface.scrollMax = stream.readUnsignedWord();
                    rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
                    int i2 = stream.readUnsignedWord();
                    rsInterface.children = new int[i2];
                    rsInterface.childX = new int[i2];
                    rsInterface.childY = new int[i2];
                    for (int j3 = 0; j3 < i2; j3++) {
                        rsInterface.children[j3] = stream.readUnsignedWord();
                        rsInterface.childX[j3] = stream.readSignedWord();
                        rsInterface.childY[j3] = stream.readSignedWord();
                    }

                }
                if (rsInterface.type == 1) {
                    stream.readUnsignedWord();
                    stream.readUnsignedByte();
                }
                if (rsInterface.type == 2) {
                    rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                    rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                    rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
                    rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
                    rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
                    rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
                    rsInterface.invSpritePadX = stream.readUnsignedByte();
                    rsInterface.invSpritePadY = stream.readUnsignedByte();
                    rsInterface.spritesX = new int[20];
                    rsInterface.spritesY = new int[20];
                    rsInterface.sprites = new Sprite[20];
                    for (int j2 = 0; j2 < 20; j2++) {
                        int k3 = stream.readUnsignedByte();
                        if (k3 == 1) {
                            rsInterface.spritesX[j2] = stream.readSignedWord();
                            rsInterface.spritesY[j2] = stream.readSignedWord();
                            String s1 = stream.readString();
                            if (streamLoader_1 != null && !s1.isEmpty()) {
                                int i5 = s1.lastIndexOf(",");
                                rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
                            }
                        }
                    }

                    rsInterface.actions = new String[5];
                    for (int l3 = 0; l3 < 5; l3++) {
                        rsInterface.actions[l3] = stream.readString();
                        if (rsInterface.actions[l3].isEmpty()) {
                            rsInterface.actions[l3] = null;
                        }
                    }
                    if (rsInterface.parentID == 3822) {
                        rsInterface.actions[2] = "Sell 10";
                        rsInterface.actions[3] = "Sell X";
                    }
                    if (rsInterface.id == 3900) {
                        rsInterface.actions[2] = "Buy 10";
                        rsInterface.actions[3] = "Buy X";
                    }
                }
                if (rsInterface.type == 3) {
                    rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
                }
                if (rsInterface.type == 4 || rsInterface.type == 1) {
                    rsInterface.centerText = stream.readUnsignedByte() == 1;
                    int k2 = stream.readUnsignedByte();
                    if (textDrawingAreas != null) {
                        rsInterface.textDrawingAreas = textDrawingAreas[k2];
                    }
                    rsInterface.textShadow = stream.readUnsignedByte() == 1;
                }
                if (rsInterface.type == 4) {
                    rsInterface.disabledText = stream.readString().replaceAll("RuneScape", ClientSettings.SERVER_NAME);
                    rsInterface.enabledText = stream.readString();
                }
                if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4) {
                    rsInterface.textColour = stream.readDWord();
                }
                if (rsInterface.type == 3 || rsInterface.type == 4) {
                    rsInterface.anInt219 = stream.readDWord();
                    rsInterface.anInt216 = stream.readDWord();
                    rsInterface.anInt239 = stream.readDWord();
                }
                if (rsInterface.type == 5) {
                    String s = stream.readString();
                    if (streamLoader_1 != null && !s.isEmpty()) {
                        int i4 = s.lastIndexOf(",");
                        rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
                    }
                    s = stream.readString();
                    if (streamLoader_1 != null && !s.isEmpty()) {
                        int j4 = s.lastIndexOf(",");
                        rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
                    }
                }
                if (rsInterface.type == 6) {
                    int l = stream.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.modelTypeDisabled = 1;
                        rsInterface.mediaID = (l - 1 << 8) + stream.readUnsignedByte();
                    }
                    l = stream.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt255 = 1;
                        rsInterface.anInt256 = (l - 1 << 8) + stream.readUnsignedByte();
                    }
                    l = stream.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt257 = (l - 1 << 8) + stream.readUnsignedByte();
                    } else {
                        rsInterface.anInt257 = -1;
                    }
                    l = stream.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt258 = (l - 1 << 8) + stream.readUnsignedByte();
                    } else {
                        rsInterface.anInt258 = -1;
                    }
                    rsInterface.modelZoom = stream.readUnsignedWord();
                    rsInterface.modelRotationY = stream.readUnsignedWord();
                    rsInterface.modelRotationX = stream.readUnsignedWord();
                }
                if (rsInterface.type == 7) {
                    rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                    rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                    rsInterface.centerText = stream.readUnsignedByte() == 1;
                    int l2 = stream.readUnsignedByte();
                    if (textDrawingAreas != null) {
                        rsInterface.textDrawingAreas = textDrawingAreas[l2];
                    }
                    rsInterface.textShadow = stream.readUnsignedByte() == 1;
                    rsInterface.textColour = stream.readDWord();
                    rsInterface.invSpritePadX = stream.readSignedWord();
                    rsInterface.invSpritePadY = stream.readSignedWord();
                    rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
                    rsInterface.actions = new String[5];
                    for (int k4 = 0; k4 < 5; k4++) {
                        rsInterface.actions[k4] = stream.readString();
                        if (rsInterface.actions[k4].isEmpty()) {
                            rsInterface.actions[k4] = null;
                        }
                    }

                }
                if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
                    rsInterface.selectedActionName = stream.readString();
                    rsInterface.spellName = stream.readString();
                    rsInterface.spellUsableOn = stream.readUnsignedWord();
                }
                if (rsInterface.type == 8) {
                    rsInterface.disabledText = stream.readString();
                }
                if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
                    rsInterface.tooltip = stream.readString();
                    if (rsInterface.tooltip.isEmpty()) {
                        if (rsInterface.atActionType == 1) {
                            rsInterface.tooltip = "Ok";
                        }
                        if (rsInterface.atActionType == 4) {
                            rsInterface.tooltip = "Select";
                        }
                        if (rsInterface.atActionType == 5) {
                            rsInterface.tooltip = "Select";
                        }
                        if (rsInterface.atActionType == 6) {
                            rsInterface.tooltip = "Continue";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printFreeIdRange(100);

        defaultTextDrawingAreas = textDrawingAreas;
        CustomInterfaces.loadInterfaces();

        aMRUNodes_238 = null;
    }

    public static TextDrawingArea[] defaultTextDrawingAreas;
    public static final int OPTION_OK = 1;

    public static final int TYPE_PROGRESS_BAR = 21;
    public static final int TYPE_TEXT_DRAW_FROM_LEFT = 22;
    public static final int TYPE_NEW_HOVER_BUTTON = 42;
    public static final int WRAPPING_TEXT = 44;
    public static final int TYPE_DROPDOWN = 13;

    private static void printFreeIdRange(int minimumFreeSlotsAvailable){
        for(int i = 0; i < interfaceCache.length; i++){
            int freeSlots = 0;
            while(interfaceCache.length > (i+1) && interfaceCache[i++] == null)
                freeSlots++;
            if(freeSlots >= minimumFreeSlotsAvailable)
                System.out.println("RANGE	["+i+", "+(i+freeSlots)+"]	(has "+freeSlots+" free slots)");
        }
    }

    public static RSInterface addInterface(int id) {
        if (interfaceCache[id] != null) {
            //System.err.println("Overwriting interface id: " + id);
        }
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        rsi.id = id;
        rsi.parentID = id;
        rsi.width = 512;
        rsi.height = 334;
        return rsi;
    }

    public static void expandChildren(int amount, RSInterface i) {
        int writeIndex = i.children == null ? 0 : i.children.length;
        int[] newChildren = new int[writeIndex + amount];
        int[] newChildX = new int[writeIndex + amount];
        int[] newChildY = new int[writeIndex + amount];
        if (i.children != null) {
            System.arraycopy(i.children, 0, newChildren, 0, i.children.length);
            System.arraycopy(i.childX, 0, newChildX, 0, i.childX.length);
            System.arraycopy(i.childY, 0, newChildY, 0, i.childY.length);
        }
        i.children = newChildren;
        i.childX = newChildX;
        i.childY = newChildY;
    }


    public void child(int id, int interID, int x, int y) {
        children[id] = interID;
        childX[id] = x;
        childY[id] = y;
    }

    public void totalChildren(int t) {
        children = new int[t];
        childX = new int[t];
        childY = new int[t];
    }

    public static RSInterface get(int interfaceId) {
        Preconditions.checkArgument(interfaceId >= 0 && interfaceId < interfaceCache.length);
        Preconditions.checkArgument(interfaceCache[interfaceId] != null);
        return interfaceCache[interfaceId];
    }

    public RSInterface getChild(int childId) {
        return get(children[childId]);
    }

    public static void addBackgroundImage(int id, int width, int height, boolean divider) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.sprite1 = tab.sprite2 = buildBackground(width, height, divider);
        tab.width = width;
        tab.height = height;
    }

    public int messageOffsetX;
    public int messageOffsetY;
    public int fontType;
    public int msgX, msgY;

    public boolean toggled = false;

    public static void hoverButton(int id, int offSprite, int hoverSprite, String hoverTooltip, int font, int color, String buttonText) {
        RSInterface tab = addInterface(id);
        tab.tooltip = hoverTooltip;
        tab.atActionType = 1;
        tab.type = TYPE_NEW_HOVER_BUTTON;
        tab.sprite1 = Game.spriteCache.lookup(offSprite);
        tab.sprite2 = Game.spriteCache.lookup(hoverSprite);
        tab.width = tab.sprite1.myWidth;
        tab.height = tab.sprite1.height;
        tab.messageOffsetX = tab.width / 2;
        tab.messageOffsetY = (tab.height / 2) + 5;
        tab.disabledText = buttonText;
        tab.textDrawingAreas = defaultTextDrawingAreas[font];
        tab.textColour = color;
        tab.centerText = true;
        tab.toggled = false;
    }

    public void addTitleText(int id, String text) {
        addText(id, text, ColourConstants.DEFAULT_TEXT_COLOR, true, true, -1, defaultTextDrawingAreas, 2);
    }

    public static void addText(int id, String text, int colour, boolean center, boolean shadow, int mouseTrigger, TextDrawingArea[] tda, int fontSize) {
        RSInterface RSInterface = addInterface(id);
        RSInterface.parentID = id;
        RSInterface.id = id;
        RSInterface.type = 4;
        RSInterface.atActionType = 0;
        RSInterface.width = 0;
        RSInterface.height = 0;
        RSInterface.contentType = 0;
        RSInterface.aByte254 = 0;
        RSInterface.mOverInterToTrigger = mouseTrigger;
        RSInterface.centerText = center;
        RSInterface.textShadow = shadow;
        RSInterface.textDrawingAreas = tda[fontSize];
        RSInterface.disabledText = text;
        RSInterface.enabledText = "";
        RSInterface.textColour = colour;
    }

    public static Sprite buildBackground(int width, int height, boolean divider) {
        int[][] pixels = new int[height][width];

        // Background
        fillPixels(pixels, Game.autoBackgroundSprites[0], 0, 0, width, height);

        // Top border
        fillPixels(pixels, Game.autoBackgroundSprites[5], 25, 0, width - 25, 6);

        // Left border
        fillPixels(pixels, Game.autoBackgroundSprites[7], 0, 30, 6, height - 30);

        // Right border
        fillPixels(pixels, Game.autoBackgroundSprites[6], width - 6, 30, width, height - 30);

        // Bottom border
        fillPixels(pixels, Game.autoBackgroundSprites[8], 25, height - 6, width - 25, height);

        // Top left corner
        insertPixels(pixels, Game.autoBackgroundSprites[1], 0, 0, true);

        // Top right corner
        insertPixels(pixels, Game.autoBackgroundSprites[2], width - 25, 0, true);

        // Bottom left corner
        insertPixels(pixels, Game.autoBackgroundSprites[3], 0, height - 30, true);

        // Bottom right corner
        insertPixels(pixels, Game.autoBackgroundSprites[4], width - 25, height - 30, true);

        // Divider
        if (divider)
            fillPixels(pixels, Game.autoBackgroundSprites[5], 6, 29, width - 6, 35);

        return new Sprite(width, height, 0, 0, MiscUtils.d2Tod1(pixels));
    }

    public static void insertPixels(int[][] pixels, Sprite image, int x, int y, boolean ignoreTransparency) {
        int[][] imagePixels = MiscUtils.d1Tod2(image.pixels, image.myWidth);

        for (int j = y; j < y + image.height; j++) {
            for (int i = x; i < x + image.myWidth; i++) {
                if (ignoreTransparency && imagePixels[j - y][i - x] == 0)
                    continue;
                pixels[j][i] = imagePixels[j - y][i - x];
            }
        }
    }

    public static void fillPixels(int[][] pixels, Sprite image, int startX, int startY, int endX, int endY) {
        int[][] imagePixels = MiscUtils.d1Tod2(image.pixels, image.myWidth);

        for (int j = startY; j < endY; j++) {
            for (int i = startX; i < endX; i++) {
                pixels[j][i] = imagePixels[(j - startY) % image.height][(i - startX) % image.myWidth];
            }
        }
    }

    private Model method206(int i, int j) {
        ItemDef itemDefinition = null;
        if (type == 4) {
            itemDefinition = ItemDef.forID(id);
            lightness += itemDefinition.anInt196;
            shading += itemDefinition.anInt184;
        }
        Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
        if (model != null)
            return model;
        if (i == 1)
            model = Model.method462(j);
        if (i == 2)
            model = EntityDef.forID(j).method160();
        if (i == 3)
            model = Game.myPlayer.method453();
        if (i == 4)
            model = ItemDef.forID(j).method202(50);
        if (i == 5)
            model = null;
        if (model != null)
            aMRUNodes_264.removeFromCache(model, (i << 16) + j);
        return model;
    }

    private static Sprite method207(int i, StreamLoader streamLoader, String s) {
        long l = (TextClass.method585(s) << 8) + i;
        Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
        if (sprite != null) {
            return sprite;
        }
        try {
            sprite = new Sprite(streamLoader, s, i);
            aMRUNodes_238.removeFromCache(sprite, l);
        } catch (Exception _ex) {
            return null;
        }
        return sprite;
    }

    public static void discardInterface(int i) {
        if (i == -1)
            return;
        for (int j = 0; j < interfaceCache.length; j++)
            if (interfaceCache[j] != null
                    && interfaceCache[j].parentID == i
                    && interfaceCache[j].type != 2)
                interfaceCache[j] = null;

    }

    public static void method208(Model model, int id, int type) {
        aMRUNodes_264.unlinkAll();
        if (model != null && type != 4) {
            aMRUNodes_264.removeFromCache(model, (type << 16) + id);
        }
    }

    public Model method209(int j, int k, boolean flag) {
        lightness = 64;
        shading = 768;
        Model model;
        if (flag) {
            model = method206(anInt255, anInt256);
        } else {
            model = method206(modelTypeDisabled, mediaID);
        }
        if (model == null) {
            return null;
        }
        if (k == -1 && j == -1 && model.anIntArray1640 == null) {
            return model;
        }
        Model model_1 = new Model(true, Frames.method532(k) & Frames.method532(j), false, model);
        if (k != -1 || j != -1) {
            model_1.method469();
        }
        if (k != -1) {
            model_1.method470(k);
        }
        if (j != -1) {
            model_1.method470(j);
        }
        model_1.method479(lightness, shading, -50, -10, -50, true);
        return model_1;
    }

    public RSInterface() {
    }

    public String hoverText;
    public int opacity;
    public int hoverType;

    public Sprite sprite1;
    public Sprite sprite2;
    public int sequenceCycle;
    public Sprite[] sprites;
    public static RSInterface[] interfaceCache;
    public int[] anIntArray212;
    public int contentType;
    public int[] spritesX;
    public int anInt216;
    public int atActionType;
    public String spellName;
    public int anInt219;
    public int width;
    public String tooltip;
    public String selectedActionName;
    public boolean centerText;
    public int scrollPosition;
    public String[] actions;
    public int[][] valueIndexArray;
    public boolean aBoolean227;
    public String enabledText;
    public int mOverInterToTrigger;
    public int invSpritePadX;
    public int textColour;
    public int modelTypeDisabled;
    public int mediaID;
    public boolean aBoolean235;
    public int parentID;
    public int spellUsableOn;
    private static MRUNodes aMRUNodes_238;
    public int anInt239;
    public int[] children;
    public int[] childX;
    public boolean usableItemInterface;
    public TextDrawingArea textDrawingAreas;
    public int invSpritePadY;
    public int[] anIntArray245;
    public int sequenceFrame;
    public int[] spritesY;
    public String disabledText;
    public boolean isInventoryInterface;
    public int id;
    public int[] invStackSizes;
    public int[] inv;
    public byte aByte254;
    private int anInt255;
    private int anInt256;
    public int anInt257;
    public int anInt258;
    public boolean aBoolean259;
    public int scrollMax;
    public int type;
    public int anInt263;
    private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
    public int anInt265;
    public boolean isMouseoverTriggered;
    public int height;
    public static int shading;
    public static int lightness;
    public boolean textShadow;
    public int modelZoom;
    public int modelRotationY;
    public int modelRotationX;
    public int[] childY;

}
