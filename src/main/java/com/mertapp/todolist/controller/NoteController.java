package com.mertapp.todolist.controller;

import com.mertapp.todolist.model.Note;
import com.mertapp.todolist.payload.request.NoteRequest;
import com.mertapp.todolist.payload.response.NoteResponse;
import com.mertapp.todolist.repository.NoteRepository;
import com.mertapp.todolist.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/note")
public class NoteController {
    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/list")
    public List<Note> getAllItems() {
        return noteRepository.findNotesByUserid(getUserDetailsFromSession().getId());
    }

    @PostMapping("/updatenote")
    @Transactional
    public void updateNote(@Valid @RequestBody NoteRequest request) {
        noteRepository.updateNoteWithId(request.getText(), request.getStatus(), request.getId(), getUserDetailsFromSession().getId());
    }

    @PostMapping("/insertnote")
    public NoteResponse insertNote(@Valid @RequestBody NoteRequest request) {
        Note note = new Note();
        note.setText(request.getText());
        note.setUserid(getUserDetailsFromSession().getId());
        note.setStatus("O");
        note.setInsert_date(new Date());
        note.setUpdate_date(new Date());
        Note responseNote = noteRepository.save(note);
        return new NoteResponse(responseNote.getId(), responseNote.getUserid(), responseNote.getText(), responseNote.getStatus(), responseNote.getInsert_date(), responseNote.getUpdate_date());
    }

    @PostMapping("/deletenote")
    @Transactional
    public void deleteNote(@RequestBody NoteRequest request) {
        noteRepository.deleteNoteWithId(request.getId(), getUserDetailsFromSession().getId());
    }

    private UserDetailsImpl getUserDetailsFromSession() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
