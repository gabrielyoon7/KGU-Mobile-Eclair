package kr.ac.kyonggi.eclair;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;


public class GameActivity extends AppCompatActivity {

    ToggleButton tbtn=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TableLayout table;
        table =(TableLayout) findViewById(R.id.tableLayout);
         tbtn = (ToggleButton) findViewById(R.id.toggleButton);
        BlockButton[][] buttons = new BlockButton[9][9];


        /**
         * 지뢰 맵 생성 시작
         * */

        int mine = 0;
        int width = 9;
        int height = 9;
        int [][] map = new int[width][height];

        while(mine<10){
            int x = (int) (Math.random()*width);
            int y = (int) (Math.random()*height);
            if(map[y][x]==-1){ //지뢰 중복 시 재시도
                continue;
            }
            else {
                map[y][x]=-1; //지뢰 생성

                /**
                 * 지뢰 힌트 생성 시작
                 * */
                if((x >= 0 && x <= width-2) && (y >= 0 && y <= height)) {
                    if( map[y][x+1] != -1){
                        map[y][x+1] += 1;  //center right
                    }
                }
                if ((x >= 1 && x <= width-1 ) && (y >= 0 && y <= height-1 )){
                    if (map[y][x - 1] != -1){
                        map[y][x - 1] += 1;  // center left
                    }
                }
                if ((x >= 1 && x <= width - 1) && (y >= 1 && y <= height-1 )){
                    if (map[y - 1][x - 1] != -1){
                        map[y - 1][x - 1] += 1;  // top left
                    }
                }
                if ((x >= 0 && x <= width - 2) && (y >= 1 && y <= height-1 )){
                    if (map[y - 1][x + 1] != -1){
                        map[y - 1][x + 1] += 1 ;  // top right
                    }
                }
                if ((x >= 0 && x <= width - 1) && (y >= 1 && y <= height - 1)){
                    if (map[y - 1][x] != -1){
                        map[y - 1][x] += 1;  // top center
                    }
                }
                if ((x >= 0 && x <= width - 2) && (y >= 0 && y <= height - 2)){
                    if (map[y + 1][x + 1] != -1){
                        map[y + 1][x + 1] += 1 ; // bottom right
                    }
                }
                if ((x >= 1 && x <= width - 1) && (y >= 0 && y <= height - 2)){
                    if (map[y + 1][x - 1] != -1){
                        map[y + 1][x - 1] += 1 ; // bottom left
                    }
                }
                if ((x >= 0 && x <= width - 1) && (y >= 0 && y <= height - 2)){
                    if (map[y + 1][x] != -1){
                        map[y + 1][x] += 1 ; // bottom center
                    }
                }
                /**
                 * 지뢰 힌트 생성 끝
                 * */
            }
            mine++;
        }

        /**
         * 지뢰 맵 생성 끝
         * */

        for(int i = 0 ; i < width; i++){
            for(int j = 0 ; j < height; j++){
                System.out.printf(map[i][j]+" ");
            }
            System.out.println();
        }


        for(int i = 0; i<9; i++){
            TableRow tableRow = new TableRow(this);
            for(int j=0; j<9; j++){

                buttons[i][j] = new BlockButton(this, i, j);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        if(tbtn.isChecked()){
                            ((BlockButton)view).toggleFlag();
                            System.out.println("토글 플래그");
                        }else{
                            ((BlockButton)view).breakBlock(view);
                            System.out.println("브레이크 블록");
                        }

                    }
                });

            }
            table.addView(tableRow);
        }


    }
}

