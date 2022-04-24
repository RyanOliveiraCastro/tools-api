package com.tools.tools.model;

import com.tools.tools.dto.ToolsDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Tools implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O titulo deve ser Preenchido")
    private String title;

    @NotNull(message = "O link deve ser Preenchido")
    private String link;

    @NotNull(message = "A descricao deve ser Preenchido")
    @Column(length = 256)
    private String description;

    @OneToMany(mappedBy = "id.tools", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ToolsTags> ToolsTags = new ArrayList<>();

    public static Tools fromDTO(final ToolsDTO toolsDTO){
        Tools tools = new Tools();
        tools.setId(toolsDTO.getId());
        tools.setTitle(toolsDTO.getTitle());
        tools.setLink(toolsDTO.getLink());
        tools.setDescription(toolsDTO.getDescription());
        tools.setToolsTags(toolsDTO.getTags().stream()
                .map(tag -> new ToolsTags(tools, new Tag(tag)))
                .collect(Collectors.toList()));
        return tools;
    }

    public static ToolsDTO toDTO(final Tools tools){
        ToolsDTO toolsDTO = new ToolsDTO();
        toolsDTO.setId(tools.getId());
        toolsDTO.setTitle(tools.getTitle());
        toolsDTO.setLink(tools.getLink());
        toolsDTO.setDescription(tools.getDescription());
        toolsDTO.setTags(tools.getToolsTags().stream()
                .map(toolsTags -> toolsTags.getTag().getName())
                .collect(Collectors.toList()));
        return toolsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tools tools = (Tools) o;
        return id != null && Objects.equals(id, tools.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
