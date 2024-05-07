package com.nicode.ibsspring.controller;

import com.nicode.ibsspring.logic.Pet;
import com.nicode.ibsspring.logic.PetModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public static String creatPet(@RequestBody Pet pet) {
        petModel.add(pet, newId.getAndIncrement());
        if (newId.get() == 1) {
            return "Поздравляем, вы создали своего первого питомца!";
        } else {
            return "Питомец № " + newId.get() + " успешно содан";
        }
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> map) {
        return petModel.getFromList(map.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> map) {
        petModel.getAll().remove(map.get("id"));
        return "Питомец" + map.get("id") + " был удалён";
    }

    @PutMapping(value = "/putPet", consumes = "application/json", produces = "application/json")
    public String putPet(@RequestBody Map<String, String> map) {
        if (!petModel.getAll().containsKey(Integer.parseInt(map.get("id")))) {
            return "Питомца с id= " + map.get("id") + "не найдено";
        }
        petModel.getAll().put(Integer.parseInt(map.get("id")), new Pet(map.get("name"), map.get("type"), Integer.parseInt(map.get("age"))));
        return "Питомец" + map.get("id") + " был успешно изменён";
    }
}
