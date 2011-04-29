package game.HitShrew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class HitShrew extends Activity   // 繼承Activity
{
  
  static HitShrew self;
  
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		self = this;
		
		openOptionsDialog("預備!");  // 顯示「預備」對話框
	}

	public void resetGame() 
	{
	  //將ACTIVITY的VIEW設為自定的HitShrewView
		setContentView(new HitShrewView(this));
	}
	
  //顯示對話框
  private void openOptionsDialog(String info)
{
    new AlertDialog.Builder(this)
    .setTitle("GameStart")
    .setMessage(info)
    .setPositiveButton("OK",
        new DialogInterface.OnClickListener()
        {
         public void onClick(DialogInterface dialoginterface, int i)
         {
           
           ImageManager.init(self);
           resetGame();
         }
         }
        )
    .show();
}

	
}