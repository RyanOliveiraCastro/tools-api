package com.tools.tools.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolsTags {

    @JsonIgnore
    @EmbeddedId
    private ToolsTagsPK id = new ToolsTagsPK();

    @Builder
    public ToolsTags(Tools tools, Tag tag) {
        this.id.setTools(tools);
        this.id.setTag(tag);
    }

    @JsonIgnore
    public Tools getTools() {
        return id.getTools();
    }

    public void setTools(Tools tools) {
        id.setTools(tools);
    }

    public Tag getTag() {
        return id.getTag();
    }

    public void setTag(Tag tag) {
        id.setTag(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToolsTags toolsTags = (ToolsTags) o;
        return id != null && Objects.equals(id, toolsTags.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
