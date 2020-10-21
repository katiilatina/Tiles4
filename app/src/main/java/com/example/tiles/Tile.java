package com.example.tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Tile {
    String tile;
    String type;
    float x, y, width, height;
    Paint p;

    public Tile(String suit, String type, float x, float y, float width, float height) {
        this.tile = suit;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        p = new Paint();
    }

    public void draw(Canvas c) {
        p.setColor(Color.GRAY);
        c.drawRect(x,y, x+width, y+height, p);
        p.setColor(Color.WHITE);
        p.setTextSize(23);
        c.drawText(tile, x + (width/2) - 35, y + (height/2), p);
        c.drawText(type, x + (width/2) - 35, y + (height/2) + 21, p);
    }
}

