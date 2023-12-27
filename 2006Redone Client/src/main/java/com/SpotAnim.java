package com;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.model.Model;

public final class SpotAnim {

	public static void unpackConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("spotanim.dat"));
		int length = stream.readUnsignedWord();
		if (cache == null) {
			cache = new SpotAnim[length];
		}
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new SpotAnim();
			}
			cache[j].anInt404 = j;
			cache[j].readValues(stream);
		}

	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt405 = stream.readUnsignedWord();
			} else if (i == 2) {
				anInt406 = stream.readUnsignedWord();
				if (Sequences.anims != null) {
					sequencesSequence = Sequences.anims[anInt406];
				}
			} else if (i == 4) {
				scale = stream.readUnsignedWord();
			} else if (i == 5) {
				height = stream.readUnsignedWord();
			} else if (i == 6) {
				anInt412 = stream.readUnsignedWord();
			} else if (i == 7) {
				ambient = stream.readUnsignedByte();
			} else if (i == 8) {
				contrast = stream.readUnsignedByte();
			} else if (i >= 40 && i < 50) {
				anIntArray408[i - 40] = stream.readUnsignedWord();
			} else if (i >= 50 && i < 60) {
				anIntArray409[i - 50] = stream.readUnsignedWord();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model getModel() {
		Model model = (Model) aMRUNodes_415.insertFromCache(anInt404);
		if (model != null) {
			return model;
		}
		model = Model.method462(anInt405);
		if (model == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray408[0] != 0) {
				model.method476(anIntArray408[i], anIntArray409[i]);
			}
		}

		aMRUNodes_415.removeFromCache(model, anInt404);
		return model;
	}

	private SpotAnim() {
		anInt406 = -1;
		anIntArray408 = new int[6];
		anIntArray409 = new int[6];
		scale = 128;
		height = 128;
	}

	public static SpotAnim[] cache;
	private int anInt404;
	private int anInt405;
	private int anInt406;
	public Sequences sequencesSequence;
	private final int[] anIntArray408;
	private final int[] anIntArray409;
	public int scale;
	public int height;
	public int anInt412;
	public int ambient;
	public int contrast;
	public static MRUNodes aMRUNodes_415 = new MRUNodes(30);

}
