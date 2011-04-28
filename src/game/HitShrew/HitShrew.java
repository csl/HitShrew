package game.HitShrew;

import android.app.Activity;
import android.os.Bundle;

public class HitShrew extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageManager.init(this);
		resetGame();
	}

	public void resetGame() {
		setContentView(new HitShrewView(this));
	}
}