package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.File;
import ru.maynim.astonmvc.repository.FileRepository;
import ru.maynim.astonmvc.repository.NoteRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileRepository fileRepository;
    private final NoteRepository noteRepository;


    @GetMapping()
    public String findAllUsers(Model model) {
        model.addAttribute("files", fileRepository.findAllWithNotes());
        return "files/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("file", fileRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty file");
        }));
        return "/files/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("file", fileRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Empty file");
        }));
        return "files/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("file") File file) {
        fileRepository.update(id, file);
        return "redirect:/files";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        fileRepository.deleteById(id);
        return "redirect:/files";
    }

    @GetMapping("/new")
    public String newNote(@ModelAttribute("file") File file, Model model) {
        model.addAttribute("notes", noteRepository.findAllWithUsers());
        return "files/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("file")  File file) {
        fileRepository.save(file);
        return "redirect:/files";
    }
}
