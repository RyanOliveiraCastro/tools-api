package com.tools.tools.service;

import com.tools.tools.dto.ToolsDTO;
import com.tools.tools.model.Tag;
import com.tools.tools.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TestTagService {

    @InjectMocks
    TagService tagService;

    @Mock
    TagRepository tagRepository;

    @Test
    public void persistNewTag(){

        Tag tag = new Tag("new Tag");

        when(this.tagRepository.existsById(tag.getName()))
                .thenReturn(false);

        this.tagService.persist(tag);

        Mockito.verify(this.tagRepository, Mockito.times(1))
                .save(tag);
    }

    @Test
    public void persistNewTagExists(){

        when(this.tagRepository.existsById("new Tag"))
                .thenReturn(true);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                ()-> tagService.persist(new Tag("new Tag")));

        assertEquals("A tag new Tag já foi inserida.", exception.getMessage());
    }


    @Test
    public void deleteTag(){
        tagService.delete("testTag");
        Mockito.verify(this.tagRepository, Mockito.times(1))
                .deleteById("testTag");
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
