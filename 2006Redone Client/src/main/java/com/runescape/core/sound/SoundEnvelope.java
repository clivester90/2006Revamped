package com.runescape.core.sound;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;

final class SoundEnvelope {

	public void method325(ByteBuffer byteBuffer) {
		anInt540 = byteBuffer.readUnsignedByte();
		anInt538 = byteBuffer.readDWord();
		anInt539 = byteBuffer.readDWord();
		method326(byteBuffer);
	}

	public void method326(ByteBuffer byteBuffer) {
		anInt535 = byteBuffer.readUnsignedByte();
		anIntArray536 = new int[anInt535];
		anIntArray537 = new int[anInt535];
		for (int i = 0; i < anInt535; i++) {
			anIntArray536[i] = byteBuffer.readUnsignedWord();
			anIntArray537[i] = byteBuffer.readUnsignedWord();
		}

	}

	void resetValues() {
		anInt541 = 0;
		anInt542 = 0;
		anInt543 = 0;
		anInt544 = 0;
		anInt545 = 0;
	}

	int method328(int i) {
		if (anInt545 >= anInt541) {
			anInt544 = anIntArray537[anInt542++] << 15;
			if (anInt542 >= anInt535) {
				anInt542 = anInt535 - 1;
			}
			anInt541 = (int) (anIntArray536[anInt542] / 65536D * i);
			if (anInt541 > anInt545) {
				anInt543 = ((anIntArray537[anInt542] << 15) - anInt544) / (anInt541 - anInt545);
			}
		}
		anInt544 += anInt543;
		anInt545++;
		return anInt544 - anInt543 >> 15;
	}

	public SoundEnvelope() {
	}

	private int anInt535;
	private int[] anIntArray536;
	private int[] anIntArray537;
	int anInt538;
	int anInt539;
	int anInt540;
	private int anInt541;
	private int anInt542;
	private int anInt543;
	private int anInt544;
	private int anInt545;
	public static int anInt546;
}