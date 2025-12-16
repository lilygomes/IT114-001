package com.example.project1;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemList extends LinkedList<Employee> {
    @Inject
    ItemList() {

    }
}
