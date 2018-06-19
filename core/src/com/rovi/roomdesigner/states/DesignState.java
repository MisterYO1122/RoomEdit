package com.rovi.roomdesigner.states;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.ListView.ItemClickListener;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.rovi.roomdesigner.Room;
import com.rovi.roomdesigner.RoomTag;
import com.rovi.roomdesigner.RoomTagListAdapter;
import com.rovi.roomdesigner.State;
import com.rovi.roomdesigner.StateManager;
import com.rovi.roomdesigner.TagListAdapter;


public class DesignState extends State implements InputProcessor{

	Texture whiteSquare;
	boolean showGrid = true;
	
	private Color gridColor = Color.DARK_GRAY;
	
	List<Room> rooms;
	
	public int gridSize = 16;
	
	Stage stage;
	VisTable root;
	
	InputMultiplexer inputMultiplexer;
	
	boolean isDrawing;
	Vector2 drawStartPosition = Vector2.Zero, drawEndPosition = Vector2.Zero;
	
	boolean movingCamera;
	
	Array<RoomTag> tagArray;
	
	public DesignState(StateManager stateManager) {
		super(stateManager);
		
		VisUI.load();
		stage = new Stage(new ScreenViewport());
		
		inputMultiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		root = new VisTable(true);
		root.setFillParent(true);
		stage.addActor(root);
		
		tagArray = new Array<RoomTag>();
		tagArray.add(new RoomTag("Default", Color.GRAY));
		tagArray.add(new RoomTag("Boss", Color.RED));
		tagArray.add(new RoomTag("Spawn", Color.GREEN));
		
		initUI();
		
		whiteSquare = new Texture("square.png");
		
		rooms = new ArrayList<>();
		
		
		camera.setPosition(0, 0);
		
		//testUIElement = new UIElement(new Texture("buttonTemplate.png"), new Vector2(0, 64), new Vector2(64, 32));
		
	}
	
	void initUI() {
		MenuBar menuBar = new MenuBar();
		root.top();
		root.add(menuBar.getTable()).fillX().expandX().row();
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu viewMenu = new Menu("View");
		
		fileMenu.addItem(new MenuItem("Save"));
		fileMenu.addItem(new MenuItem("Load"));
		fileMenu.addItem(new MenuItem("Quit"));
		
		//Edit menu
		MenuItem roomPropertiesSubMenu = new MenuItem("Room Properties");
		PopupMenu roomPropSub = new PopupMenu();
		
		MenuItem tagsSubMenu = new MenuItem("Tags");
		final VisDialog tagsDialog = new VisDialog("Edit Tags");
		tagsDialog.addCloseButton();
		
		final TagListAdapter tagsListAdapter = new TagListAdapter(tagArray);
		ListView tagsListView = new ListView<>(tagsListAdapter);
		
		final VisDialog addTagDialog = new VisDialog("New tag");
		addTagDialog.addCloseButton();
		final VisTextArea tagNameText = new VisTextArea();
		VisTextButton addTagDoneButton = new VisTextButton("Enter");
		addTagDoneButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tagArray.add(new RoomTag(tagNameText.getText(), Color.GRAY));
				tagsListAdapter.add(tagArray.get(tagArray.size-1)); //CRASHES FOR SOME REASON???
				addTagDialog.hide();
				super.clicked(event, x, y);
			}
		});
		
		addTagDialog.add(tagNameText);
		addTagDialog.row();
		addTagDialog.add(addTagDoneButton);
		
		VisTextButton addTagButton = new VisTextButton("Add new tag");
		addTagButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				addTagDialog.show(stage);
				super.clicked(event, x, y);
			}
		});
		
		tagsDialog.add(addTagButton);
		tagsDialog.row();
		
		
		tagsDialog.add(tagsListView.getMainTable()).grow();
		
		tagsSubMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tagsDialog.show(stage);
				super.clicked(event, x, y);
			}
		});
		roomPropSub.addItem(tagsSubMenu);
		
		roomPropertiesSubMenu.setSubMenu(roomPropSub);
		
		editMenu.addItem(roomPropertiesSubMenu);
		
		//View menu
		MenuItem showGridItem = new MenuItem("Toggle grid");
		showGridItem.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toggleGrid();
				super.clicked(event, x, y);
			}
		});
		viewMenu.addItem(showGridItem);
		
		menuBar.addMenu(fileMenu);
		menuBar.addMenu(editMenu);
		menuBar.addMenu(viewMenu);
		
	}
	
	private void toggleGrid() {
		showGrid = !showGrid;
	}
	
	@Override
	public void update() {
	
		camera.update();
	}
	
	@Override
	public void draw() {
		super.draw();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawGrid(16, 1);
		drawRooms(16);
		
		drawDrawingRect();
		//testUIElement.draw(batch);
		
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}
	
	
	private void drawGrid(int tileSize, int roomInterval) {
		if(!showGrid)
			return;
		
		int camX, camY;
		
		camX = (int)(camera.position.x / (tileSize * roomInterval)) - 6;
		camY = (int)(camera.position.y / (tileSize * roomInterval)) - 6;
		batch.setColor(gridColor);
		for(int x = camX - viewport.getScreenWidth() / 2 / tileSize ; x < viewport.getScreenWidth() / 2 / tileSize + camX + 8; x++) {
			for(int y = camY - viewport.getScreenHeight() / 2 / tileSize; y < viewport.getScreenHeight() / 2 / tileSize + camY + 8; y++) {
				batch.draw(whiteSquare, tileSize * roomInterval * x, tileSize * roomInterval * y, 1, tileSize * roomInterval);
				batch.draw(whiteSquare, tileSize * roomInterval * x, tileSize * roomInterval * y, tileSize * roomInterval, 1);
			}
		}
		
		batch.setColor(Color.WHITE);
	}
	
	private void drawRooms(int tileSize) {
		for(int i = 0; i < rooms.size(); i++) {
			drawRoom(i, rooms.get(i).x, rooms.get(i).y, rooms.get(i).width, rooms.get(i).height);
		}
	}
	
	private void drawRoom(int roomId, int x, int y, int width, int height) {
		batch.setColor(rooms.get(roomId).tag.getColor());
		batch.draw(whiteSquare, x * gridSize, y * gridSize, width * gridSize, height * gridSize);
		batch.setColor(Color.WHITE);
	}
	
	private void drawDrawingRect() {
		if(isDrawing) {
			int x1, y1, x2, y2;
			x1 = MathUtils.floor(Math.min(drawStartPosition.x, drawEndPosition.x) / gridSize);
			y1 = MathUtils.floor (Math.min(drawStartPosition.y, drawEndPosition.y) / gridSize);
			x2 = MathUtils.ceil(Math.max(drawStartPosition.x, drawEndPosition.x) / gridSize);
			y2 = MathUtils.ceil(Math.max(drawStartPosition.y, drawEndPosition.y) / gridSize);
			
			for(int x = x1; x < x2; x++) {
				for(int y = y1; y < y2; y++) {
					batch.draw(whiteSquare, x * gridSize, y * gridSize, gridSize, gridSize);
				}
			}
		}
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		VisUI.dispose();
		super.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		super.resize(width, height);
	}
	
	int checkMouseClickOnRoom() {
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).isOverlappingPoint(camera.getMousePosition(), gridSize))
			{
				return i;
			}
		}
		return -1;
	}
	
	private void openRoomPropertyWindow(final int roomId) {
		VisDialog roomClickDialog = new VisDialog("Room Properties");
		
		
		roomClickDialog.button(new VisTextButton("Edit Room"));
		
		
		VisTextButton deleteButton = new VisTextButton("Delete");
		deleteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rooms.remove(roomId);
				super.clicked(event, x, y);
			}
		});
		
		roomClickDialog.button(deleteButton);
		roomClickDialog.addCloseButton();
		
		roomClickDialog.row();
		
		VisTextArea roomDesc = new VisTextArea();
		
		
		roomClickDialog.add(roomDesc);
		
		roomClickDialog.row();
		final VisDialog tagDialog = new VisDialog("Tags");
		RoomTagListAdapter tagAdapter = new RoomTagListAdapter(tagArray);
		ListView<RoomTag> tagListView = new ListView<RoomTag>(tagAdapter);
		final VisTextButton tagButton = new VisTextButton(rooms.get(roomId).tag.getTag());
		
		tagListView.setItemClickListener(new ItemClickListener<RoomTag>() {
			
			@Override
			public void clicked(RoomTag tag) {
				rooms.get(roomId).tag = tag;
				tagButton.setText(tag.getTag());
			}
		});
		
		tagDialog.add(tagListView.getMainTable()).grow();
		
		tagDialog.row();
		tagDialog.addCloseButton();
		
		
		
		
		tagButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tagDialog.show(stage);
				super.clicked(event, x, y);
			}
		});
		
		
		roomClickDialog.add(tagButton).left();
		roomClickDialog.show(stage);
		
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.RIGHT) {
			final int clickedRoom = checkMouseClickOnRoom();
			if(clickedRoom != -1)
			{
				openRoomPropertyWindow(clickedRoom);
				return true;
			}
		}
		else if(button == Buttons.LEFT) {
			isDrawing = true;
			drawStartPosition = camera.getMousePosition();
			
			drawEndPosition = drawStartPosition ;
		}
		else if(button == Buttons.MIDDLE) {
			movingCamera = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(movingCamera)
		{
			movingCamera = false;
			return true;
		}
		
		if(!isDrawing)
			return true;
		
		int x1, x2, y1, y2;
		
		x1 = MathUtils.floor(Math.min(drawStartPosition.x, drawEndPosition.x) / gridSize);
		y1 = MathUtils.floor (Math.min(drawStartPosition.y, drawEndPosition.y) / gridSize);
		x2 = MathUtils.ceil(Math.max(drawStartPosition.x, drawEndPosition.x) / gridSize);
		y2 = MathUtils.ceil(Math.max(drawStartPosition.y, drawEndPosition.y) / gridSize);
		
		
		

		
		Room room = new Room(x1, y1, x2-x1, y2-y1);
		
		boolean canCreateRoom = true;
		for(int i = 0; i < rooms.size(); i++) {
			if(room.isOverlappingRoom(rooms.get(i))) {
				canCreateRoom = false;
				break;
			}
		}
		if(canCreateRoom)
			rooms.add(room);
		
		isDrawing = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(movingCamera) {
			camera.move(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
		}
		
		if(isDrawing) {
			drawEndPosition = camera.getMousePosition();
			
			
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
