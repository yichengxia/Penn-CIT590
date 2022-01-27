package file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieDBTest {

	//instance of movie db to test
	MovieDB db;
	
	@BeforeEach
	void setUp() throws Exception {
		//initialize movie db
		this.db = new MovieDB();
		
		//setup movie db
		this.db.setUp("moviedata.txt", "movieratings.csv");
	}

	@Test
	void testSetUp() { 
		assertEquals(6, this.db.getActorsInfo().size());
		assertEquals(7, this.db.getMoviesInfo().size());
		
		assertEquals("meryl streep", this.db.getActorsInfo().get(0).getName());
		assertEquals(3, this.db.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", this.db.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", this.db.getMoviesInfo().get(0).getName());
		assertEquals(79, this.db.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, this.db.getMoviesInfo().get(0).getAudienceRating());
	}

}
