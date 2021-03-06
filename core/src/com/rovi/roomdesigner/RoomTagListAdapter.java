package com.rovi.roomdesigner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

public class RoomTagListAdapter extends ArrayAdapter<RoomTag, VisTable>{

	
	public RoomTagListAdapter(Array<RoomTag> tagArray) {
		super(tagArray);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VisTable createView(final RoomTag tag) {
		
		VisLabel label = new VisLabel(tag.getTag());
		final Image colorImage = new Image(new Texture("tagColorImg.png"));
		colorImage.setColor(tag.getColor());
		
		final VisTable table = new VisTable();
		
		table.add(label).expandX().left().minWidth(150);
		table.add(colorImage);
		table.padBottom(5);
		
		return table;
	}
	
}
