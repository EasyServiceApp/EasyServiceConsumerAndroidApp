package com.service.easyservice.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.service.easyservice.util.Constants;

public class TextViewOpenSansRegular extends TextView implements Constants{

	String fontPath = F_OPEN_SANS_REGULAR;

	public TextViewOpenSansRegular(Context context) {
		super(context);
		Typeface tf1 = Typeface.createFromAsset(context.getAssets(), fontPath);
		this.setTypeface(tf1);
	}

	public TextViewOpenSansRegular(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Typeface face=Typeface.createFromAsset(context.getAssets(), fontPath);
		this.setTypeface(face);
	}

	public TextViewOpenSansRegular(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		Typeface face=Typeface.createFromAsset(context.getAssets(), fontPath); 
		this.setTypeface(face); 
	}
	protected void onDraw (Canvas canvas)
	{
		super.onDraw(canvas);
	}
}
