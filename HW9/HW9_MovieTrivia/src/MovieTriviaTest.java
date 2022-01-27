import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;


class MovieTriviaTest {
	
	// instance of movie trivia object to test
	MovieTrivia mt;
	// instance of movieDB object
	MovieDB movieDB = new MovieDB();
	
	@BeforeEach
	void setUp() throws Exception {
		// initialize movie trivia object
		mt = new MovieTrivia();
		
		// set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		// set up movieDB object
		movieDB.setUp("moviedata.txt", "movieratings.csv");
	}

	@Test
	void testSetUp() { 
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}
	
	@Test
	void testInsertActor() {
		mt.insertActor("test1", new String[]{"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());	
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		
		// add additional test case scenarios
		// when the actor already exists
		mt.insertActor("Brad PITT", new String[]{"seven ", " The Curious Case of Benjamin Button "}, movieDB.getActorsInfo());
		// actor names should not be case sensitive, but spelling must be correct
		assertEquals(7, movieDB.getActorsInfo().size());
		assertEquals("brad pitt", movieDB.getActorsInfo().get(5).getName());
		assertEquals(3, movieDB.getActorsInfo().get(5).getMoviesCast().size());
		assertTrue(movieDB.getActorsInfo().get(5).getMoviesCast().contains("the curious case of benjamin button"));
		// keep the format in actorsInfo
		mt.insertActor(" Robin Williams ", new String[]{" Jumanji "}, movieDB.getActorsInfo());
		assertEquals("robin williams", movieDB.getActorsInfo().get(4).getName());
		assertEquals("jumanji", movieDB.getActorsInfo().get(4).getMoviesCast().get(1));
		// when inserting a new actor, add the new instance of the Actor class to the end of the ArrayList actorsInfo
		mt.insertActor("Bradley Cooper ", new String[]{" The Hangover "}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());
		assertEquals("the hangover", movieDB.getActorsInfo().get(7).getMoviesCast().get(0));
		mt.insertActor("Yicheng Xia", new String[]{}, movieDB.getActorsInfo());
		assertEquals(9, movieDB.getActorsInfo().size());
		assertEquals(0, movieDB.getActorsInfo().get(8).getMoviesCast().size());
	}
	
	@Test
	void testInsertRating() {
		mt.insertRating("testmovie", new int[]{79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
		// add additional test case scenarios
		// create an instance of the Movie class for a new movie and append to the end of moviesInfo
		mt.insertRating("Winnie the Pooh", new int[]{90, 100}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());	
		assertEquals("winnie the pooh", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(90, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(100, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		// update the critics and audience scores for an existing movie in moviesInfo
		// the movie should not be case sensitive, but spelling must be correct
		mt.insertRating("Rocky ii", new int[]{71, 61}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals("rocky ii", movieDB.getMoviesInfo().get(3).getName());
		assertEquals(71, movieDB.getMoviesInfo().get(3).getCriticRating());
		assertEquals(61, movieDB.getMoviesInfo().get(3).getAudienceRating());
		// a movie with leading or trailing whitespace
		mt.insertRating(" Arrival ", new int[]{94, 81}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals("arrival", movieDB.getMoviesInfo().get(1).getName());
		// an incorrect number in ratings or missing ratings
		mt.insertRating("Rocky ii", new int[]{-1, 61}, movieDB.getMoviesInfo());
		assertEquals(71, movieDB.getMoviesInfo().get(3).getCriticRating());
		mt.insertRating("Rocky ii", new int[]{}, movieDB.getMoviesInfo());
		assertEquals(71, movieDB.getMoviesInfo().get(3).getCriticRating());
	}
	
	@Test
	void testSelectWhereActorIs() {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		// add additional test case scenarios
		assertEquals(4, mt.selectWhereActorIs("amy adams", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("amy adams", movieDB.getActorsInfo()).get(0));
		// non-existent actors
		assertEquals(0, mt.selectWhereActorIs("Christopher Robin", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.selectWhereActorIs("Winnie the Pooh", movieDB.getActorsInfo()).size());
		// actor names should not be case sensitive, but spelling must be correct
		assertEquals(mt.selectWhereActorIs("Tom Hanks", movieDB.getActorsInfo()),
		             mt.selectWhereActorIs("ToM hanKS", movieDB.getActorsInfo()));
		// actor names with leading or trailing whitespace
		assertEquals(mt.selectWhereActorIs("Robin Williams ", movieDB.getActorsInfo()),
		             mt.selectWhereActorIs("Robin Williams", movieDB.getActorsInfo()));
	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		// add additional test case scenarios
		assertEquals(2, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("meryl streep"));
		assertTrue(mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("tom hanks"));
		// non-existent movies
		assertEquals(0, mt.selectWhereMovieIs("Christopher Robin", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.selectWhereMovieIs("Winnie the Pooh", movieDB.getActorsInfo()).size());
		// movie names should not be case sensitive, but spelling must be correct
		assertEquals(mt.selectWhereMovieIs("Rocky ii", movieDB.getActorsInfo()),
		             mt.selectWhereMovieIs("Rocky II", movieDB.getActorsInfo()));
		// movie names with leading or trailing whitespace
		assertEquals(mt.selectWhereMovieIs(" Arrival ", movieDB.getActorsInfo()),
		             mt.selectWhereMovieIs("arrival", movieDB.getActorsInfo()));
	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		// add additional test case scenarios
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(6, mt.selectWhereRatingIs('>', 0, false, movieDB.getMoviesInfo()).size());
		// non-existent comparison
		assertEquals(0, mt.selectWhereRatingIs('?', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('.', 0, false, movieDB.getMoviesInfo()).size());
		// a targetRating that is out of range
		assertEquals(0, mt.selectWhereRatingIs('>', -1, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('<', 101, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('>', -1, false, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('<', 101, false, movieDB.getMoviesInfo()).size());
	}
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		// add additional test case scenarios
		// existing actors
		assertEquals(1, mt.getCoActors("tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("meryl streep"));
		// actor names should not be case sensitive, but spelling must be correct
		assertEquals(1, mt.getCoActors("Amy ADAMS", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("Amy ADAMS", movieDB.getActorsInfo()).contains("meryl streep"));
		// actor names with leading or trailing whitespace
		assertEquals(1, mt.getCoActors(" tom HANKS ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors(" tom HANKS ", movieDB.getActorsInfo()).contains("meryl streep"));
		// non-existent actors
		assertEquals(0, mt.getCoActors("Winnie the Pooh", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testGetCommonMovie () {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		// add additional test case scenarios
		// existing actors
		assertEquals(1, mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).contains("doubt"));
		// actor names should not be case sensitive, but spelling must be correct
		assertEquals(1, mt.getCommonMovie("meryl streep", "AMY ADAms", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "AMY ADAms", movieDB.getActorsInfo()).contains("doubt"));
		// actor names with leading or trailing whitespace
		assertEquals(1, mt.getCommonMovie(" meryl streep ", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie(" meryl streep ", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		// actor1 and actor2 can be the same, in this case, return an ArrayList of movie names that this actor was cast in
		assertEquals(0, mt.getCommonMovie("Brandon Krakowsky", "Brandon Krakowsky", movieDB.getActorsInfo()).size());
		assertEquals(2, mt.getCommonMovie("Brad Pitt", "brad pitt", movieDB.getActorsInfo()).size());
		assertEquals("seven", mt.getCommonMovie("Brad Pitt", "brad pitt", movieDB.getActorsInfo()).get(0));
		assertEquals("fight club", mt.getCommonMovie("Brad Pitt", "brad pitt", movieDB.getActorsInfo()).get(1));
	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		// add additional test case scenarios
		// insert rating
		mt.insertRating("Winnie the Pooh", new int[]{90, 100}, movieDB.getMoviesInfo());
		assertEquals(4, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("winnie the pooh"));
		// change rating
		mt.insertRating("jaws", new int[]{84, 84}, movieDB.getMoviesInfo());
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		// change rating again (down)
		mt.insertRating("et", new int[]{80, 100}, movieDB.getMoviesInfo());
		assertEquals(2, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
		// change rating again (up)
		mt.insertRating("jaws", new int[]{85, 86}, movieDB.getMoviesInfo());
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		// add additional test case scenarios
		// existing movies
		assertEquals(1, mt.getCommonActors("doubt", "arrival", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "arrival", movieDB.getActorsInfo()).contains("amy adams"));
		// movie names with leading or trailing whitespace
		assertEquals(1, mt.getCommonActors(" Doubt ", "arrival", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors(" Doubt ", "arrival", movieDB.getActorsInfo()).contains("amy adams"));
		// movie1 and movie2 can be the same
		assertEquals(2, mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertTrue(mt.getCommonActors("doubt", "doubt", movieDB.getActorsInfo()).contains("amy adams"));
		// non-existent movies
		assertEquals(0, mt.getCommonActors("doubt", "winnie the pooh", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testGetMean () {
		// add ALL test case scenarios!
		assertTrue(Math.abs(67.86 - MovieTrivia.getMean(movieDB.getMoviesInfo())[0]) <= 0.01);
		assertTrue(Math.abs(65.71 - MovieTrivia.getMean(movieDB.getMoviesInfo())[1]) <= 0.01);
		// insert a new rating, then get mean again
		mt.insertRating("Winnie the Pooh", new int[]{90, 100}, movieDB.getMoviesInfo());
		assertTrue(Math.abs(70.62 - MovieTrivia.getMean(movieDB.getMoviesInfo())[0]) <= 0.01);
		assertTrue(Math.abs(70.00 - MovieTrivia.getMean(movieDB.getMoviesInfo())[1]) <= 0.01);
		// insert a new rating, then get mean again
		mt.insertRating("Christopher Robin", new int[]{90, 100}, movieDB.getMoviesInfo());
		assertTrue(Math.abs(72.78 - MovieTrivia.getMean(movieDB.getMoviesInfo())[0]) <= 0.01);
		assertTrue(Math.abs(73.33 - MovieTrivia.getMean(movieDB.getMoviesInfo())[1]) <= 0.01);
		// update an existing rating, then get mean again
		mt.insertRating("christopher robin", new int[]{0, 0}, movieDB.getMoviesInfo());
		assertTrue(Math.abs(62.78 - MovieTrivia.getMean(movieDB.getMoviesInfo())[0]) <= 0.01);
		assertTrue(Math.abs(62.22 - MovieTrivia.getMean(movieDB.getMoviesInfo())[1]) <= 0.01);
		// update an existing rating, then get mean again
		mt.insertRating("winnie the pooh", new int[]{0, 0}, movieDB.getMoviesInfo());
		assertTrue(Math.abs(52.78 - MovieTrivia.getMean(movieDB.getMoviesInfo())[0]) <= 0.01);
		assertTrue(Math.abs(51.11 - MovieTrivia.getMean(movieDB.getMoviesInfo())[1]) <= 0.01);
	}
}
