package com.example.cassandra_beta.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.cassandra_beta.model.Example;
import com.example.cassandra_beta.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

  @Autowired
  ExampleRepository exampleRepository;

  @GetMapping("/tutorials")
  public ResponseEntity<List<Example>> getAllTutorials(@RequestParam(required = false) String title) {
    try {
      List<Example> tutorials = new ArrayList<Example>();

      if (title == null)
        exampleRepository.findAll().forEach(tutorials::add);
      else
        exampleRepository.findByTitleContaining(title).forEach(tutorials::add);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tutorials/{id}")
  public ResponseEntity<Example> getTutorialById(@PathVariable("id") UUID id) {
    Optional<Example> tutorialData = exampleRepository.findById(id);

    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tutorials")
  public ResponseEntity<Example> createTutorial(@RequestBody Example tutorial) {
    try {
      Example _tutorial = exampleRepository.save(new Example(Uuids.timeBased(), tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tutorials/{id}")
  public ResponseEntity<Example> updateTutorial(@PathVariable("id") UUID id, @RequestBody Example tutorial) {
    Optional<Example> tutorialData = exampleRepository.findById(id);

    if (tutorialData.isPresent()) {
      Example _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(exampleRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") UUID id) {
    try {
      exampleRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tutorials")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      exampleRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tutorials/published")
  public ResponseEntity<List<Example>> findByPublished() {
    try {
      List<Example> tutorials = exampleRepository.findByPublished(true);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
