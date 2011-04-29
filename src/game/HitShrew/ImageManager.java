package game.HitShrew;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class ImageManager {
	
	private static Map<Integer,Bitmap> values = new HashMap<Integer, Bitmap>();

	//回傳指定的圖來draw
	public static Bitmap getBitmap(int index){
		return values.get(index);
	}
	
	public static void init(Context context)
	{
	  //抓取所有地鼠照片, 放進Bitmap物件中
	  //依integer分類, 由圖片來決定現在地鼠的情況
		Resources resources = context.getResources();
		//UP_ONE, 地鼠出來了
		values.put(new Integer(13), loadBallView(resources,R.drawable.show1));
		values.put(new Integer(12), loadBallView(resources,R.drawable.show2));
		values.put(new Integer(11), loadBallView(resources,R.drawable.show3));
		values.put(new Integer(10), loadBallView(resources,R.drawable.show4));
		values.put(new Integer(9), loadBallView(resources,R.drawable.show5));
		values.put(new Integer(8), loadBallView(resources,R.drawable.show6));
		values.put(new Integer(7), loadBallView(resources,R.drawable.show6));
		values.put(new Integer(6), loadBallView(resources,R.drawable.show6));
		values.put(new Integer(5), loadBallView(resources,R.drawable.show5));
		values.put(new Integer(4), loadBallView(resources,R.drawable.show4));
		values.put(new Integer(3), loadBallView(resources,R.drawable.show3));
		values.put(new Integer(2), loadBallView(resources,R.drawable.show2));
		values.put(new Integer(1), loadBallView(resources,R.drawable.show1));
		
		//NOTHING, 只有洞
		values.put(new Integer(0), loadBallView(resources,R.drawable.pic_empty));

		//DOWN_HIT, 打下去
		values.put(new Integer(-9), loadBallView(resources,R.drawable.pic_hited));
		values.put(new Integer(-8), loadBallView(resources,R.drawable.pic_hited));		
		values.put(new Integer(-7), loadBallView(resources,R.drawable.pic_hited));
		values.put(new Integer(-6), loadBallView(resources,R.drawable.pic_hited));
		
		//往下降
		values.put(new Integer(-5), loadBallView(resources,R.drawable.show5));
		values.put(new Integer(-4), loadBallView(resources,R.drawable.show4));
		values.put(new Integer(-3), loadBallView(resources,R.drawable.show3));
		values.put(new Integer(-2), loadBallView(resources,R.drawable.show2));
		values.put(new Integer(-1), loadBallView(resources,R.drawable.show1));
	}
	
  //將Bitmap draw出來
	private static Bitmap loadBallView(Resources resources,int resId) 
	{
	 	Drawable image = resources.getDrawable(resId);
		Bitmap bitmap = Bitmap.createBitmap(80,80, Bitmap.Config.ARGB_8888);
        
        Canvas canvas = new Canvas(bitmap);
        image.setBounds(0, 0, 80,80);
        image.draw(canvas);
	 	
		return bitmap;
    }
}
