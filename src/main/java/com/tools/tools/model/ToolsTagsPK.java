package com.tools.tools.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode()
public class ToolsTagsPK implements Serializable {

    private static final long serialVersionUID = 6735299601229224093L;

    @ManyToOne
    @JoinColumn(name = "tools_id")
    private Tools tools;

    @ManyToOne
    @JoinColumn (name = "tag_name")
    private Tag tag;

}
