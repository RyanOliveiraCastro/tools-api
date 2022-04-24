package com.tools.tools.service;

import com.tools.tools.dto.ToolsDTO;
import com.tools.tools.exceptions.NumeroMaximoTagsException;
import com.tools.tools.model.Tools;
import com.tools.tools.repositories.TagRepository;
import com.tools.tools.repositories.ToolsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TestToolsService {


    @InjectMocks
    ToolsService toolsService;

    @Mock
    ToolsRepository toolsRepository;

    @Mock
    TagRepository tagRepository;

    @Captor
    ArgumentCaptor<Tools> toolsArgumentCaptor;

    @Test
    public void createNewTools(){

        ToolsDTO toolsDTO = this.getToolsDTO(null, 1);

        when(this.tagRepository.existsById(any()))
                .thenReturn(true);

        when(this.toolsRepository.existsBytitle("TestTools".toLowerCase()))
                .thenReturn(false);

        when(this.toolsRepository.save(any(Tools.class)))
                .thenReturn(Tools.fromDTO(this.getToolsDTO(1L, 1)));

        ToolsDTO result = toolsService.persist(toolsDTO);

        verify(this.toolsRepository, times(1))
                        .save(toolsArgumentCaptor.capture());

        assertNotNull(toolsArgumentCaptor.getValue());
        assertEquals("TestTools", result.getTitle());
        assertEquals("LinkTestTools", result.getLink());
        assertEquals("Teste for tools", result.getDescription());
        assertEquals(1, result.getId());
        assertNotNull(result.getTags());
    }

    @Test
    public void createNewToolsMaxTags(){

        ToolsDTO toolsDTO = this.getToolsDTO(null, 9);

        NumeroMaximoTagsException exception = assertThrows(NumeroMaximoTagsException.class,
                ()-> toolsService.persist(toolsDTO));

        assertEquals("O número máximo de tags é 8.", exception.getMessage());
    }

    @Test
    public void createNewToolsTagNotExist(){

        ToolsDTO toolsDTO = this.getToolsDTO(null, 1);

        when(this.tagRepository.existsById(any()))
                .thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                ()-> toolsService.persist(toolsDTO));

        assertEquals("Tag tag 0 não existe.", exception.getMessage());
    }

    @Test
    public void createNewToolsToolsExist(){

        ToolsDTO toolsDTO = this.getToolsDTO(null, 1);

        when(this.tagRepository.existsById(any()))
                .thenReturn(true);

        when(this.toolsRepository.existsBytitle("TestTools".toLowerCase()))
                .thenReturn(true);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                ()-> toolsService.persist(toolsDTO));

        assertEquals("A Ferramenta TestTools já foi inserida.", exception.getMessage());
    }

    @Test
    public void deleteTools(){

        toolsService.delete(1L);

        verify(this.toolsRepository, times(1))
                .deleteById(1L);
    }


    //TODO DELETE

    private ToolsDTO getToolsDTO(final Long id, final int tagNumber) {
        ToolsDTO toolsDTO = new ToolsDTO();
        toolsDTO.setId(id);
        toolsDTO.setTitle("TestTools");
        toolsDTO.setDescription("Teste for tools");
        toolsDTO.setLink("LinkTestTools");

        List<String> tagList = new ArrayList<>();
        for (int i = 0; i < tagNumber; i++) {
            tagList.add("tag " + i);
        }

        toolsDTO.setTags(tagList);

        return toolsDTO;
    }

}
