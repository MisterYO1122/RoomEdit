package com.rovi.roomdesigner;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter;

public class TagListAdapter extends ArrayAdapter<RoomTag, VisTable>{

	ColorPicker colorPicker;
	
	public TagListAdapter(Array<RoomTag> tagArray) {
		super(tagArray);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VisTable createView(final RoomTag tag) {
		
		VisLabel label = new VisLabel(tag.getTag());
		final Image colorImage = new Image(new Texture("tagColorImg.png"));
		colorImage.setColor(tag.getColor());
		
		final VisTable table = new VisTable();
		
		colorImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("changed color on item " + tag.getTag());
				
				colorPicker = new ColorPicker(new ColorPickerAdapter() { //TODO: SHOULD DISPOSE OF THIS
					@Override
					public void finished(Color newColor) {
						tag.setColor(newColor);
						colorImage.setColor(newColor);
					}
				});
				
				table.getStage().addActor(colorPicker.fadeIn());
			}
		});
		
		
		
		table.add(label).expandX().left().minWidth(150);
		table.add(colorImage);
		table.padBottom(5);
		
		return table;
	}
	
	@Override
	protected void updateView(VisTable view, RoomTag item) {
		colorPicker.dispose();
	}

}
