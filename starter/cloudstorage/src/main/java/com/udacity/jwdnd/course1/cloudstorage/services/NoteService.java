package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer saveNote(Note note){
        return noteMapper.createNote(note);
    }

    public List<Note> getUserNotes(Integer userId){
        return noteMapper.getNotesByUser(userId);
    }

    public int deleteNote(int noteId){
        return noteMapper.deleteNote(noteId);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }
}
