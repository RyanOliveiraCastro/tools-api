package com.tools.tools.service;


import com.tools.tools.model.Tag;
import com.tools.tools.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tag persist(final Tag tag) {

        if(this.tagRepository.existsById(tag.getName())){
            throw new EntityExistsException("A tag " + tag.getName() + " já foi inserida.");
        }

        return this.tagRepository.save(tag);
    }

    public void delete(final String tagName) {
        if (!this.tagRepository.existsById(tagName)) {
            throw new EntityNotFoundException("A tag " + tagName + " não existe.");
        }
        this.tagRepository.deleteById(tagName);
    }

}
