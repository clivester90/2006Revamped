package com;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.model.frames.Frames;

public final class Sequences {

	public static void unpackConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("seq.dat"));
		int length = stream.readUnsignedWord();
		if (anims == null) {
			anims = new Sequences[length];
		}
		for (int j = 0; j < length; j++) {
			if (anims[j] == null) {
				anims[j] = new Sequences();
			}
			anims[j].readValues(stream);
		}
	}

	public int getDuration(int i) {
		int j = duration[i];
		if (j == 0) {
			Frames frames = Frames.get(primary[i]);
			if (frames != null) {
				j = duration[i] = frames.anInt636;
			}
		}
		if (j == 0) {
			j = 1;
		}
		return j;
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				break;
			}
			if (i == 1) {
				length = stream.readUnsignedByte();
				primary = new int[length];
				secondary = new int[length];
				duration = new int[length];
				for (int j = 0; j < length; j++) {
					primary[j] = stream.readUnsignedWord();
					secondary[j] = stream.readUnsignedWord();
					if (secondary[j] == 65535) {
						secondary[j] = -1;
					}
					duration[j] = stream.readUnsignedWord();
				}

			} else if (i == 2) {
				padding = stream.readUnsignedWord();
			} else if (i == 3) {
				int k = stream.readUnsignedByte();
				vertices = new int[k + 1];
				for (int l = 0; l < k; l++) {
					vertices[l] = stream.readUnsignedByte();
				}

				vertices[k] = 0x98967f;
			} else if (i == 4) {
				allowsRotation = true;
			} else if (i == 5) {
				priority1 = stream.readUnsignedByte();
			} else if (i == 6) {
				shield = stream.readUnsignedWord();
			} else if (i == 7) {
				weapon = stream.readUnsignedWord();
			} else if (i == 8) {
				resetCycle = stream.readUnsignedByte();
			} else if (i == 9) {
				runFlag = stream.readUnsignedByte();
			} else if (i == 10) {
				walkFlag = stream.readUnsignedByte();
			} else if (i == 11) {
				delayType = stream.readUnsignedByte();
			} else if (i == 12) {
				stream.readDWord();
			} else {
				System.out.println("Error unrecognised seq config code: " + i);
			}
		} while (true);
		if (length == 0) {
			length = 1;
			primary = new int[1];
			primary[0] = -1;
			secondary = new int[1];
			secondary[0] = -1;
			duration = new int[1];
			duration[0] = -1;
		}
		if (runFlag == -1) {
			if (vertices != null) {
				runFlag = 2;
			} else {
				runFlag = 0;
			}
		}
		if (walkFlag == -1) {
			if (vertices != null) {
				walkFlag = 2;
				return;
			}
			walkFlag = 0;
		}
	}

	private Sequences() {
		padding = -1;
		allowsRotation = false;
		priority1 = 5;
		shield = -1;
		weapon = -1;
		resetCycle = 99;
		runFlag = -1;
		walkFlag = -1;
		delayType = 2;
	}

	public static Sequences[] anims;
	public int length;
	public int[] primary;
	public int[] secondary;
	private int[] duration;
	public int padding;
	public int[] vertices;
	public boolean allowsRotation;
	public int priority1;
	public int shield;
	public int weapon;
	public int resetCycle;
	public int runFlag;
	public int walkFlag;
	public int delayType;
	public static int anInt367;
}
