package com.cemozan.webservice;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class MovieController implements IMovieService{
	
	@Autowired
	private IMovieService service;
	 
	
	@GetMapping("/movies/search")
	// Handling the "GET" request from user for localhost:port_number/movies/search?movie_name=movieName
    public List<Movie> searchByName(@RequestParam(name = "movie_name") String movieName) throws UnirestException{
		
		return this.service.searchByName(movieName); // Changing the code to more clear way and MVC standards. (Thanks to the office hours)
		 
     }
	
	
	@PostMapping("/movies/saveToList/{id}")
	// Handling the "POST" request from user for localhost:port_number/movies/saveToList/id
    public boolean saveToList(@PathVariable(name = "id") String id) throws UnirestException{

		return this.service.saveToList(id); // Changing the code to more clear way and MVC standards. (Thanks to the office hours)
		
    }

	
	@PostMapping("/movies/detail/{id}")
    public Movie addToList(@PathVariable(name = "id") String id) throws UnirestException{
		
    	return this.service.addToList(id); // Changing the code to more clear way and MVC standards. (Thanks to the office hours)
    	
    }
    
	
}
