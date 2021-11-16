package kr.ac.kyonggi.eclair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class BlockButton extends Button{
    int x;
    int y;
    int neighborMines=-1;
    boolean mine=false;
    boolean flag = false;
    static int flags=10;
    static int blocks=81;

    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x=x;
        this.y=y;
    }

    public void toggleFlag(){
        if(flag){
            flag=false;
            flags++;
            setText("");
        }else{
            if(flags>0){
                flag=true;
                flags--;
                setText("+");
            }
        }
    }

    public boolean breakBlock(View view){
        if(mine){
            //display mine
            setText("*");
            setEnabled(false);
            return true;
        }else{
            //number of mines around
            blocks--;
            setText(neighborMines+"");
            setEnabled(false);
            return false;
        }
    }
}
