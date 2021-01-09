package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * Create or Update a new note
     * @param note - Note to create or update
     * @param authentication - Authenticated user
     */
    @PostMapping
    public String createOrUpdateNote(Note note, Authentication authentication, Model model) {
        Integer result;
        String username = authentication.getName();
        int userId = userService.getUserByUsername(username).getUserid();
        note.setUserid(userId);

        if(note.getNoteid().intValue() > 0){
            //note already exists so update operation
            try {
                result = noteService.updateNote(note);
                model.addAttribute("isSuccess", result > 0);
                model.addAttribute("errorMessage", "Something went wrong");
            } catch (Exception e) {
                model.addAttribute("isSuccess", false);
                model.addAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
            }
        }else{
            try {
                //new note. save operation
                result = noteService.saveNote(note);
                model.addAttribute("isSuccess", result > 0);
                model.addAttribute("errorMessage", "Something went wrong");
            }  catch (Exception e) {
                model.addAttribute("isSuccess", false);
                model.addAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
            }
        }
        return "result";
    }

    @GetMapping("/{noteId}/delete")
    public String deleteNote(@PathVariable int noteId, Model model){
        int count = noteService.deleteNote(noteId);
        model.addAttribute("isSuccess", count > 0);
        model.addAttribute("errorMessage", "Could not delete Note. Please try again");
        return "result";
    }
}
