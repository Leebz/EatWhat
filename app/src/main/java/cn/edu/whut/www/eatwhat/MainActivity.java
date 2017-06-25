package cn.edu.whut.www.eatwhat;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.Inflater;

import com.github.clans.fab.*;
import com.github.clans.fab.FloatingActionButton;



public class MainActivity extends AppCompatActivity {
    private Button btn_breakfast;
    private Button btn_lunch;
    private Button btn_dinner;
    private FloatingActionButton btn_modify_breakfast;
    private FloatingActionButton btn_modify_lunch;
    private FloatingActionButton getBtn_modify_dinner;
    final int TYPE_BREAKFAST = 0;
    final int TYPE_LUNCH = 1;
    final int TYPE_DINNER = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_breakfast = (Button)findViewById(R.id.btn_breakfast);
        btn_lunch = (Button)findViewById(R.id.btn_lunch);
        btn_dinner = (Button)findViewById(R.id.btn_dinner);
        btn_modify_breakfast = (FloatingActionButton)findViewById(R.id.btn_modify_bre);
        btn_modify_lunch = (FloatingActionButton)findViewById(R.id.btn_modify_lun);
        getBtn_modify_dinner = (FloatingActionButton)findViewById(R.id.btn_modify_din);


        btn_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(TYPE_BREAKFAST);
            }
        });
        btn_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(TYPE_LUNCH);

            }
        });
        btn_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(TYPE_DINNER);
            }
        });

        btn_modify_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity(TYPE_BREAKFAST);
            }
        });
        btn_modify_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity(TYPE_LUNCH);

            }
        });
        getBtn_modify_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity(TYPE_DINNER);
            }
        });
        init();

    }
    public boolean checkList(int type){
        ArrayList<String> list = new ArrayList<>();
        if(type==0){

            SharedPreferences sp = this.getSharedPreferences("breakfast", Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("breakfast",null));

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

        if(list.size()==0){
            return false;
        }
        return true;
    }
    public void jumpActivity(int type){
        final int fTYPE = type;

        if(!checkList(type)){
            Snackbar.make(findViewById(R.id.menu),"数据为空，请添加数据",Snackbar.LENGTH_LONG)
                    .setAction("去添加", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startEditActivity(fTYPE);
                        }
                    })
                    .show();
        }
        else{
            startFunctionActivity(fTYPE);
        }

    }
    public void startEditActivity(int type){
        Intent intent = new Intent(MainActivity.this,MenuManager.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",type);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void startFunctionActivity(int type){
        Intent intent = new Intent(MainActivity.this,WheelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",type);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void init(){
        SharedPreferences sp = this.getSharedPreferences("use_record",MODE_PRIVATE);
        boolean flag = sp.getBoolean("used",false);
        //读取标记，判断是否填充示例数据
        if(flag==false){
            //如果没有用过就填充数据
            prepareData();
            //修改标记
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("used",true);
            editor.apply();
        }


    }
    public void prepareData(){
        ArrayList<String> list = new ArrayList<>();
        {
            SharedPreferences sp = this.getSharedPreferences("breakfast", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("breakfast",new HashSet<String>(initData(0)));
            editor.commit();
        }
        {
            SharedPreferences sp = this.getSharedPreferences("lunch",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("lunch",new HashSet<String>(initData(1)));
            editor.commit();
        }
        {
            SharedPreferences sp = this.getSharedPreferences("dinner",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("dinner",new HashSet<String>(initData(2)));
            editor.commit();
        }
    }


    public ArrayList<String> initData(int type){
        ArrayList<String> list = new ArrayList<>();
        if(type==0){
            list.add("包子");
            list.add("热干面");
            list.add("豆皮");
            list.add("灌汤包");
            list.add("胡辣汤");
            list.add("小笼包");
            list.add("粥");
            list.add("饼子");

        }
        else if(type==1){
            list.add("煲仔饭");
            list.add("盖浇饭");
            list.add("黑椒厨房");
            list.add("食惠厨房");
            list.add("烤肉饭");

        }
        else if(type==2){
            list.add("食惠厨房");
            list.add("八块钱三个菜");
            list.add("饼子粥");
            list.add("炒面");

        }

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
