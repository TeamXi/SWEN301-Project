package nz.ac.victoria.ecs.kpsmart.state.entities.state;

public enum Direction {
	From, To;

	public Direction flip() {
		switch(this) {
			case From: return To;
			case To: return From;
		}
		return null;
	}
}