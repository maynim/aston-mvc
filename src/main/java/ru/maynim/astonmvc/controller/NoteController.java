package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.Note;
import ru.maynim.astonmvc.service.FileService;
import ru.maynim.astonmvc.service.NoteService;
import ru.maynim.astonmvc.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;

    @GetMapping()
    public String findAllNotes(Model model) {
        model.addAttribute("notes", noteService.findAllWithUsers());
        return "notes/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("note", noteService.findById(id));
        model.addAttribute("files", fileService.findAllByNoteId(id));
        return "/notes/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("note", noteService.findById(id));
        return "notes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("note") Note note) {
        noteService.update(id, note);
        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        noteService.deleteById(id);
        return "redirect:/notes";
    }

    @GetMapping("/new")
    public String newNote(@ModelAttribute("note") Note note, Model model) {
        model.addAttribute("users", userService.findAll());
        return "notes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("note")  Note note) {
        noteService.save(note);
        return "redirect:/notes";
    }
}
