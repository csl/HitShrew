package game.HitShrew;

import android.graphics.Color;
import android.graphics.Paint;

//�M�w���W��T����rcolor�M�j�p
public class PaintSuite {

	static Paint KV4text = new Paint();
	static Paint paintForQuite = new Paint();
	static Paint paintForNoise = new Paint();
	static {
		paintForQuite.setColor(Color.BLUE);
		paintForNoise.setColor(Color.RED);
		KV4text.setColor(Color.RED);
		KV4text.setTextSize(22);
	}
}
