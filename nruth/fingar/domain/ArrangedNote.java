package nruth.fingar.domain;


/**
 * contains a note (name & octave), its start beat, and its duration
 */
public final class ArrangedNote {
	private Note note;
	private float start_beat;
	private float duration;
	
	public ArrangedNote(Note note, float start_beat, float duration) {
		if(note == null){
			throw new NullPointerException("Note must be provided");
		}
		
		this.note = note;
		this.start_beat = start_beat;
		this.duration = duration;
	}
	
	public Note note() {
		return note;
	}
	
	public float start_beat() {
		return start_beat;
	}
	
	public float duration() {
		return duration;
	}
}