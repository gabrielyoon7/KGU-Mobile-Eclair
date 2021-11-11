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

