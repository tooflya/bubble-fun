package com.tooflya.bubblefun.screens;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxScreen extends Screen  implements IScrollDetectorListener, IOnSceneTouchListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================

    protected static int FONT_SIZE = 28;
    protected static int PADDING = 50;
    
    protected static int MENUITEMS = 7;

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

    private Font mFont; 
    private BitmapTextureAtlas mFontTexture;     
    
    private BitmapTextureAtlas mMenuTextureAtlas;        
    private TextureRegion mMenuLeftTextureRegion;
    private TextureRegion mMenuRightTextureRegion;
    
	private BitmapTextureAtlas BackgroundTexture;
	private TextureRegion BackgroundTextureRegion;
	private Sprite background;
	
	private BitmapTextureAtlas TitleTexture;
	private TextureRegion TitleTextureRegion;
	private Sprite title;
	
	
	HUD hud = new HUD();
    
    private Sprite menuleft;
    private Sprite menuright;

    // Scrolling
    private SurfaceScrollDetector mScrollDetector;
    private ClickDetector mClickDetector;

    private float mMinX = 0;
    private float mMaxX = 0;
    private float mCurrentX = 0;
    private int iItemClicked = -1;
    
    private Rectangle scrollBar;        
    private List<TextureRegion> columns = new ArrayList<TextureRegion>();

	private final com.tooflya.bubblefun.entities.Sprite mBackground = new com.tooflya.bubblefun.entities.Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "main-bg.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> mClouds = new CloudsManager<Cloud>(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "cloud.png", 382, 0, 1, 3), this.mBackground));

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "back-btn.png", 100, 900, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MENU);
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen() {
		this.loadResources();

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);
        
        //Images for the menu
        for (int i = 0; i < MENUITEMS; i++) {				
        	BitmapTextureAtlas mMenuBitmapTextureAtlas = new BitmapTextureAtlas(256,256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    		TextureRegion mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuBitmapTextureAtlas, Game.instance, "b/menu"+i+".png", 0, 0);
        	
        Game.loadTextures(mMenuBitmapTextureAtlas);
        	columns.add(mMenuTextureRegion);
        	
        	
        }
        
        
        //Textures for menu arrows
        this.mMenuTextureAtlas = new BitmapTextureAtlas(128,128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mMenuLeftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTextureAtlas, Game.instance, "b/menu_left.png", 0, 0);
        this.mMenuRightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMenuTextureAtlas, Game.instance, "b/menu_right.png",64, 0);
        Game.loadTextures(mMenuTextureAtlas);
        
        
        this.mScrollDetector = new SurfaceScrollDetector(this);
        this.mClickDetector = new ClickDetector(this);

        this.setOnSceneTouchListener(this);
        this.setTouchAreaBindingEnabled(true);
        this.setOnSceneTouchListenerBindingEnabled(true);

        CreateMenuBoxes();
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mClouds.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.screens.set(Screen.MENU);

		return false;
	}

	@Override
	public void onClick(ClickDetector arg0, TouchEvent arg1) {
		Game.screens.set(Screen.CHOISE);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
        this.mClickDetector.onTouchEvent(pSceneTouchEvent);
        this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return false;
	}

	@Override
	public void onScroll(ScrollDetector arg0, TouchEvent arg1, float pDistanceX, float pDistanceY) {

		//Disable the menu arrows left and right (15px padding)
    	if(Game.camera.getMinX()<=15)
         	menuleft.setVisible(false);
         else
         	menuleft.setVisible(true);
    	 
    	 if(Game.camera.getMinX()>mMaxX-15)
             menuright.setVisible(false);
         else
        	 menuright.setVisible(true);
         	
        //Return if ends are reached
        if ( ((mCurrentX - pDistanceX) < mMinX)  ){                	
            return;
        }else if((mCurrentX - pDistanceX) > mMaxX){
        	
        	return;
        }
        
        //Center camera to the current point
       Game.camera.offsetCenter(-pDistanceX,0 );
        mCurrentX -= pDistanceX;
        
       
        //Set the scrollbar with the camera
        float tempX =Game.camera.getCenterX()-Options.cameraWidth/2;
        // add the % part to the position
        tempX+= (tempX/(mMaxX+Options.cameraWidth))*Options.cameraWidth;      
        //set the position
        scrollBar.setPosition(tempX, scrollBar.getY());
        
        //set the arrows for left and right
        menuright.setPosition(Game.camera.getCenterX()+Options.cameraWidth/2-menuright.getWidth(),menuright.getY());
        menuleft.setPosition(Game.camera.getCenterX()-Options.cameraWidth/2,menuleft.getY());
        
      
        
        //Because Camera can have negativ X values, so set to 0
    	if(Game.camera.getMinX()<0){
    		Game.camera.offsetCenter(0,0 );
    		mCurrentX=0;
    	}
	}

	// ===========================================================
	// Methods
	// ===========================================================
    
    private void CreateMenuBoxes() {
    	
         int spriteX = PADDING;
    	 int spriteY = PADDING;
    	 
    	 //current item counter
         int iItem = 1;

    	 for (int x = 0; x < columns.size(); x++) {
    		 
    		 //On Touch, save the clicked item in case it's a click and not a scroll.
             final int itemToLoad = iItem;
    		 
    		 Sprite sprite = new Sprite(spriteX,spriteY + Options.cameraWidth / 4 - 15,columns.get(x)){
    			 
    			 public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                     iItemClicked = itemToLoad;
                     return false;
    			 }        			 
    		 };        		 
    		 iItem++;
    		
    		 
    		 this.attachChild(sprite);        		 
    		 this.registerTouchArea(sprite);        		 

    		 spriteX += 20 + PADDING+sprite.getWidth();
		}
    	
    	 mMaxX = spriteX - Options.cameraWidth;
    	 
    	 //set the size of the scrollbar
    	 float scrollbarsize = Options.cameraWidth/((mMaxX+Options.cameraWidth)/Options.cameraWidth);
    	 scrollBar = new Rectangle(0,Options.cameraWidth-20,scrollbarsize, 20);
    	 scrollBar.setColor(1,0,0);
    	 this.attachChild(scrollBar);
    	 
    	 menuleft = new Sprite(0,Options.cameraWidth/2-mMenuLeftTextureRegion.getHeight()/2,mMenuLeftTextureRegion);
    	 menuright = new Sprite(Options.cameraWidth-mMenuRightTextureRegion.getWidth(),Options.cameraWidth/2-mMenuRightTextureRegion.getHeight()/2,mMenuRightTextureRegion);
    	 this.attachChild(menuright);
    	 menuleft.setVisible(false);
    	 this.attachChild(menuleft);
    }

}