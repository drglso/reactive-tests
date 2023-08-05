package edu.reactive.demo.domain.model.identification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Identification {

    private String documentType;
    private String documentNumber;

}

