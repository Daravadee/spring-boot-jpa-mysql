package com.example.easydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.easydemo.exception.ResourceNotFoundException;
import com.example.easydemo.model.Note;
import com.example.easydemo.repository.NoteRepository;

import java.util.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	
	// Get All Notes
	@GetMapping("/notes")
	public @ResponseBody List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
    // Create a new Note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	
    // Get a Single Note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value="id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(
					()->new ResourceNotFoundException("Note", "id", noteId)
				);
	}

	// Update a Note
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value="id") Long noteId,@Valid @RequestBody Note noteDetails) {
		
		Note note = noteRepository.findById(noteId).orElseThrow(
						()-> new ResourceNotFoundException("Note", "id", noteId)
					);
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		
		Note updateNote = noteRepository.save(note);
		return updateNote;
	}
    
	// Delete a Note
	public ResponseEntity<?> deleteNote(@PathVariable(value="id") Long noteId){
		Note note = noteRepository.findById(noteId).orElseThrow(
						()-> new ResourceNotFoundException("Note", "id", noteId)
					);
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}
}
