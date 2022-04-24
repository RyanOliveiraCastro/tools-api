package com.tools.tools.model;

import com.tools.tools.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    public static TagDTO toDTO(final List<Tag> tagList){
        return new TagDTO(tagList.stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return name != null && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
