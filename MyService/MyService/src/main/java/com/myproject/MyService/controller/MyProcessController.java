package com.myproject.MyService.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.MyService.model.MyProcess;
import com.myproject.MyService.repository.MyProcessRepository;

@RestController
@RequestMapping("/p1")
public class MyProcessController {

	@Autowired
	private MyProcessRepository myProcessRepo;

	@GetMapping("/Process")
	public ResponseEntity<?> getAllProcess() {
		List<MyProcess> myProcess = myProcessRepo.findAll();

		if (myProcess.size() > 0) {
			return new ResponseEntity<List<MyProcess>>(myProcess, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No process Available", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/Process")
	public ResponseEntity<?> createProcess(@RequestBody MyProcess myProcess) {
		try {
			myProcess.setCreatedAt(new Date(System.currentTimeMillis()));
			myProcessRepo.save(myProcess);
			return new ResponseEntity<MyProcess>(myProcess, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/Process/{id}")
	public ResponseEntity<?> getSingleProcess(@PathVariable("id") String id) {
		Optional<MyProcess> myProcessOptional = myProcessRepo.findById(id);

		if (myProcessOptional.isPresent()) {
			return new ResponseEntity<>(myProcessOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No process Available for this id.", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/Process/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody MyProcess myProcess) throws InterruptedException {
		Optional<MyProcess> myProcessOptional = myProcessRepo.findById(id);

		if (myProcessOptional.isPresent() && !myProcessOptional.get().getRunning()) {
			try {
				MyProcess myProcToSave = myProcessOptional.get();
				myProcToSave.setRunning(true);
				myProcToSave.setCompleted(
						myProcess.getCompleted() != null ? myProcess.getCompleted() : myProcToSave.getCompleted());
				myProcToSave.setName(myProcess.getName() != null ? myProcess.getName() : myProcToSave.getName());
				myProcToSave.setDescription(myProcess.getDescription() != null ? myProcess.getDescription()
						: myProcToSave.getDescription());
				myProcToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
				myProcessRepo.save(myProcToSave);
				
				Thread.sleep(15000);
				
				myProcToSave.setRunning(false);
				myProcToSave.setCompleted(true);
				myProcessRepo.save(myProcToSave);
				return new ResponseEntity<>(myProcToSave, HttpStatus.OK);
			} catch(Exception e)
			{
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		} else {
			
			if(myProcessOptional.isEmpty())
			{
				return new ResponseEntity<>("this id does not exists.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>("Process 2 is running. Please wait for Process 1.....", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/Process/{id}")
	public ResponseEntity<?> deleteProcessById(@PathVariable("id") String id) {
		try {
			myProcessRepo.deleteById(id);
			return new ResponseEntity<>("Successfully Deleted Process", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
