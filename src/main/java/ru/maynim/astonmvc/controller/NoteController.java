package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.model.Note;
import ru.maynim.astonmvc.repository.FileRepository;
import ru.maynim.astonmvc.repository.NoteRepository;
import ru.maynim.astonmvc.repository.UserRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("notes", noteRepository.findAll());
        return "notes/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("note", noteRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty note");
        }));
        model.addAttribute("files", fileRepository.findAllByNoteId(id));
        return "/notes/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("note", noteRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty note");
        }));
        return "notes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("note") Note note) {
        noteRepository.update(id, note);
        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        noteRepository.deleteById(id);
        return "redirect:/notes";
    }

    @GetMapping("/new")
    public String newNote(@ModelAttribute("note") Note note, Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "notes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("note")  Note note) {
        noteRepository.save(note);
        return "redirect:/notes";
    }
}
