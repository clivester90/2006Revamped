package com.client.model.frames;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.Stream;

public class FramesBase {

	public FramesBase(Stream stream) {
		int length = stream.readUnsignedByte();
		opcode = new int[length];
		skinlist = new int[length][];
		for (int j = 0; j < length; j++) {
			opcode[j] = stream.readUnsignedByte();
		}

		for (int k = 0; k < length; k++) {
			int l = stream.readUnsignedByte();
			skinlist[k] = new int[l];
			for (int i1 = 0; i1 < l; i1++) {
				skinlist[k][i1] = stream.readUnsignedByte();
			}

		}

	}

	public final int[] opcode;
	public final int[][] skinlist;
}
