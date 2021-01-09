package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private User user;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String home(Credential credential, Note note, Authentication authentication, Model model) {
        user = userService.getUserByUsername(authentication.getName());
        List<Note> notes = new ArrayList<>(noteService.getUserNotes(user.getUserid()));
        List<File> files = new ArrayList<>(fileService.getFilesByUser(user.getUserid()));
        List<Credential> credentials = credentialService.getCredentialsByUser(user.getUserid());
        model.addAttribute("userNotes", notes);
        model.addAttribute("userFiles", files);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("/files")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) {
        if (user != null) {
            try {
                int fileId = fileService.saveFile(file, user.getUserid());
                model.addAttribute("isSuccess", fileId > 0);
            } catch (IOException exception) {
                String errorPrompt = "Could not upload file " + file.getOriginalFilename() + "Reason: "+exception.getMessage();
                model.addAttribute("isSuccess", false);
                model.addAttribute("errorMessage", errorPrompt);
            }
        } else {
            model.addAttribute("isSuccess", false);
            model.addAttribute("errorMessage", "You are not logged in");
        }
        return "result";
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() +"\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getFileData());
    }

    @GetMapping("/files/{fileId}/delete")
    public String deleteFile(@PathVariable int fileId, Model model) {
        int count = fileService.deleteFile(fileId);
        model.addAttribute("isSuccess", count > 0);
        model.addAttribute("errorMessage", "Could not delete file. Please try again");
        return "result";
    }
}
