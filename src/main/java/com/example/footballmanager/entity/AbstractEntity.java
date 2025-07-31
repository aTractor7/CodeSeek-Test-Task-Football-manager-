package com.example.footballmanager.entity;

public interface AbstractEntity {
    Long getId();

    int hashCode();

    boolean equals(Object obj);
}
