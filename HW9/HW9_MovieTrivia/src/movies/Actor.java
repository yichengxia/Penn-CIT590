package movies;

import java.util.ArrayList;

/**
 * Represents an Actor with name and list of movies the actor has acted in.
 */
public class Actor {
	
	/**
	 * Name of actor.
	 */
	private String name;
	
	/**
	 * Movies the actor has acted in.
	 */
	private ArrayList<String> moviesCasted = new ArrayList<String>();
	
	/**
	 * Creates Actor with given name.
	 * @param name of actor
	 */
	public Actor(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name of the actor
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the movies the actor has acted in
	 */
	public ArrayList<String> getMoviesCast() {
		return moviesCasted;
	}
	
	/**
	 * Returns String containing the actor's name and the list of movies the actor has acted in.
	 */
	@Override
	public String toString() {
		return "Name: " + name + " Acted in: " + moviesCasted.toString(); 
	}
	
}
