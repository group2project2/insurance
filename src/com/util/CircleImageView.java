package com.util;

/*import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {
	 private Paint mPaint; //画笔
	 private int mRadius; //圆形图片的半径
	 private float mScale; //图片的缩放比例
	 
	 public CircleImageView(Context context) {
	        super(context);
	    }
	 public CircleImageView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	 public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	 
	 @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size, size);
	}
	 
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint();
        Bitmap bitmap = drawableToBitmap(getDrawable());
      //初始化BitmapShader，传入bitmap对象
        BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
      //画圆形，指定好中心点坐标、半径、画笔
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
	}
	
	 //写一个drawble转BitMap的方法
    private Bitmap drawableToBitmap(Drawable drawable) {
    	if (drawable instanceof BitmapDrawable) {
    		BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
    	}
    	int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义的圆形ImageView，可以直接当组件在布局中使用。
 * @author caizhiming
 *
 */
public class CircleImageView extends ImageView{

    private Paint paint ;
    
    public CircleImageView(Context context) {  
        this(context,null);  
    }  
  
    public CircleImageView(Context context, AttributeSet attrs) {  
        this(context, attrs,0);  
    }  
  
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle); 
        paint = new Paint();
        
    }  
  
    /**
     * 绘制圆形图片
     * @author caizhiming
     */
    @Override  
    protected void onDraw(Canvas canvas) {  
  
        Drawable drawable = getDrawable();  
        if (null != drawable) {  
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();  
            Bitmap b = getCircleBitmap(bitmap, 14);  
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());  
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();  
            canvas.drawBitmap(b, rectSrc, rectDest, paint);  
  
        } else {  
            super.onDraw(canvas);  
        }  
    }  
  
    /**
     * 获取圆形图片方法
     * @param bitmap
     * @param pixels
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {  
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  
                bitmap.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
          
        final int color = 0xff424242;
       
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        int x = bitmap.getWidth(); 
        
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
        return output;  
        
        
    }  
} 
