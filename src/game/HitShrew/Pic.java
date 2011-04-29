package game.HitShrew;

public class Pic {
  
  //盢瓜だ兜
	public static final int NOTHING = 0;    //⊿公
	
	public static final int UP_ONE = 13;    //公ㄓ
	
	public static final int DOWN_HIT = -9;  //ゴ
	
	int currentType = NOTHING;
	
	//眎瓜
	public void toNext()
	{
		if(currentType > 0){
		  //繷
			currentType --;
		}
		else if(currentType < 0){
      //狜繷
			currentType ++;
		}
	}

  //繷ㄓ
	public void toShow() {
		currentType = UP_ONE;         //繷ㄓ程蔼翴
	}
	
  //check
	public void click(HitShrewView ptr){
		if(currentType > NOTHING)        //狦公Τㄓ
		{
		  HitShrewView.self.PlayMedia();        //ゴㄓ
		  HitShrewView.self.score++;            //だ
			currentType = DOWN_HIT;               //篈砞ゴ
		}
	}
}
