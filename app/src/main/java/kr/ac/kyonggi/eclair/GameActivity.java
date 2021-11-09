package kr.ac.kyonggi.eclair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        Button[][] buttons = new Button[9][9];

        for(int i = 0; i<9; i++){
            TableRow tableRow = new TableRow(this);
            for(int j=0; j<9; j++){
                buttons[i][j] = new Button(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);
            }
            table.addView(tableRow);
        }
    }
}