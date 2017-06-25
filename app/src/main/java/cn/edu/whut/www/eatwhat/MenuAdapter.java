package cn.edu.whut.www.eatwhat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 42910 on 2017/6/25.
 */

public class MenuAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private Context mContext;
    public MenuAdapter(Context context, List<String> menulist){
        super(R.layout.menu_item, menulist);
        mContext = context;
    }

    private OnItemEditTextChangedListener mListener;

    public void setListener(OnItemEditTextChangedListener listener) {
        mListener = listener;
    }
    @Override
    protected void convert(final BaseViewHolder viewHolder, String item) {
        viewHolder.setText(R.id.food_text,item)
                .addOnClickListener(R.id.btn_delete);
        EditText text = viewHolder.getView(R.id.food_text);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mListener.onEditTextAfterTextChanged(s,viewHolder.getLayoutPosition());

            }
        });
    }
    public interface OnItemEditTextChangedListener{
        void onEditTextAfterTextChanged(Editable editable,int position);
    }
}
