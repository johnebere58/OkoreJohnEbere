

package com.example.okorejohnebere.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okorejohnebere.R;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


public class CustomListView extends FrameLayout {

    Context context;
    View loadingLayout;
    View emptyLayout;
    View retryLayout;
    View reloadTv;
    TextView loading_tv;
    RecyclerView recyclerView;
    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        View rootView = View.inflate(context,R.layout.my_custom_listview,null);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        retryLayout = rootView.findViewById(R.id.retry_layout);
        loadingLayout = rootView.findViewById(R.id.loading_layout);
        emptyLayout = rootView.findViewById(R.id.empty_layout);
        reloadTv = rootView.findViewById(R.id.reload_tv);
        loading_tv = rootView.findViewById(R.id.loading_tv);

        hideEverything();
        addView(rootView);
    }

    public void showLoading(){
        hideEverything();
        loadingLayout.setVisibility(VISIBLE);
    }

    private void hideEverything(){
        loadingLayout.setVisibility(GONE);
        retryLayout.setVisibility(GONE);
        emptyLayout.setVisibility(GONE);
        reloadTv.setVisibility(GONE);
    }

    public void hideLoadingAndShowError(OnClickListener retryClicked){
        hideEverything();
        retryLayout.setVisibility(VISIBLE);
        retryLayout.setOnClickListener(retryClicked);
    }

    public void hideLoading(){
        hideEverything();
    }

    public void showEmpty(OnClickListener retryClicked){
        hideEverything();
        emptyLayout.setVisibility(VISIBLE);
        if(retryClicked!=null){
            reloadTv.setVisibility(VISIBLE);
            emptyLayout.setOnClickListener(retryClicked);
        }
    }

    public void setLoadingText(String text){
        loading_tv.setText(text);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
