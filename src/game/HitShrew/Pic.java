package game.HitShrew;

public class Pic {
  
  //�N�Ϥ����T�Ӥj��
	public static final int NOTHING = 0;    //�S�a��
	
	public static final int UP_ONE = 13;    //�a���X�ӤF
	
	public static final int DOWN_HIT = -9;  //���U�h�F
	
	int currentType = NOTHING;
	
	//�U�@�i��
	public void toNext()
	{
		if(currentType > 0){
		  //���Y
			currentType --;
		}
		else if(currentType < 0){
      //�@�Y
			currentType ++;
		}
	}

  //�Y�X�ӤF
	public void toShow() {
		currentType = UP_ONE;         //�Y�X�ӳ̰��I�F
	}
	
  //���U�hcheck
	public void click(HitShrewView ptr){
		if(currentType > NOTHING)        //�p�G�a�����X��
		{
		  HitShrewView.self.PlayMedia();        //���ĥ��X��
		  HitShrewView.self.score++;            //�[��
			currentType = DOWN_HIT;               //���A�]�����U�h
		}
	}
}
