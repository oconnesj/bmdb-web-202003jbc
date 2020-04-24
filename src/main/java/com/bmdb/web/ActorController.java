package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.business.JsonResponse;
import com.bmdb.db.ActorRepository;

@RestController
@RequestMapping("/actors")

public class ActorController {
	@Autowired
	private ActorRepository actorRepo;

	@GetMapping("/")
	public JsonResponse list(){
		JsonResponse jr = null;
		List<Actor> actors = actorRepo.findAll();
		if (actors.size()>0) {
			jr = JsonResponse.getInstance(actors);

		}
		else {
			jr = JsonResponse.getErrorInstance("No actors found.");

		}
		
		return jr;
	}
	

	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		// Expected responses?
		// 1 - a single actor 
		// 2 - bad id  - no actor found 
		// 3 - Exception?? - hold off for now, implement 
		//					exception handling as needed 
		JsonResponse jr = null;
		Optional<Actor> actor = actorRepo.findById(id);
		if (actor.isPresent()) {
			jr = JsonResponse.getInstance(actor.get());
			
		}
		else {
			jr = JsonResponse.getErrorInstance("No actor found for id: "+id);
		}
		return jr;
	}
	
	
	// create method 
	@PostMapping("/")
	public JsonResponse createMoive(@RequestBody Actor a) {
		JsonResponse jr = null;
		
		
		try {
			a = actorRepo.save(a);
			jr = JsonResponse.getInstance(a);
		} 		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		
		return jr;
		
	}
	//update method 
	
	@PutMapping("/")
	public JsonResponse updateActor(@RequestBody Actor a) {
		JsonResponse jr = null;
		
		
		try {
			a = actorRepo.save(a);
			jr = JsonResponse.getInstance(a);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating actor: " + e.getMessage());
			e.printStackTrace();


		}
		return jr;

		
	}
	@DeleteMapping("/{id}")
	public JsonResponse deleteActor(@PathVariable int id) {
		JsonResponse jr = null;
		
		
		try {
			actorRepo.deleteById(id);
			jr = JsonResponse.getInstance("Actor id:  "+id+" deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting actor: " + e.getMessage());
			e.printStackTrace();


		}
		return jr;
		
	}
	
	
}
