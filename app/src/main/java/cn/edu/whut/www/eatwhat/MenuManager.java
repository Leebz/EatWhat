package cn.edu.whut.www.eatwhat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MenuManager extends AppCompatActivity implements MenuAdapter.OnItemEditTextChangedListener{
    private RecyclerView mRecyclerView;
    private MenuAdapter mMenuAdapter;
    private FloatingActionButton mAddBtn;
    private int mType;
    private ArrayList<String> mList;
    @Override
    protected void onStop() {
        super.onStop();
        saveData(mType,refreshList());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manager);

        Bundle bundle = getIntent().getExtras();
        mType = bundle.getInt("TYPE",-1);

        setTitle("Menu Manager");
        mRecyclerView = (RecyclerView)findViewById(R.id.menu_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MenuManager.this));

        mMenuAdapter = new MenuAdapter(MenuManager.this,new ArrayList<String>());
        mMenuAdapter.setListener(MenuManager.this);
        mMenuAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mMenuAdapter);

        mAddBtn = (FloatingActionButton)findViewById(R.id.btn_menu_submit);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        mMenuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mList.remove(position);
                mMenuAdapter.setNewData(mList);
                mMenuAdapter.notifyDataSetChanged();
            }
        });
        mList = getData(mType);
        mMenuAdapter.addData(mList);


    }

    public ArrayList<String> getData(int type){
        ArrayList<String> list = new ArrayList<>();
        Set<String> nullset = new HashSet<>();
        nullset.add("null");
        if(type==0){

            SharedPreferences sp = this.getSharedPreferences("breakfast", Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("breakfast",nullset));

        }
        else if(type==1){

            SharedPreferences sp = this.getSharedPreferences("lunch",Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("lunch",nullset));
        }
        else{
            SharedPreferences sp = this.getSharedPreferences("dinner",Context.MODE_PRIVATE);
            list.clear();
            list.addAll(sp.getStringSet("dinner",nullset));
        }

        return list;
    }
    public void addData(){
        mList.add("");
        mMenuAdapter.setNewData(mList);
        mMenuAdapter.notifyDataSetChanged();
    }

    public ArrayList<String> refreshList(){

        ArrayList<String> list = new ArrayList<>();
        int length = mList.size();
        Log.i("TESTDATA",length+"");
        for(int i=0;i<length;i++){
            String data = mList.get(i).trim();
            if(!data.equals("")){
                list.add(data);
                Log.i("TESTDATA",data);
            }
        }

        return list;
    }
    public void saveData(int type,ArrayList<String> list){

        if(type==0)
        {
            SharedPreferences sp = this.getSharedPreferences("breakfast", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("breakfast",new HashSet<String>(list));
            editor.commit();
        }
        else if(type==1)
        {
            SharedPreferences sp = this.getSharedPreferences("lunch",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("lunch",new HashSet<String>(list));
            editor.commit();
        }
        else if(type==2)
        {
            SharedPreferences sp = this.getSharedPreferences("dinner",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("dinner",new HashSet<String>(list));
            editor.commit();
        }
    }


    @Override
    public void onEditTextAfterTextChanged(Editable editable, int position) {
        mList.set(position,editable.toString());
    }
}
