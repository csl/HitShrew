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
	
  //�w�]��
  int COLUMN_COUNT = 3;
  int ROW_COUNT = 3;
  int HOLE_COUNT = COLUMN_COUNT * ROW_COUNT;     //�j�p: 3x3
  
  int internel = 2000;
  String KEY = "key";
  int TILE_SIZE = 80;  
  
	private static Random random = new Random(); //�üƬ��F�����n�X��
	
  int startX = 35;
  int startY = 60;
  
  public int gameLength = 30;    //�C���ɶ�

  public int score = 0;         //�C������
  
  public int progress = 800;    //�p��[�֪��ɶ�
  public int aimProgress = 0;

  static HitShrewView self;

  private MediaPlayer mMediaPlayer;       //����
  private Timer timer = new Timer(true);  //�p��ɶ�
	
	public HitShrewView(Context context) 
	{
		super(context);
		self = this;
		
		postDelayed(flush80ms, 80);   //���a���]�X��
		this.postDelayed(flush1000ms, 1000);   //��s���p�M�æ��M�w�֭n�a���]�X��
				
    timer.schedule(new timerTask(), 1000, 1000);   //�}�l�p��ɶ�

	}
	
	public class timerTask extends TimerTask   //�p�⾹
  {
    public void run()
    {
      gameLength--;
    }
  };
	
	public void PlayMedia()    //���񭵮�
	{
    try
    {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setDataSource( "/sdcard/sound.wav" );
      mMediaPlayer.prepare();
      mMediaPlayer.start();        //���񭵮�

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
	
	//�O���a��, �@3x3��, �C�@���@��Ojbect Pic�ӰO��
	private List<Pic> holeQuite = new ArrayList<Pic>(HOLE_COUNT);
	{
		for(int i=0; i<HOLE_COUNT; i++){
			holeQuite.add(new Pic());
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas)     //�}�ldraw�a�� 
	{
		super.onDraw(canvas);
		
		if(gameLength <= 0 )   //�ɶ���A�C������
		{
			getHandler().removeCallbacks(flush1000ms);
			getHandler().removeCallbacks(flush80ms);
			timer.cancel();
			timer.purge();			
			doGameOver();
			return ;
		}
		
		//�e���h��color
		canvas.drawColor(Color.WHITE);		
		drawInfoPanel(canvas);
		
		for(int i=0; i<holeQuite.size(); i++)         //�@�Ӭ}�@�Ӭ}�B�z
		{
	    //�N�Ϥ��qholeQuite�����
			Pic pic = holeQuite.get(i);
			
			//�B�z3x3��j�p, �ݲ{�b�O���@��
			int y = i / COLUMN_COUNT;
			int x = i % COLUMN_COUNT;
			
			//����{�b�a��(Pic)�{�b��status�M��ޥX��
      Bitmap  bm = ImageManager.getBitmap(pic.currentType);
  		//draw
			canvas.drawBitmap(bm, startX + x*TILE_SIZE, 
						                startY + y*TILE_SIZE, 
						PaintSuite.paintForQuite);
			
			  //���U�@�i��, ���a�����b�ʪ��ˤl
			  pic.toNext();
		}
	}
	
	private void doGameOver()         //game�����F
	{
	  //��ܡuFinish�v��ܮ�
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
			  HitShrew.self.finish();   //�����{��
			}
		}).show();
	}

	//draw ���W����T, �ɶ��M�o��
	private void drawInfoPanel(Canvas canvas)
	{
    // ��ܮɶ�
		canvas.drawText("Time: " + gameLength, 29, 20, PaintSuite.KV4text);
	  // ��ܡu�����v������
		canvas.drawText("Scores: " + score *10, 29, 50, PaintSuite.KV4text);
	}


	Runnable flush80ms = new Runnable()
	{
		public void run(){
		  //���sdraw, ���a����ʧ@
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
			  //holeQuite����X���A�OPic.NOTHING
			  //�M��ڭ̭n���a���]�X��
				if(each.currentType == Pic.NOTHING)
				{
					//�[�J����
					temp.add(each);
				}
			}
			
			//�ݤj�p
			int size = temp.size();
			
			if(size == 1)
			{
				//�u���@��, �⥦�ܦ�UP_ONE
				temp.poll().toShow();
				//���e
				invalidate();
			}
			else if(size > 1)
			{
				//�üƮ������Ӭ}, ���n���L�]�X��
				for(int i=0; i<random.nextInt(2) + 1; i++){
					temp.remove(random.nextInt(temp.size())).toShow();
				}
				//���e
				invalidate();
			}
			
			//�C�C�[�ֳt��
			postDelayed(this, progress + random.nextInt(500));
			progress -= 10;
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//�O�_�O���Ī�
		if(event.getAction() != MotionEvent.ACTION_UP)
		{
			return true;
		}
		
		
		//������U���y��
		float x = event.getX();
		float y = event.getY();
		float offsetIndexX = x - startX;
		float offsetIndexY = y -startY;
		
		//�p��O�_�bRANGE��
		int indexX = (int)offsetIndexX / 80;
		int indexY = (int)offsetIndexY / 80;

		//����y��, �ݤ��O�b�}��
		if(indexX>=3 || indexX <0 || indexY>=4|| indexY<0){
			return true;
		}
		
		//�ǰe�y�йL�h�O�_����a��, ��holeQuite's click�|���D
		holeQuite.get(indexY* 3 + indexX).click(this);
		return true;
	}
}

