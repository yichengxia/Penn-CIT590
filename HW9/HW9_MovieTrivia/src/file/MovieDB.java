package file;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import movies.Actor;
import movies.Movie;

/**
 * Loads movie and ratings data from two given data files.
 * Creates two ArrayLists for storing the information in those data files.
 *
 */
public class MovieDB {
	
	/**
	 * List of actors information.
	 */
	private ArrayList<Actor> actorsInfo = new ArrayList<Actor>();
	
	/**
	 * List of movies information.
	 */
	private ArrayList<Movie> moviesInfo = new ArrayList<Movie>();
	
	/**
	 * Loads and parses the given movieData and movieRatings data files.
	 * @param movieData file to load and parse
	 * @param movieRatings file to load and parse
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movieData file
		try {
			File f = new File(movieData);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.trim().split(",");
				Actor newActor = new Actor(array[0].trim().toLowerCase());
				for (int i = 1; i < array.length; i++) {
					newActor.getMoviesCast().add(array[i].trim().toLowerCase());
				}
				actorsInfo.add(newActor);
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//load movieRatings file
		try {
			File f = new File(movieRatings);
			FileReader fd = new FileReader(f);
			BufferedReader br = new BufferedReader(fd);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.trim().split(",");
				if (array[1].trim().charAt(0) >= '0' && array[1].trim().charAt(0) <= '9') {
					Movie newMovie = new Movie(array[0].trim().toLowerCase(), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
					moviesInfo.add(newMovie);
				}
			}
			fd.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get actors information.
	 * @return list of actors
	 */
	public ArrayList<Actor> getActorsInfo() {
		return this.actorsInfo;
	}
	
	/**
	 * Get movies information.
	 * @return list of movies
	 */
	public ArrayList<Movie> getMoviesInfo() {
		return this.moviesInfo;
	}

}
