package com.bf.facade.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Client {
    String rut;
    String name;
    Integer nationality;
    String birthDate;
}
