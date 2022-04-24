package com.tools.tools.resources;

import com.tools.tools.dto.TagDTO;
import com.tools.tools.model.Tag;
import com.tools.tools.repositories.TagRepository;
import com.tools.tools.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/tag")
public class TagResource {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Operation(summary = "Finds tags by Id", description = "Return a list of tags", tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Tag.class))))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public TagDTO list() {
        return Tag.toDTO(this.tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    @Operation(summary = "Add a new tag", tags = {"Tag"}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Tag object that needs to be added",
            content = @Content(schema = @Schema(implementation = Tag.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Tag.class))),
            @ApiResponse(responseCode = "400", description = "The 'example' tag already exists", content = @Content())
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity persist(@Valid @RequestBody Tag tag) {
        try {
            return ResponseEntity.created(URI.create("/tag")).body(this.tagService.persist(tag));
        } catch (EntityExistsException ex) {
            return ResponseEntity.badRequest().body((ex.getMessage()));
        }
    }

    @Operation(summary = "Delete tag by name", tags = {"Tag"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "s", description = "not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity remove(@PathVariable("name") String tagName) {
        try {
            this.tagService.delete(tagName);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("There is a tool with this tag.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
