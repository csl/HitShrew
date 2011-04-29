package game.HitShrew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class HitShrewView extends View{
	
  //預設值
  int COLUMN_COUNT = 3;
  int ROW_COUNT = 3;
  int HOLE_COUNT = COLUMN_COUNT * ROW_COUNT;     //大小: 3x3
  
  int internel = 2000;
  String KEY = "key";
  int TILE_SIZE = 80;  
  
	private static Random random = new Random(); //亂數為了那隻要出來
	
  int startX = 35;
  int startY = 60;
  
  public int gameLength = 30;    //遊戲時間

  public int score = 0;         //遊戲分數
  
  public int progress = 800;    //計算加快的時間
  public int aimProgress = 0;

  static HitShrewView self;

  private MediaPlayer mMediaPlayer;       //音效
  private Timer timer = new Timer(true);  //計算時間
	
	public HitShrewView(Context context) 
	{
		super(context);
		self = this;
		
		postDelayed(flush80ms, 80);   //讓地鼠跑出來
		this.postDelayed(flush1000ms, 1000);   //更新狀況和亂收決定誰要地鼠跑出來
				
    timer.schedule(new timerTask(), 1000, 1000);   //開始計算時間

	}
	
	public class timerTask extends TimerTask   //計算器
  {
    public void run()
    {
      gameLength--;
    }
  };
	
	public void PlayMedia()    //播放音效
	{
    try
    {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setDataSource( "/sdcard/sound.wav" );
      mMediaPlayer.prepare();
      mMediaPlayer.start();        //播放音效

    } catch (IllegalArgumentException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalStateException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }     
	}
	
	//記錄地鼠, 共3x3隻, 每一隻一個Ojbect Pic來記錄
	private List<Pic> holeQuite = new ArrayList<Pic>(HOLE_COUNT);
	{
		for(int i=0; i<HOLE_COUNT; i++){
			holeQuite.add(new Pic());
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas)     //開始draw地鼠 
	{
		super.onDraw(canvas);
		
		if(gameLength <= 0 )   //時間到，遊戲結束
		{
			getHandler().removeCallbacks(flush1000ms);
			getHandler().removeCallbacks(flush80ms);
			timer.cancel();
			timer.purge();			
			doGameOver();
			return ;
		}
		
		//畫底層的color
		canvas.drawColor(Color.WHITE);		
		drawInfoPanel(canvas);
		
		for(int i=0; i<holeQuite.size(); i++)         //一個洞一個洞處理
		{
	    //將圖片從holeQuite抓取取
			Pic pic = holeQuite.get(i);
			
			//處理3x3格大小, 看現在是那一格
			int y = i / COLUMN_COUNT;
			int x = i % COLUMN_COUNT;
			
			//抓取現在地鼠(Pic)現在的status然後晝出來
      Bitmap  bm = ImageManager.getBitmap(pic.currentType);
  		//draw
			canvas.drawBitmap(bm, startX + x*TILE_SIZE, 
						                startY + y*TILE_SIZE, 
						PaintSuite.paintForQuite);
			
			  //播下一張圖, 讓地鼠有在動的樣子
			  pic.toNext();
		}
	}
	
	private void doGameOver()         //game結束了
	{
	  //顯示「Finish」對話框
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle("Game Over");
		builder.setMessage("Finish! You get " + score*10 + " points!");
		builder.setCancelable(false);
		builder.setNeutralButton("CLick", new android.content.DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) 
			{
			  //HitShrew kk = (HitShrew)getContext();
				//kk.resetGame();
			  
			  mMediaPlayer.release();
			  HitShrew.self.finish();   //結束程式
			}
		}).show();
	}

	//draw 左上的資訊, 時間和得分
	private void drawInfoPanel(Canvas canvas)
	{
    // 顯示時間
		canvas.drawText("Time: " + gameLength, 29, 20, PaintSuite.KV4text);
	  // 顯示「打中」的分數
		canvas.drawText("Scores: " + score *10, 29, 50, PaintSuite.KV4text);
	}


	Runnable flush80ms = new Runnable()
	{
		public void run(){
		  //重新draw, 讓地鼠能動作
		  invalidate();
		  postDelayed(this, 100);
		}
	};

	Runnable flush1000ms = new Runnable(){
		public void run()
		{
			
			LinkedList<Pic> temp = new LinkedList<Pic>();
			for(Pic each : holeQuite)
			{
			  //holeQuite中找出狀態是Pic.NOTHING
			  //然後我們要讓地鼠跑出來
				if(each.currentType == Pic.NOTHING)
				{
					//加入它們
					temp.add(each);
				}
			}
			
			//看大小
			int size = temp.size();
			
			if(size == 1)
			{
				//只有一隻, 把它變成UP_ONE
				temp.poll().toShow();
				//重畫
				invalidate();
			}
			else if(size > 1)
			{
				//亂數拿掉那個洞, 不要讓他跑出來
				for(int i=0; i<random.nextInt(2) + 1; i++){
					temp.remove(random.nextInt(temp.size())).toShow();
				}
				//重畫
				invalidate();
			}
			
			//慢慢加快速度
			postDelayed(this, progress + random.nextInt(500));
			progress -= 10;
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//是否是有效的
		if(event.getAction() != MotionEvent.ACTION_UP)
		{
			return true;
		}
		
		
		//抓取按下的座標
		float x = event.getX();
		float y = event.getY();
		float offsetIndexX = x - startX;
		float offsetIndexY = y -startY;
		
		//計算是否在RANGE裡
		int indexX = (int)offsetIndexX / 80;
		int indexY = (int)offsetIndexY / 80;

		//抓取座標, 看不是在洞裡
		if(indexX>=3 || indexX <0 || indexY>=4|| indexY<0){
			return true;
		}
		
		//傳送座標過去是否打到地鼠, 由holeQuite's click會知道
		holeQuite.get(indexY* 3 + indexX).click(this);
		return true;
	}
}

