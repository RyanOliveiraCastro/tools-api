package com.tools.tools.resources;


import com.tools.tools.dto.ToolsDTO;
import com.tools.tools.exceptions.NumeroMaximoTagsException;
import com.tools.tools.model.Tools;
import com.tools.tools.repositories.ToolsRepository;
import com.tools.tools.service.ToolsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tools")
public class ToolsResource {

    @Autowired
    private ToolsRepository toolsRepository;

    @Autowired
    private ToolsService toolsService;

    @Operation(summary = "Finds tools by Id", description = "Return a list of tools", tags = { "Tools" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ToolsDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ToolsDTO> list() {
        return this.toolsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(Tools::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Add a new tool", tags = { "Tools" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Tools object that needs to be added",
            content = @Content(schema = @Schema(implementation = ToolsDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ToolsDTO.class))),
            @ApiResponse(responseCode = "400", description = "The max number of tag is eight (8)", content = @Content()),
            @ApiResponse(responseCode = "400", description = "The 'example' already exists", content = @Content()),
            @ApiResponse(responseCode = "404", description = "The 'example' tag not found", content = @Content())
    })
    @PostMapping
    public ResponseEntity persist(@Valid @RequestBody ToolsDTO toolsDTO) {
        try {
            return ResponseEntity.created(URI.create("/tools")).body(this.toolsService.persist(toolsDTO));
        } catch (NumeroMaximoTagsException | EntityExistsException | EntityNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Delete tools by Id", tags = { "Tools"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable(name ="id") Long toolsId) {
        try {
            this.toolsService.delete(toolsId);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

}
