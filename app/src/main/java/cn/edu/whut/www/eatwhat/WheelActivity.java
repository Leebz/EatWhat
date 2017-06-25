package cn.edu.whut.www.eatwhat;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

public class WheelActivity extends AppCompatActivity {
    private Button btn_start;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        Bundle bundle = getIntent().getExtras();
        mType = bundle.getInt("TYPE",-1);
        if(mType==-1){
            this.finish();
            return;
        }

        final ArrayList<String> list = getData(mType);
        final LoopView loopView = (LoopView)findViewById(R.id.loopView);


        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WheelActivity.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(WheelActivity.this, list.get(index), Toast.LENGTH_SHORT).show();
            }
        });
        loopView.setItems(list);
        loopView.setInitPosition(0);
    }

    public ArrayList<String> getData(int type){
        ArrayList<String> list = new ArrayList<>();
        Set<String> nullset = new HashSet<>();
        nullset.add("null");
        if(type==0){

            SharedPreferences sp = this.getSharedPreferences("breakfast", Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("breakfast",null));
//            Toast.makeText(this, sp.getAll().keySet().size(), Toast.LENGTH_SHORT).show();

        }
        else if(type==1){

            SharedPreferences sp = this.getSharedPreferences("lunch",Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("lunch",null));
        }
        else{
            SharedPreferences sp = this.getSharedPreferences("dinner",Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("dinner",null));
        }

        return list;
    }

}
