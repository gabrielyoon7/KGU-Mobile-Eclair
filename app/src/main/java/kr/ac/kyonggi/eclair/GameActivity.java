package kr.ac.kyonggi.eclair;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;


public class GameActivity extends AppCompatActivity {

    ToggleButton tbtn = null;
    BlockButton[][] buttons = new BlockButton[9][9];
    TextView mineNum = null;
//    int iForButton = 0;
//    int jForButton =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TableLayout table;
        table = (TableLayout) findViewById(R.id.tableLayout);
        tbtn = (ToggleButton) findViewById(R.id.toggleButton);
        mineNum = (TextView) findViewById(R.id.MineNum);


        /**
         * 지뢰 맵 생성 시작
         * */

        int mine = 0;
        int width = 9;
        int height = 9;
        int[][] map = new int[width][height];

        while (mine < 10) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (map[y][x] == -1) { //지뢰 중복 시 재시도
                continue;
            } else {
                map[y][x] = -1; //지뢰 생성

                /**
                 * 지뢰 힌트 생성 시작
                 * */
                if ((x >= 0 && x <= width - 2) && (y >= 0 && y <= height)) {
                    if (map[y][x + 1] != -1) {
                        map[y][x + 1] += 1;  //center right
                    }
                }
                if ((x >= 1 && x <= width - 1) && (y >= 0 && y <= height - 1)) {
                    if (map[y][x - 1] != -1) {
                        map[y][x - 1] += 1;  // center left
                    }
                }
                if ((x >= 1 && x <= width - 1) && (y >= 1 && y <= height - 1)) {
                    if (map[y - 1][x - 1] != -1) {
                        map[y - 1][x - 1] += 1;  // top left
                    }
                }
                if ((x >= 0 && x <= width - 2) && (y >= 1 && y <= height - 1)) {
                    if (map[y - 1][x + 1] != -1) {
                        map[y - 1][x + 1] += 1;  // top right
                    }
                }
                if ((x >= 0 && x <= width - 1) && (y >= 1 && y <= height - 1)) {
                    if (map[y - 1][x] != -1) {
                        map[y - 1][x] += 1;  // top center
                    }
                }
                if ((x >= 0 && x <= width - 2) && (y >= 0 && y <= height - 2)) {
                    if (map[y + 1][x + 1] != -1) {
                        map[y + 1][x + 1] += 1; // bottom right
                    }
                }
                if ((x >= 1 && x <= width - 1) && (y >= 0 && y <= height - 2)) {
                    if (map[y + 1][x - 1] != -1) {
                        map[y + 1][x - 1] += 1; // bottom left
                    }
                }
                if ((x >= 0 && x <= width - 1) && (y >= 0 && y <= height - 2)) {
                    if (map[y + 1][x] != -1) {
                        map[y + 1][x] += 1; // bottom center
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


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.printf(map[i][j] + " ");
            }
            System.out.println();
        }


        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);

                buttons[i][j].x=i;
                buttons[i][j].y=j;

                if (map[i][j] == -1) {
                    buttons[i][j].mine = true;
                } else {
                    buttons[i][j].neighborMines = map[i][j];
                }

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tbtn.isChecked()) {
                            ((BlockButton) view).toggleFlag();
                            System.out.println(BlockButton.flags);
                            mineNum.setText(BlockButton.flags + "");

                        } else {
                            boolean gameOver = ((BlockButton) view).breakBlock(view);
                            if (gameOver) {
                                //전부 오픈하는 메소드

                                for (int i = 0; i < buttons.length; i++) {
                                    for (int j = 0; j < buttons[i].length; j++) {
                                        if (buttons[i][j].mine) {
                                            buttons[i][j].setText("*");
                                            buttons[i][j].setEnabled(false);
                                        } else {
                                            buttons[i][j].setText(buttons[i][j].neighborMines + "");
                                            buttons[i][j].setEnabled(false);
                                        }
                                    }
                                }
                                //게임 종료

                                AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this);
                                dlg.setTitle("GameOver"); //제목
                                dlg.setMessage("게임이 종료되었습니다."); // 메시지
                                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //토스트 메시지
                                        Toast.makeText(GameActivity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dlg.show();
                            }else{// recursive
                                if(buttons[((BlockButton) view).getBX()][((BlockButton) view).getBY()].neighborMines==0){
                                    //
                                    uncoverNeighbors(((BlockButton) view).getBX(), ((BlockButton) view).getBY(),view);
                                }
                            }

                        }//else끝

                        if(BlockButton.flags == 0 && BlockButton.blocks==10){
                            AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this);
                            dlg.setTitle("You Win"); //제목
                            dlg.setMessage("지뢰를 모두 발견했습니다."); // 메시지
                            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //토스트 메시지
                                    Toast.makeText(GameActivity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dlg.show();
                        }//if문 종료

                    }
                    void uncoverNeighbors(int i, int j, View view){
                        boolean a= (i<0||i>8||j<0||j>8);
                        boolean b = buttons[i][j].isEnabled()==false;
                        boolean c =buttons[i][j].neighborMines==-1;
                        System.out.println(i+""+j);
                        System.out.println(a);
                        System.out.println(b);
                        System.out.println(c);
                        if(a||b||c) {
                            return;
                        }
                        else{
                            buttons[i][j].breakBlock(view);
                            uncoverNeighbors(i+1,j,view); //right
                            uncoverNeighbors(i,j+1,view); //bottom
                            uncoverNeighbors(i+1,j+1,view);//rightBottom
                            uncoverNeighbors(i-1,j,view); //left
                            uncoverNeighbors(i,j-1,view); //top
                            uncoverNeighbors(i-1,j-1,view); //leftTop
                            uncoverNeighbors(i+1,j-1,view); //rightTop
                            uncoverNeighbors(i-1,j+1,view); //leftBottom

                        }
                    }
                    // recursive 함수

                });
            }
            table.addView(tableRow);
        }
    }





//    public class ButtonOnClickListener implements View.OnClickListener
//    {
//
//        int i;
//        int j;
//        public ButtonOnClickListener(int i, int j) {
//            this.i = i;
//            this.j = j;
//        }
//
//        @Override
//        public void onClick(View view)
//        {
//            if (tbtn.isChecked()) {
//                ((BlockButton) view).toggleFlag();
//                System.out.println(BlockButton.flags);
//                mineNum.setText(BlockButton.flags + "");
//
//            } else {
//                boolean gameOver = ((BlockButton) view).breakBlock(view);
//                if (gameOver) {
//                    //전부 오픈하는 메소드
//
//                    for (int i = 0; i < buttons.length; i++) {
//                        for (int j = 0; j < buttons[i].length; j++) {
//                            if (buttons[i][j].mine) {
//                                buttons[i][j].setText("*");
//                                buttons[i][j].setEnabled(true);
//                            } else {
//                                buttons[i][j].setText(buttons[i][j].neighborMines + "");
//                                buttons[i][j].setEnabled(true);
//                            }
//                        }
//                    }
//                    //게임 종료
//
//                    AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this);
//                    dlg.setTitle("GameOver"); //제목
//                    dlg.setMessage("게임이 종료되었습니다."); // 메시지
//                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            //토스트 메시지
//                            Toast.makeText(GameActivity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    dlg.show();
//                }else{ // recursive
//
//                    if(int i=0; i<9; i++){
//
//                    }
//
//
//                }
//
//
//
//
//            }//else끝
//
//            if(BlockButton.flags == 0 && BlockButton.blocks==10){
//                AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this);
//                dlg.setTitle("You Win"); //제목
//                dlg.setMessage("지뢰를 모두 발견했습니다."); // 메시지
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //토스트 메시지
//                        Toast.makeText(GameActivity.this, "확인을 누르셨습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dlg.show();
//            }//if문 종료
//        }
//
//    };
}


