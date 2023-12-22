// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class TileUnderlay {

	public TileUnderlay(int i, int j, int k, int l, int i1, int j1, boolean flag) {
		isFlatShaded = true;
		swHSL = i;
		neHSL = j;
		nwHSL = k;
		seHSL = l;
		texture = i1;
		rgb = j1;
		isFlatShaded = flag;
	}

	final int swHSL;
	final int neHSL;
	final int nwHSL;
	final int seHSL;
	final int texture;
	boolean isFlatShaded;
	final int rgb;
}
