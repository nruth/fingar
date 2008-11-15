/**
	 
 */
package nruth.gitfga;

import java.util.*;

import nruth.gitfga.Notes.Note;
import nruth.gitfga.Position.GuitarString;

/** an octave of notes given positions on the fretboard
	@author nicholasrutherford
	
 */
public final class Octave { //TODO how am I supposed to be using this again . . .
	Object[] lookup_table;
	
	private ArrayList<Position> lookup(Note note){ return (ArrayList<Position>) lookup_table[note.ordinal()]; }
	
	
	public Octave(Position root_c_position){
		lookup_table = new Object[Note.values().length];
		
		for(int idx=0; idx<Notes.Note.values().length; idx++){ 
			lookup_table[idx] = new ArrayList<Position>(5);
		}
		
		lookup(Note.C).add(root_c_position);
		
		//for each string
		//	find the relative fret location from root_c
		//	and modify it by the scale interval of the wanted note, e.g. +2 frets for D
		//	if the generated position is valid, keep it, otherwise discard it
		
		//for each note
		//get all possible positions
		//and filter by stating must be < 12 frets from root_c when converting string gaps to relative fret distances
		for(Note note : Note.values()){
			Position[] all = note.getPositionsForNote();
			for(Position p : all){
				int relative_fret_gap = p.getFret() - root_c_position.getFret();
				System.out.println(p+" against "+root_c_position);
				System.out.print("frets: "+relative_fret_gap);
				relative_fret_gap -= p.getString().get_relative_fret_gap(root_c_position.getString());
				System.out.println(" and with strings: "+relative_fret_gap);
				if(relative_fret_gap > 0 && relative_fret_gap < 12){ lookup(note).add(p); }
			}
		}
	}
	
	public Position[] getPositionsForNote(Note n){ 
		ArrayList<Position> positions = lookup(n); 
		return positions.toArray(new Position[positions.size()]);
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int idx=0; idx<lookup_table.length; idx++ ){
			ArrayList<Position> note_positions = (ArrayList<Position>)lookup_table[idx];
			str.append("Note: "+Note.values()[idx]);
			for(Position p : note_positions){
				str.append("["+p+"]");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	public static void main(String args[]){
		Octave octave = new Octave(new Position(1,GuitarString.B));
		System.out.println(octave.toString());
	}
}
