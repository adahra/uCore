package io.anuke.ucore.spritesort;

import com.badlogic.gdx.utils.Pool.Poolable;

public class DrawPointer implements Comparable<DrawPointer>, Poolable{
	public Drawable drawable;
	public float layer;
	public DrawLayer sorter = DrawLayer.object;
	
	public DrawPointer(){
		
	}
	
	public DrawPointer(Drawable draw, float layer){
		this.drawable = draw;
		this.layer = layer;
	}
	
	public DrawPointer(Drawable draw, DrawLayer dl, float layer){
		this.drawable = draw;
		this.layer = layer;
		this.sorter = dl;
	}
	
	public void draw(){
		drawable.draw(this);
	}
	
	public DrawPointer add(){
		DrawHandler.instance().add(this);
		return this;
	}

	@Override
	public int compareTo(DrawPointer o){
		return sorter.compare(layer, o.sorter, o.layer);
	}

	@Override
	public void reset(){
		sorter = DrawLayer.object;
	}
}