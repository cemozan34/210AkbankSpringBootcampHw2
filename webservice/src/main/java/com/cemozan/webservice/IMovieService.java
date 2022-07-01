package com.cemozan.webservice;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface IMovieService {
	public List<Movie> searchByName(@RequestParam(name = "movie_name") String movieName) throws UnirestException;
	public boolean saveToList(@PathVariable(name = "id") String id) throws UnirestException;
	public Movie addToList(@PathVariable(name = "id") String id) throws UnirestException;
}
