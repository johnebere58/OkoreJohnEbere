

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

import androidx.appcompat.widget.AppCompatTextView;

import com.example.okorejohnebere.R;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


public class ReadMoreTextView extends AppCompatTextView {
    int minLength = 100;

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    public void setReadMoreText(final String text,boolean clickable){
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if(text.length()>minLength) {
            String minText = text.substring(0, minLength);
            ssb.append(minText);
            ssb.append("...");
            ssb.append(readLessSpan(text,minText,null));
            setText(ssb);
        }else{
            setText(text);
        }

        if(clickable)setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void setReadMoreText(final String text,boolean full,boolean clickable,ToggleReadMore onToggle){
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if(text.length()>minLength) {
        String minText = text.substring(0, minLength);
            if(!full) {
                ssb.append(minText);
                ssb.append("...");
                ssb.append(readLessSpan(text, minText,onToggle));
                setText(ssb);
            }else{
                SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                ssb1.append(text);
                ssb1.append("  ");
                ssb1.append(readMoreSpan(text,minText,onToggle));
                setText(ssb1);
            }
        }else{
            setText(text);
        }

        if(clickable)setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString spanStringForegroundandBold(CharSequence s, int color) {
        SpannableString dname = new SpannableString(s);
        dname.setSpan(new ForegroundColorSpan(color), 0, s.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        dname = spanStringTypeface(dname, Typeface.BOLD);
        return dname;
    }

    private SpannableString spanStringClickableBoldandForeground(CharSequence s,int color, final View.OnClickListener onClickListener) {
        SpannableString dname = new SpannableString(s);
        dname.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, dname.length(), 33);
        dname = spanStringTypeface(dname, Typeface.BOLD);
        dname.setSpan(new ForegroundColorSpan(color), 0, s.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return dname;
    }

    private SpannableString spanStringTypeface(CharSequence s, int typeface) {
        SpannableString dname = new SpannableString(s);
        dname.setSpan(new StyleSpan(typeface), 0, s.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return dname;
    }

    private SpannableString readLessSpan(final String text, final String lessText, final ToggleReadMore onToggle){
        return spanStringClickableBoldandForeground("Read More", getResources().getColor(R.color.blue0),
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                        ssb1.append(text);
                        ssb1.append("  ");
                        ssb1.append(readMoreSpan(text,lessText,onToggle));
                        setText(ssb1);
                        if(onToggle!=null)onToggle.onToggled(true);
                    }
                });
    }

    private SpannableString readMoreSpan(final String text, final String lessText, final ToggleReadMore onToggle){
        return spanStringClickableBoldandForeground("Read Less", getResources().getColor(R.color.blue0),
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                        ssb1.append(lessText);
                        ssb1.append("...");
                        ssb1.append(readLessSpan(text,lessText,onToggle));
                        setText(ssb1);
                        if(onToggle!=null)onToggle.onToggled(false);
                    }
                });
    }

    private interface ToggleReadMore {

        void onToggled(boolean fullMode);

    }

}
