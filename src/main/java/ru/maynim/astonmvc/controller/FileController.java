package ru.maynim.astonmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maynim.astonmvc.entity.File;
import ru.maynim.astonmvc.service.FileService;
import ru.maynim.astonmvc.service.NoteService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final NoteService noteService;


    @GetMapping()
    public String findAllFiles(Model model) {
        model.addAttribute("files", fileService.findAllWithNotes());
        return "files/findAll";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        model.addAttribute("file", fileService.findById(id));
        return "/files/info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("file", fileService.findById(id));
        return "files/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("file") File file) {
        fileService.update(id, file);
        return "redirect:/files";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        fileService.deleteById(id);
        return "redirect:/files";
    }

    @GetMapping("/new")
    public String newNote(@ModelAttribute("file") File file, Model model) {
        model.addAttribute("notes", noteService.findAllWithUsers());
        return "files/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("file")  File file) {
        fileService.save(file);
        return "redirect:/files";
    }
}
