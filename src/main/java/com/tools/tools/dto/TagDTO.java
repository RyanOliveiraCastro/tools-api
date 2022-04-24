package com.tools.tools.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {

    private List<String> tags;

}
