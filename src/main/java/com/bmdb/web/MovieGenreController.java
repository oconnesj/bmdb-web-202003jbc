package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.MovieGenre;
import com.bmdb.business.JsonResponse;
import com.bmdb.db.MovieGenreRepository;

@RestController
@RequestMapping("/moviegenres")

public class MovieGenreController {
	@Autowired
	private MovieGenreRepository moviegenreRepo;

	@GetMapping("/")
	public JsonResponse list(){
		JsonResponse jr = null;
		List<MovieGenre> moviegenres = moviegenreRepo.findAll();
		if (moviegenres.size()>0) {
			jr = JsonResponse.getInstance(moviegenres);

		}
		else {
			jr = JsonResponse.getErrorInstance("No movie genres found.");

		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		// Expected responses?
		// 1 - a single moviegenre 
		// 2 - bad id  - no moviegenre found 
		// 3 - Exception?? - hold off for now, implement 
		//					exception handling as needed 
		JsonResponse jr = null;
		Optional<MovieGenre> moviegenre = moviegenreRepo.findById(id);
		if (moviegenre.isPresent()) {
			jr = JsonResponse.getInstance(moviegenre.get());
			
		}
		else {
			jr = JsonResponse.getErrorInstance("No movie genre found for id: "+id);
		}
		return jr;
	}
	
	
	// create method 
	@PostMapping("/")
	public JsonResponse createMoive(@RequestBody MovieGenre mg) {
		JsonResponse jr = null;
		
		
		try {
			mg = moviegenreRepo.save(mg);
			jr = JsonResponse.getInstance(mg);
		} 		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		
		return jr;
		
	}
	//update method 
	
	@PutMapping("/")
	public JsonResponse updateMovieGenre(@RequestBody MovieGenre mg) {
		JsonResponse jr = null;
		
		
		try {
			mg = moviegenreRepo.save(mg);
			jr = JsonResponse.getInstance(mg);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating movie genre: " + e.getMessage());
			e.printStackTrace();


		}
		return jr;

		
	}
	@DeleteMapping("/{id}")
	public JsonResponse deleteMovieGenre(@PathVariable int id) {
		JsonResponse jr = null;
		
		
		try {
			moviegenreRepo.deleteById(id);
			jr = JsonResponse.getInstance("MovieGenre id:  "+id+" deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting movie genre: " + e.getMessage());
			e.printStackTrace();


		}
		return jr;
		
	}
	
	
}
