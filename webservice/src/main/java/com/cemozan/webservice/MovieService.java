package com.cemozan.webservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MovieService implements IMovieService {
	 public List<Movie> searchByName(@RequestParam(name = "movie_name") String movieName) throws UnirestException{
			
			// Sending API request with using movieName, which is taken from query string named as movie_name, w.r.t CollectAPI documentation.
			 HttpResponse<JsonNode> response =  Unirest.get("https://api.collectapi.com/imdb/imdbSearchByName?query="+movieName)
					  .header("content-type", "application/json")
					  .header("authorization", "apikey 1HsyxyFajXCgMXrvgZOo6t:215vHnXoOm34zQoezFbax3")
					  .asJson();
			 
			 // Creating an empty <Movie> list to add returned movies from API call.
			 List<Movie> movieList = new ArrayList<Movie>();
			 
			 try {
				 
				 // Parsing the response to get body message.
				 JSONObject responsejson = response.getBody().getObject();
				 
				 // Getting "result" part of the parsed object.
				 JSONArray result = responsejson.getJSONArray("result");
				 
		         for(int i = 0 ; i < result.length() ; i++) {	 
		        	 
		        	 // Creating an object to add each movie returned from API call for each iteration.
		        	 Movie movie = new Movie();
		        	 
		        	 // Setting movie attributes using setter methods.
		        	 movie.setTitle(result.getJSONObject(i).getString("Title"));
		        	 movie.setImdbId(result.getJSONObject(i).getString("imdbID"));
		        	 movie.setPoster(result.getJSONObject(i).getString("Poster"));
		        	 movie.setType(result.getJSONObject(i).getString("Type"));
		        	 movie.setYear(result.getJSONObject(i).getString("Year"));
		        	 
		        	 // Adding the movie object to the list.
		        	 movieList.add(movie);
		         }
		         
		         // Returning the movie list to the user as JSON format. Do not needed any extra JSON specification
		         // thanks to the @RestController annotation.
		         return movieList;
		         
			 }catch (Exception e) {
				 // Printing an error message to the console to warn the developer.
				 System.out.println("Error in sending request to external API!");
				 return movieList;
			}
			 
	     }
	 
	 
	 public boolean saveToList(@PathVariable(name = "id") String id) throws UnirestException{

			// Opening the file to write.
			try(FileWriter fw = new FileWriter("./target/movies.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
					// Sending API request.
					HttpResponse<JsonNode> response =  Unirest.get("https://api.collectapi.com/imdb/imdbSearchById?movieId="+id)
							  .header("content-type", "application/json")
							  .header("authorization", "apikey 1HsyxyFajXCgMXrvgZOo6t:215vHnXoOm34zQoezFbax3")
								  .asJson();
					
					// Parsing the "response"
					JSONObject responsejson = response.getBody().getObject();
					JSONObject result = responsejson.getJSONObject("result");
					 
					String title =  (String) result.get("Title");
					String imdbId = (String) result.get("imdbID");
					String poster = (String) result.get("Poster");
					String type = (String) result.get("Type");
					String year = (String) result.get("Year");
					
					// Opening the file to read if the given id's movie is already added to the file.
					try(BufferedReader in = new BufferedReader(new FileReader("./target/movies.txt"))) {
						
					    String str;
					    while ((str = in.readLine()) != null) {
					    	String[] tokens = str.split(",");
					    	if (tokens[0].equals(imdbId)){
					    		// If the movie is already added to the file, print the Error message and return false.
					    		System.out.println("Movie is already added to the file.");
					    		return false;
					    	}
					    }
					}catch (IOException e) {
					    System.out.println("File Read Error");
					}
					
					// If the movie is not already added to the file, print the movie attributes to the file and return true.
					
					String writedTxtToFile = imdbId + "," + title + "," + poster + "," + type + "," + year;
					
					// Writing the file.
					out.println(writedTxtToFile);
					 
					return true;
					
				}catch (Exception e) {
					System.out.println("Error in sending request to external API!");
					return false;
				}
				
					
	    }
	 
	 public Movie addToList(@PathVariable(name = "id") String id) throws UnirestException{
	    	try(BufferedReader in = new BufferedReader(new FileReader("./target/movies.txt"))) {
	    	    String str;
	    	    
	    	    while ((str = in.readLine()) != null) {
	    	    	String[] tokens = str.split(",");
	    	    	
	    	    	// Checking if the file contains the given id's movie. 
	    	    	// If the movie exist, do not send an API request to the external source, use the file
	    	    	if (tokens[0].equals(id)){
	    	    		Movie movie = new Movie();
	    	    		movie.setImdbId(tokens[0]);
	    	    		movie.setTitle(tokens[1]);
	    	    		movie.setPoster(tokens[2]);
	    	    		movie.setType(tokens[3]);
	    	    		movie.setYear(tokens[4]);
	    	    		
	    	    		// Printing the situation report to the developer.
	    	    		System.out.println("Movie is found in the file.");
	    	    		return movie;
	    	    		
	    	    	} 	
	    	    }
	    	    
	    	    try {
	    	    	// If the movie does not exist in the file, send API request to the external source.
	    	    	HttpResponse<JsonNode> response =  Unirest.get("https://api.collectapi.com/imdb/imdbSearchById?movieId="+id)
	  					  .header("content-type", "application/json")
	  					  .header("authorization", "apikey 1HsyxyFajXCgMXrvgZOo6t:215vHnXoOm34zQoezFbax3")
	  						  .asJson();
	  	 
		  			JSONObject responsejson = response.getBody().getObject();
		  			JSONObject result = responsejson.getJSONObject("result");
		  			
		  			Movie movie = new Movie();
		  			movie.setTitle((String) result.get("Title"));
		  			movie.setImdbId((String) result.get("imdbID"));
		  			movie.setPoster((String) result.get("Poster"));
		  			movie.setType((String) result.get("Type"));
		  			movie.setYear((String) result.get("Year"));
		  			
		  			System.out.println("Movie cannot be found in the file. Sending request to the external API.");
		  			
		  			return movie;
		  			
	    	    }catch (Exception e) {
	    	    	System.out.println("Error in sending request to external API!");
					return null;
				}
	    	    
	    	}catch (IOException e) {
	    	    System.out.println("File Read Error");
	    	}
	        return null;
	    }
}
