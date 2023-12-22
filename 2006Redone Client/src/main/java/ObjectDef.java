// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ObjectDef {

	public static ObjectDef forID(int i) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == i) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef class46 = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		class46.type = i;
		class46.setDefaults();
		class46.readValues(stream);
		if (i == 6) {
			class46.actions[1] = "Load";
			class46.actions[2] = "Pick-up";
		}
		switch (i) {

		}
		return class46;
	}

	private void setDefaults() {
		models = null;
		modelTypes = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		sizeX = 1;
		sizeY = 1;
		blocksWalk = true;
		blocksProjectiles = true;
		hasActions = false;
		adjustsToTerrain = false;
		isFlatShaded = false;
		isSolid = false;
		animationID = -1;
		wallWidth = 16;
		brightness = 0;
		contrast = 0;
		actions = null;
		minimapFunction = -1;
		anInt758 = -1;
		rotateLeft = false;
		castsShadow = true;
		scaleX = 128;
		scaleY = 128;
		anInt740 = 128;
		rotationFlags = 0;
		offsetX = 0;
		offsetY = 0;
		offsetZ = 0;
		isDecoration = false;
		ghost = false;
		holdsItemPiles = -1;
		varbit = -1;
		setting = -1;
		childrenIDs = null;
	}

	public void method574(OnDemandFetcher class42_sub1) {
		if (models == null) {
			return;
		}
		for (int element : models) {
			class42_sub1.method560(element & 0xffff, 0);
		}
	}

	public static void nullLoader() {
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public static void unpackConfig(StreamLoader streamLoader) {
		stream = new Stream(streamLoader.getDataForName("loc.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("loc.idx"));
		int totalObjects = stream.readUnsignedWord();
		streamIndices = new int[totalObjects];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}

		cache = new ObjectDef[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDef();
		}

	}

	public boolean method577(int i) {
		if (modelTypes == null) {
			if (models == null) {
				return true;
			}
			if (i != 10) {
				return true;
			}
			boolean flag1 = true;
			for (int element : models) {
				flag1 &= Model.method463(element & 0xffff);
			}

			return flag1;
		}
		for (int j = 0; j < modelTypes.length; j++) {
			if (modelTypes[j] == i) {
				return Model.method463(models[j] & 0xffff);
			}
		}

		return true;
	}

	public Model method578(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = method581(i, k1, j);
		if (model == null) {
			return null;
		}
		if (adjustsToTerrain || isFlatShaded) {
			model = new Model(adjustsToTerrain, isFlatShaded, model);
		}
		if (adjustsToTerrain) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.anInt1626; i2++) {
				int j2 = model.anIntArray1627[i2];
				int k2 = model.anIntArray1629[i2];
				int l2 = k + (l - k) * (j2 + 64) / 128;
				int i3 = j1 + (i1 - j1) * (j2 + 64) / 128;
				int j3 = l2 + (i3 - l2) * (k2 + 64) / 128;
				model.anIntArray1628[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (models == null) {
			return true;
		}
		boolean flag1 = true;
		for (int element : models) {
			flag1 &= Model.method463(element & 0xffff);
		}
		return flag1;
	}

	public ObjectDef method580() {
		int i = -1;
		if (varbit != -1) {
			VarBit varBit = VarBit.cache[varbit];
			int j = varBit.anInt648;
			int k = varBit.anInt649;
			int l = varBit.anInt650;
			int i1 = Game.anIntArray1232[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (setting != -1) {
			i = clientInstance.variousSettings[setting];
		}
		if (i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1) {
			return null;
		} else {
			return forID(childrenIDs[i]);
		}
	}

	private Model method581(int j, int k, int l) {
		Model model = null;
		long l1;
		if (modelTypes == null) {
			if (j != 10) {
				return null;
			}
			l1 = (type << 6) + l + ((long) (k + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if (model_1 != null) {
				return model_1;
			}
			if (models == null) {
				return null;
			}
			boolean flag1 = rotateLeft ^ l > 3;
			int k1 = models.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = models[i2];
				if (flag1) {
					l2 += 0x10000;
				}
				model = (Model) mruNodes1.insertFromCache(l2);
				if (model == null) {
					model = Model.method462(l2 & 0xffff);
					if (model == null) {
						return null;
					}
					if (flag1) {
						model.method477();
					}
					mruNodes1.removeFromCache(model, l2);
				}
				if (k1 > 1) {
					aModelArray741s[i2] = model;
				}
			}

			if (k1 > 1) {
				model = new Model(k1, aModelArray741s);
			}
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < modelTypes.length; j1++) {
				if (modelTypes[j1] != j) {
					continue;
				}
				i1 = j1;
				break;
			}

			if (i1 == -1) {
				return null;
			}
			l1 = (type << 6) + (i1 << 3) + l + ((long) (k + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if (model_2 != null) {
				return model_2;
			}
			int j2 = models[i1];
			boolean flag3 = rotateLeft ^ l > 3;
			if (flag3) {
				j2 += 0x10000;
			}
			model = (Model) mruNodes1.insertFromCache(j2);
			if (model == null) {
				model = Model.method462(j2 & 0xffff);
				if (model == null) {
					return null;
				}
				if (flag3) {
					model.method477();
				}
				mruNodes1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = scaleX != 128 || scaleY != 128 || anInt740 != 128;
		boolean flag2;
		flag2 = offsetX != 0 || offsetY != 0 || offsetZ != 0;
		Model model_3 = new Model(modifiedModelColors == null, Class36.method532(k), l == 0 && k == -1 && !flag && !flag2, model);
		if (k != -1) {
			model_3.method469();
			model_3.method470(k);
			model_3.anIntArrayArray1658 = null;
			model_3.anIntArrayArray1657 = null;
		}
		while (l-- > 0) {
			model_3.method473();
		}
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);
			}

		}
		if (flag) {
			model_3.method478(scaleX, anInt740, scaleY);
		}
		if (flag2) {
			model_3.method475(offsetX, offsetY, offsetZ);
		}
		model_3.method479(64 + brightness, 768 + contrast * 5, -50, -10, -50, !isFlatShaded);
		if (holdsItemPiles == 1) {
			model_3.anInt1654 = model_3.modelHeight;
		}
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}

	private void readValues(Stream stream) {
		int i = -1;
		label0 : do {
			int j;
			do {
				j = stream.readUnsignedByte();
				if (j == 0) {
					break label0;
				}
				if (j == 1) {
					int k = stream.readUnsignedByte();
					if (k > 0) {
						if (models == null || lowMem) {
							modelTypes = new int[k];
							models = new int[k];
							for (int k1 = 0; k1 < k; k1++) {
								models[k1] = stream.readUnsignedWord();
								modelTypes[k1] = stream.readUnsignedByte();
							}

						} else {
							stream.currentOffset += k * 3;
						}
					}
				} else if (j == 2) {
					name = stream.readString();
				} else if (j == 3) {
					description = stream.readBytes();
				} else if (j == 5) {
					int l = stream.readUnsignedByte();
					if (l > 0) {
						if (models == null || lowMem) {
							modelTypes = null;
							models = new int[l];
							for (int l1 = 0; l1 < l; l1++) {
								models[l1] = stream.readUnsignedWord();
							}

						} else {
							stream.currentOffset += l * 2;
						}
					}
				} else if (j == 14) {
					sizeX = stream.readUnsignedByte();
				} else if (j == 15) {
					sizeY = stream.readUnsignedByte();
				} else if (j == 17) {
					blocksWalk = false;
				} else if (j == 18) {
					blocksProjectiles = false;
				} else if (j == 19) {
					i = stream.readUnsignedByte();
					if (i == 1) {
						hasActions = true;
					}
				} else if (j == 21) {
					adjustsToTerrain = true;
				} else if (j == 22) {
					isFlatShaded = true;
				} else if (j == 23) {
					isSolid = true;
				} else if (j == 24) {
					animationID = stream.readUnsignedWord();
					if (animationID == 65535) {
						animationID = -1;
					}
				} else if (j == 28) {
					wallWidth = stream.readUnsignedByte();
				} else if (j == 29) {
					brightness = stream.readSignedByte();
				} else if (j == 39) {
					contrast = stream.readSignedByte();
				} else if (j >= 30 && j < 39) {
					if (actions == null) {
						actions = new String[5];
					}
					actions[j - 30] = stream.readString();
					if (actions[j - 30].equalsIgnoreCase("hidden")) {
						actions[j - 30] = null;
					}
				} else if (j == 40) {
					int i1 = stream.readUnsignedByte();
					modifiedModelColors = new int[i1];
					originalModelColors = new int[i1];
					for (int i2 = 0; i2 < i1; i2++) {
						modifiedModelColors[i2] = stream.readUnsignedWord();
						originalModelColors[i2] = stream.readUnsignedWord();
					}

				} else if (j == 60) {
					minimapFunction = stream.readUnsignedWord();
				} else if (j == 62) {
					rotateLeft = true;
				} else if (j == 64) {
					castsShadow = false;
				} else if (j == 65) {
					scaleX = stream.readUnsignedWord();
				} else if (j == 66) {
					scaleY = stream.readUnsignedWord();
				} else if (j == 67) {
					anInt740 = stream.readUnsignedWord();
				} else if (j == 68) {
					anInt758 = stream.readUnsignedWord();
				} else if (j == 69) {
					rotationFlags = stream.readUnsignedByte();
				} else if (j == 70) {
					offsetX = stream.readSignedWord();
				} else if (j == 71) {
					offsetY = stream.readSignedWord();
				} else if (j == 72) {
					offsetZ = stream.readSignedWord();
				} else if (j == 73) {
					isDecoration = true;
				} else if (j == 74) {
					ghost = true;
				} else {
					if (j != 75) {
						continue;
					}
					holdsItemPiles = stream.readUnsignedByte();
				}
				continue label0;
			} while (j != 77);
			varbit = stream.readUnsignedWord();
			if (varbit == 65535) {
				varbit = -1;
			}
			setting = stream.readUnsignedWord();
			if (setting == 65535) {
				setting = -1;
			}
			int j1 = stream.readUnsignedByte();
			childrenIDs = new int[j1 + 1];
			for (int j2 = 0; j2 <= j1; j2++) {
				childrenIDs[j2] = stream.readUnsignedWord();
				if (childrenIDs[j2] == 65535) {
					childrenIDs[j2] = -1;
				}
			}

		} while (true);
		if (i == -1) {
			hasActions = models != null && (modelTypes == null || modelTypes[0] == 10);
			if (actions != null) {
				hasActions = true;
			}
		}
		if (ghost) {
			blocksWalk = false;
			blocksProjectiles = false;
		}
		if (holdsItemPiles == -1) {
			holdsItemPiles = blocksWalk ? 1 : 0;
		}
	}

	private ObjectDef() {
		type = -1;
	}

	public boolean isDecoration;
	private byte brightness;
	private int offsetX;
	public String name;
	private int anInt740;
	private static final Model[] aModelArray741s = new Model[4];
	private byte contrast;
	public int sizeX;
	private int offsetY;
	public int minimapFunction;
	private int[] originalModelColors;
	private int scaleX;
	public int setting;
	private boolean rotateLeft;
	public static boolean lowMem;
	private static Stream stream;
	public int type;
	private static int[] streamIndices;
	public boolean blocksProjectiles;
	public int anInt758;
	public int[] childrenIDs;
	private int holdsItemPiles;
	public int sizeY;
	public boolean adjustsToTerrain;
	public boolean isSolid;
	public static Game clientInstance;
	private boolean ghost;
	public boolean blocksWalk;
	public int rotationFlags;
	private boolean isFlatShaded;
	private static int cacheIndex;
	private int scaleY;
	private int[] models;
	public int varbit;
	public int wallWidth;
	private int[] modelTypes;
	public byte[] description;
	public boolean hasActions;
	public boolean castsShadow;
	public static MRUNodes mruNodes2 = new MRUNodes(30);
	public int animationID;
	private static ObjectDef[] cache;
	private int offsetZ;
	private int[] modifiedModelColors;
	public static MRUNodes mruNodes1 = new MRUNodes(500);
	public String[] actions;

}
