import java.util.ArrayList;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 * @author Yicheng Xia
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	
	public static void main(String[] args) {
		
		// create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		// setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		// load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		// print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors() {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies() {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	/**
	 * Inserts given actor and his/her movies into database
	 * While this method is called “insertActor”, it should actually do an insert or an update
	 * This method does not return anything
	 * It will work by just modifying the given actorsInfo ArrayList passed to it
	 * @param actor the actor name as a string
	 * @param movies a String array of movie names that the actor has acted in
	 * @param actorsInfo the ArrayList that is to be inserted into/updated
	 */
	public void insertActor(String actor, String[] movies, ArrayList<Actor> actorsInfo) {
		// initiate newActor by triming whitespace and converting all characters to lowercase
		Actor newActor = new Actor(actor.trim().toLowerCase());
		for (int i = 0; i < movies.length; i++) {
			// add movies by triming whitespace and converting all characters to lowercase
			newActor.getMoviesCast().add(movies[i].trim().toLowerCase());
		}
		for (int i = 0; i < actorsInfo.size(); i++) {
			// if the given actor is already present in the given actorsInfo arraylist
			// append the given movies to the end of moviesCasted
			if (actorsInfo.get(i).getName().equals(newActor.getName())) {
				for (int j = 0; j < newActor.getMoviesCast().size(); j++) {
					// skip duplicate movies
					if (!actorsInfo.get(i).getMoviesCast().contains(newActor.getMoviesCast().get(j))) {
						actorsInfo.get(i).getMoviesCast().add(newActor.getMoviesCast().get(j));
					}
				}
				return; // return after adding finishes
			}
		}
		// if the given actor is not already present in the given actorsInfo arraylist,
		// append the given actor along with their given movies to the end
		actorsInfo.add(newActor);
	}

	/**
	 * Inserts given ratings for given movie into database
	 * Updates the ratings for a movie if the movie is already in the database
	 * @param movie the movie name as a string
	 * @param ratings an int array with 2 elements: the critics' rating at index 0 and the audience rating at index 1
	 * @param moviesInfo the ArrayList that is to be inserted into/updated
	 */
	public void insertRating(String movie, int[] ratings, ArrayList<Movie> moviesInfo) {
		// avoid incorrect numbers in ratings or missing ratings
		if (ratings.length != 2 || ratings[0] < 0 || ratings[1] > 100) {
			return;
		}
		// initiate newMovie by triming whitespace and converting all characters to lowercase
		Movie newMovie = new Movie(movie.trim().toLowerCase(), ratings[0], ratings[1]);
		for (int i = 0; i < moviesInfo.size(); i++) {
			// check if the movie is already in the database
			if (moviesInfo.get(i).getName().equals(newMovie.getName())) {
				// if so, set new ratings and return
				moviesInfo.set(i, newMovie);
				return;
			}
		}
		// otherwise add as newMovie
		moviesInfo.add(newMovie);
	}
	
	/**
	 * Given an actor, returns the list of all movies
	 * Given a non-existent movie, this method should return an empty list
	 * @param actor the name of an actor as a String
	 * @param actorsInfo the ArrayList to get the data from
	 * @return the list of all movies based on the given actor
	 */
	public ArrayList<String> selectWhereActorIs(String actor, ArrayList<Actor> actorsInfo) {
		for (int i = 0; i < actorsInfo.size(); i++) {
			// check if actor is in actorsInfo
			if (actorsInfo.get(i).getName().equals(actor.trim().toLowerCase())) {
				// if so, return moviesCasted in actorsInfo
				return actorsInfo.get(i).getMoviesCast();
			}
		}
		// otherwise return an empty ArrayList
		return new ArrayList<String>();
	}

	/**
	 * Given a movie, returns the list of all actors in that movie
	 * Given a non-existent movie, this method should return an empty list
	 * @param movie the name of a movie as a String
	 * @param actorsInfo the ArrayList to get the data from
	 * @return the list of all actors based on the given movie
	 */
	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList<Actor> actorsInfo) {
		// initiate movieList for return
		ArrayList<String> movieList = new ArrayList<String>();
		for (int i = 0; i < actorsInfo.size(); i++) {
			// check if movie is in actorsInfo
			if (actorsInfo.get(i).getMoviesCast().contains(movie.trim().toLowerCase())) {
				// if so, add it to movieList
				movieList.add(actorsInfo.get(i).getName());
			}
		}
		return movieList;
	}

	/**
	 * Returns a list of movies that satisfy an inequality or equality,
	 * based on the comparison argument and the targeted rating argument
	 * Given incorrect input, this method should return an empty list
	 * @param comparison either '='', '>', or '<' and passed in as a char
	 * @param targetRating
	 * @param isCritic a boolean that represents whether we are interested in the critics' rating or the audience rating
	 *                 true = critic ratings, false = audience ratings
	 * @param moviesInfo the ArrayList that is to be searched
	 * @return a list of movies that satisfy an inequality or equality
	 */
	public ArrayList<String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList<Movie> moviesInfo) {
		// initiate movieList for return
		ArrayList<String> movieList = new ArrayList<String>();
		// avoid invalid cases by directly returning empty movieList
		if (targetRating < 0 || targetRating > 100 || !(comparison == '=' || comparison == '>' || comparison == '<')) {
			return movieList;
		}
		// match comparison, targetRating and isCritic
		// break and return after any case
		switch (comparison) {
			case '=':
				if (isCritic) {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getCriticRating() == targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}	
				} else {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getAudienceRating() == targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}
				}
				break;
			case '>':
				if (isCritic) {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getCriticRating() > targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}	
				} else {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getAudienceRating() > targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}
				}
				break;
			case '<':
				if (isCritic) {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getCriticRating() < targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}	
				} else {
					for (int i = 0; i < moviesInfo.size(); i++) {
						if (moviesInfo.get(i).getAudienceRating() < targetRating) {
							movieList.add(moviesInfo.get(i).getName());
						}
					}
				}
				break;
		}
		return movieList;
	}

	/**
	 * Returns a list of all actors that the given actor has ever worked with in any movie
	 * except the actor herself/himself.
	 * @param actor the name of an actor as a String
	 * @param actorsInfo the ArrayList to search through
	 * @return a list of actors
	 */
	public ArrayList<String> getCoActors(String actor, ArrayList<Actor> actorsInfo) {
		// initiate searchList for search
		ArrayList<String> searchList = selectWhereActorIs(actor, actorsInfo);
		// initiate returnList for return
		ArrayList<String> returnList = new ArrayList<String>();
		for (int i = 0; i < actorsInfo.size(); i++) {
			// skip the actor itself
			if (actorsInfo.get(i).getName().equals(actor.trim().toLowerCase())) {
				continue;
			}
			for (int j = 0; j < searchList.size(); j++) {
				// check if searchList contains the same movie
				if (actorsInfo.get(i).getMoviesCast().contains(searchList.get(j))) {
					// if so, add and break to search for the next actor
					returnList.add(actorsInfo.get(i).getName());
					break;
				}
			}
		}
		return returnList;
	}

	/**
	 * Returns a list of movie names where both actors were cast
	 * In cases where the two actors have never worked together, this method returns an empty list
	 * @param actor1
	 * @param actor2
	 * @param actorsInfo
	 * @return a list of movie names where both actors were cast
	 */
	public ArrayList<String> getCommonMovie(String actor1, String actor2, ArrayList<Actor> actorsInfo) {
		// initiate movieList for return
		ArrayList<String> movieList = new ArrayList<String>();
		// call selectWhereActorIs to get ArrayList actor1Movie and actor2Movie for actor1 and actor2
		ArrayList<String> actor1Movie = selectWhereActorIs(actor1, actorsInfo);
		ArrayList<String> actor2Movie = selectWhereActorIs(actor2, actorsInfo);
		for (int i = 0; i < actor1Movie.size(); i++) {
			// check if actor2Movie contains the same movie in actor1Movie
			if (actor2Movie.contains(actor1Movie.get(i))) {
				// if so, add it to movieList
				movieList.add(actor1Movie.get(i));
			}
		}
		return movieList;
	}

	/**
	 * Returns a list of movie names that both critics and the audience have rated above 85 (>= 85)
	 * @param moviesInfo
	 * @return a list of movie names that both critics and the audience have rated above 85
	 */
	public ArrayList<String> goodMovies(ArrayList<Movie> moviesInfo) {
		// initiate movieList for return
		ArrayList<String> movieList = new ArrayList<String>();
		for (int i = 0; i < moviesInfo.size(); i++) {
			// check if moviesInfo contains the movie with both ratings >= 85
			if (moviesInfo.get(i).getCriticRating() >= 85 && moviesInfo.get(i).getAudienceRating() >= 85) {
				// if so, add its name to movieList
				movieList.add(moviesInfo.get(i).getName());
			}
		}
		return movieList;
	}

	/**
	 * Given a pair of movies, this method returns a list of actors that acted in both movies
	 * In cases where the movies have no actors in common, it returns an empty list
	 * @param movie1
	 * @param movie2
	 * @param actorsInfo
	 * @return a list of actors that acted in both movies
	 */
	public ArrayList<String> getCommonActors(String movie1, String movie2, ArrayList<Actor> actorsInfo) {
		// initiate actorList for return
		ArrayList<String> actorList = new ArrayList<String>();
		// call selectWhereMovieIs to get ArrayList movie1Actor and movie2Actor for movie1 and movie2
		ArrayList<String> movie1Actor = selectWhereMovieIs(movie1, actorsInfo);
		ArrayList<String> movie2Actor = selectWhereMovieIs(movie2, actorsInfo);
		for (int i = 0; i < movie1Actor.size(); i++) {
			// check if movie2Actor contains the same movie in movie1Actor
			if (movie2Actor.contains(movie1Actor.get(i))) {
				// if so, add it to movieList
				actorList.add(movie1Actor.get(i));
			}
		}
		return actorList;
	}

	/**
	 * Given the moviesInfo DB, this static method returns the mean value of the critics' ratings and the audience ratings
	 * @param moviesInfo
	 * @return the mean value of the critics' ratings and the audience ratings
	 */
	public static double[] getMean(ArrayList<Movie> moviesInfo) {
		double[] mean = new double[2];
		double sumCritic = 0.0;
		double sumAudience = 0.0;
		for (int i = 0; i < moviesInfo.size(); i++) {
			// automatically casting int ratings to double by adding to sumCritic and sumAudience
			sumCritic += moviesInfo.get(i).getCriticRating();
			sumAudience += moviesInfo.get(i).getAudienceRating();
		}
		// calculate mean
		mean[0] = sumCritic / moviesInfo.size();
		mean[1] = sumAudience / moviesInfo.size();
		return mean;
	}
	
}
