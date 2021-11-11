package kr.ac.kyonggi.eclair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class BlockButton extends Button{
    int x;
    int y;
    int neighborMines=0;
    boolean mine=false;
    boolean flag = false;
    static int flags;
    static int blocks;

    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x=x;
        this.y=y;
    }

    public void toggleFlag(){
        if(flag){
            flag=false;
            flags--;
            setText("");
        }else{
            flag=true;
            flags++;
            setText("+");
        }
    }

    public boolean breakBlock(View view){
        if(mine){
            //display mine

            return true;
        }else{
            //number of mines around
            blocks--;
            return false;
        }

    }
}
