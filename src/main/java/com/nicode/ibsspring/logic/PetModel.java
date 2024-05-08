package com.nicode.ibsspring.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PetModel implements Serializable {
    private static final PetModel instance = new PetModel();
    private final Map<Integer, Pet> model;

    private static final AtomicInteger newId = new AtomicInteger(1);

    public PetModel() {
        model = new HashMap<Integer, Pet>();
    }

    public static PetModel getInstance() {
        return instance;
    }

    public int add(Pet pet) {
        int id= newId.getAndIncrement();
        model.put(id, pet);
        return id;
    }

    public Pet getFromList(int id) {
        return model.get(id);
    }

    public Map<Integer, Pet> getAll() {
        return model;
    }
}
