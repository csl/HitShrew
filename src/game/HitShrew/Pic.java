package game.HitShrew;

public class Pic {
  
  //將圖片分三個大項
	public static final int NOTHING = 0;    //沒地鼠
	
	public static final int UP_ONE = 13;    //地鼠出來了
	
	public static final int DOWN_HIT = -9;  //打下去了
	
	int currentType = NOTHING;
	
	//下一張圖
	public void toNext()
	{
		if(currentType > 0){
		  //降頭
			currentType --;
		}
		else if(currentType < 0){
      //昇頭
			currentType ++;
		}
	}

  //頭出來了
	public void toShow() {
		currentType = UP_ONE;         //頭出來最高點了
	}
	
  //按下去check
	public void click(HitShrewView ptr){
		if(currentType > NOTHING)        //如果地鼠有出來
		{
		  HitShrewView.self.PlayMedia();        //音效打出來
		  HitShrewView.self.score++;            //加分
			currentType = DOWN_HIT;               //狀態設為打下去
		}
	}
}
