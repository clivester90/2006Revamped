package com.client.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.model.Model;
import com.NodeSub;
import com.client.model.Vertex;

public class SceneNode extends NodeSub {

	public void draw(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		Model model = getRotatedModel();
		if (model != null) {
			modelHeight = model.modelHeight;
			model.draw(i, j, k, l, i1, j1, k1, l1, i2);
		}
	}

	public Model getRotatedModel() {
		return null;
	}

	public SceneNode() {
		modelHeight = 1000;
	}

	public Vertex[] normals;
	public int modelHeight;
}
