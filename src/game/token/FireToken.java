package game.token;

import ui.drawing.TokenPainter;
import ui.drawing.FireTokenPainter;

public class FireToken extends ThreatToken {

	@Override
	public TokenPainter getPainter() {
		return new FireTokenPainter();
	}

}
