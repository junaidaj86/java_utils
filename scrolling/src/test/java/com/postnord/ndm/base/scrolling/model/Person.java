package com.postnord.ndm.base.scrolling.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Person {
    private String name;
    private Integer age;
}
