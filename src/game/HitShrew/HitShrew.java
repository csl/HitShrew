package game.HitShrew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class HitShrew extends Activity   // �~��Activity
{
  
  static HitShrew self;
  
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		self = this;
		
		openOptionsDialog("�w��!");  // ��ܡu�w�ơv��ܮ�
	}

	public void resetGame() 
	{
	  //�NACTIVITY��VIEW�]���۩w��HitShrewView
		setContentView(new HitShrewView(this));
	}
	
  //��ܹ�ܮ�
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