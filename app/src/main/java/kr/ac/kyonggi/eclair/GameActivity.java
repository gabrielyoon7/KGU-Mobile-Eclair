package kr.ac.kyonggi.eclair;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TableLayout table;
        table =(TableLayout) findViewById(R.id.tableLayout);

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
                        ((BlockButton)view).breakBlock(view);
                    }
                });

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        ((BlockButton)view).toggleFlag();
                    }
                });
            }
            table.addView(tableRow);
        }


    }
}

