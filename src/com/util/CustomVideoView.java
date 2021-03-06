package com.util;

import android.content.Context;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

	public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomVideoView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	
	@Override
	public void setOnPreparedListener(OnPreparedListener l) {
		super.setOnPreparedListener(l);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
