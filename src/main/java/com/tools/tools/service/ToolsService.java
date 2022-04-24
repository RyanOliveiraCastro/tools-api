package com.tools.tools.service;

import com.tools.tools.dto.ToolsDTO;
import com.tools.tools.exceptions.NumeroMaximoTagsException;
import com.tools.tools.model.Tools;
import com.tools.tools.repositories.TagRepository;
import com.tools.tools.repositories.ToolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ToolsService {

    @Autowired
    ToolsRepository toolsRepository;

    @Autowired
    TagRepository tagRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ToolsDTO persist(final ToolsDTO toolsDTO) {

        this.checkTagList(toolsDTO.getTags());
        this.searchBytitle(toolsDTO.getTitle());
        toolsDTO.setTitle(toolsDTO.getTitle().toLowerCase());

        Tools persistTools = this.toolsRepository.save(Tools.fromDTO(toolsDTO));

        return Tools.toDTO(persistTools);
    }

    public void delete(final Long toolsId) {
        this.toolsRepository.deleteById(toolsId);
    }

    private void checkTagList(final List<String> tagList) {
        if (tagList.size() > 8) {
            throw new NumeroMaximoTagsException("O número máximo de tags é 8.");
        }

        tagList.forEach(tagName -> {
            if (!this.tagRepository.existsById(tagName)) {
                throw new EntityNotFoundException("Tag " +  tagName + " não existe.");
            }
        });
    }

    private void searchBytitle(final String title) {
        if (this.toolsRepository.existsBytitle(title.toLowerCase())) {
            throw new EntityExistsException("A Ferramenta " + title + " já foi inserida.");
        }
    }

}
