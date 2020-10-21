package com.example.tiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class TilesView extends View {

    float width, height;
    ArrayList<Tile> tiles = new ArrayList<>();
    HashMap<String, String[]> hashMap;
    Random random = new Random();
    int number;
    int added;
    int countTilesRow = 5;
    int countTilesCol = 3;
    int countTiles = 14; //количество тайлов на поле
    Paint p = new Paint();
    boolean check = true;
    ArrayList<Tile> used;

    public TilesView(Context context) {
        super(context);
    }

    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        number = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("mytag", "size = " + tiles.size());
        fillTestData();
        for (Tile t : tiles) {
            t.draw(canvas);
        }
        //выигрышная комбинацию
        if(check_victory()){
            Toast.makeText(getContext(), "Выигрышная комбинация", Toast.LENGTH_LONG).show();
        }
    }

    private boolean check_victory() {
        int amount_3tiles_comb = 0;
        int amount_pair_comb = 0;
        String[] types = {"масти", "ветер", "драконы"};
        String s = "";
        for (int i = 0; i < tiles.size(); i++) {
            s += tiles.get(i).tile + " " + tiles.get(i).type + ", ";
        }
        ArrayList<Tile> temp = new ArrayList<>();
        used = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            temp.clear();
            for (int j = 0; j < tiles.size(); j++) {
                if(tiles.get(j).tile.equals(types[i])){
                    temp.add(tiles.get(j));
                }
            }
            int amount_3tiles_comb_temp = 0;
            int amount_pair_comb_temp = 0;
            int count_tiles = 0;
            if(types[i].equals("масти")) {
                //последовательно 3 карты масти
                for (int j = 0; j < temp.size(); j++) {
                    String curr_type = temp.get(j).type.split(" ")[0];
                    int curr_number = Integer.parseInt(temp.get(j).type.split(" ")[1]);
                    int second = -1;
                    int third = -1;
                    for (int k = 0; k < temp.size(); k++) {
                        String curr_type2 = temp.get(k).type.split(" ")[0];
                        int curr_number2 = Integer.parseInt(temp.get(k).type.split(" ")[1]);
                        if (curr_type.equals(curr_type2) && curr_number + 1 == curr_number2) {
                            second = curr_number2;
                        }
                        if (curr_type.equals(curr_type2) && curr_number + 2 == curr_number2) {
                            third = curr_number2;
                        }
                    }
                    if (second != -1 && third != -1) {
                        amount_3tiles_comb_temp += 1;
                    } else if (second != -1 && third == -1) {
                        amount_pair_comb_temp += 1;
                    }
                }
                if(amount_3tiles_comb_temp > 0) {
                    amount_pair_comb_temp -= amount_3tiles_comb_temp;
                }
                amount_pair_comb += amount_pair_comb_temp;
                amount_3tiles_comb += amount_3tiles_comb_temp;
            }

            amount_3tiles_comb_temp = 0;
            amount_pair_comb_temp = 0;
            //одинаковые
            for (int j = 0; j < temp.size(); j++) {
                String curr_tile = temp.get(j).tile;
                String curr_type = temp.get(j).type;
                count_tiles = 0;
                ArrayList<Tile> local_tiles = new ArrayList<>();
                for (int k = 0; k < temp.size(); k++) {
                    if(k != j) {
                        String curr_tile2 = temp.get(k).tile;
                        String curr_type2 = temp.get(k).type;
                        if (curr_tile.equals(curr_tile2) && curr_type.equals(curr_type2)) {
                            local_tiles.add(temp.get(k));
                            count_tiles += 1;
                        }
                    }
                }
                used.addAll(local_tiles);
                if (count_tiles == 2 || count_tiles == 3) {
                    amount_3tiles_comb_temp += 1;
                } else if (count_tiles == 1) {
                    amount_pair_comb_temp += 1;
                }
            }
            if(amount_3tiles_comb_temp > 0)
                amount_3tiles_comb += amount_3tiles_comb_temp / 3;
            if(amount_pair_comb_temp > 0){
                amount_pair_comb += amount_pair_comb_temp / 2;
            }
        }

        Log.d("mytag", "amount_3tiles_comb = " + amount_3tiles_comb + ", amount_pair_comb = " + amount_pair_comb);
        if(amount_3tiles_comb == 4 && amount_pair_comb == 1){
            return true;
        }
        return false;
    }

    private boolean check_tiles_fields(Tile tile) {
        for(Tile t: used){
            if (t.tile.equals(tile.tile) == t.type.equals(tile.type)){
                return true;
            }
        }
        return false;
    }

    public void fillTestData(){
//        String[] data = {"масти,пины 2", "масти,пины 3", "масти,пины 4", "масти,маны 6", "масти,маны 7", "масти,маны 8", "масти,пины 7", "масти,пины 8", "масти,пины 9", "масти,соу 1", "масти,соу 2", "масти,соу 3", "масти,соу 8", "масти,соу 9"};
//        String[] data = {"масти,пины 2", "масти,пины 3", "масти,пины 4", "драконы,красный", "драконы,красный", "драконы,красный", "ветер,юг", "ветер,юг", "ветер,юг", "масти,соу 1", "масти,соу 2", "масти,соу 3", "масти,соу 8", "масти,соу 9"};
        String[] data = {"масти,пины 2", "масти,пины 3", "масти,пины 4", "драконы,красный", "драконы,красный", "драконы,красный", "ветер,юг", "ветер,юг", "ветер,юг", "масти,соу 1", "масти,соу 2", "масти,соу 3", "ветер,север", "ветер,север"};
        ArrayList<Tile> tiles2 = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            String[] line = data[i].split(",");
            tiles2.add(new Tile(line[0], line[1], tiles.get(i).x, tiles.get(i).y, tiles.get(i).width, tiles.get(i).height));
        }
        tiles.clear();
        tiles = tiles2;
    }

    public void initTiles(){
        String[] types = {"масти", "ветер", "драконы"};
        String[] suits = {"маны", "пины", "соу"};
        String[] winds = {"восток", "юг", "запад", "север"};
        String[] dragons = {"красный", "белый", "зелёный"};
        hashMap = new HashMap<>();
        hashMap.put("масти", suits);
        hashMap.put("ветер", winds);
        hashMap.put("драконы", dragons);
        added = 0;
        while(added != countTiles){
            //получаем тип тайла
            number = random.nextInt(types.length);
            String type = types[number];
            String[] values = hashMap.get(type);
            number = random.nextInt(values.length);
            String nameTile = values[number];
            if(type.equals("масти")) {
                int rand_number = random.nextInt(9) + 1;
                nameTile += " " + rand_number;
            }
            float[] coords = getCoords();
            Tile tile = null;
            if(coords.length == 4) {
                tile = new Tile(type, nameTile, coords[0], coords[1], coords[2], coords[3]);
            }
            if(tile != null){
                if(check_tiles(tile)) //ограничение на 4 карты
                    tiles.add(tile);
                else
                    continue;
            } else
                Log.d("mytag", "тайл пустой");
            added += 1;
        }
    }

    public boolean check_tiles(Tile tile){
        int count = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).tile.equals(tile.tile) && tiles.get(i).type.equals(tile.type)){
                count += 1;
            }
        }
        if(count > 4){
            return false;
        }
        return true;
    }

    public float[] getCoords(){
        int current = added;
        float[] coords;
        float tile_width = width / countTilesCol;
        float tile_height = height / countTilesRow;
        int padding = 10;
        float width_new = tile_width - (padding * 2);
        float height_new  = tile_height - (padding * 2);
        int i = (current / countTilesCol) + 1;
        int j = (current % countTilesCol) + 1;
        coords = new float[]{padding + (tile_width * j - tile_width), tile_height * i - tile_height + padding, width_new, height_new};
        return coords;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        if(check){
            initTiles();
            check = false;
        }
    }
}
