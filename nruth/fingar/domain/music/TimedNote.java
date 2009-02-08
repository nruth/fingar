package nruth.fingar.domain.music;

/**
 * contains a note (name & octave), its start beat, and its duration.
 * Is immutable.
 */
public final class TimedNote {
	public TimedNote(Note note, float start_beat, float duration) {
		if(note == null){
			throw new NullPointerException("Note must be provided");
		}
		
		this.note = note;
		this.start_beat = start_beat;
		this.duration = duration;
	}
	
	public Note note() {	return note;	}
	public float start_beat() {	return start_beat; }	
	public float duration() {	return duration;	}
	
	@Override
	public boolean equals(Object object_to_check) {
		if(object_to_check == null) return false;
    	if(object_to_check == this) return true;
    	TimedNote note_to_check = (TimedNote) object_to_check;
    	
    	return (
			note_to_check.note().equals(note())
			&& (note_to_check.start_beat() == start_beat())
			&& (note_to_check.duration() == duration())
    	);
	}
	
	@Override
	public String toString() {
		return super.toString() + " - {"+ note() +"|start:("+ start_beat() +")|dur:("+ duration() +")}";
	}
	
	private final Note note;
	private final float start_beat;
	private final float duration;
}