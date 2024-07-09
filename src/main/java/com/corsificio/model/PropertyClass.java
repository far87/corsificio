package com.corsificio.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("prop.class")
public record PropertyClass(String nome, String ambiente) {

}
