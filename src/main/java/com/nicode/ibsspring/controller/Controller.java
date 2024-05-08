package com.nicode.ibsspring.controller;

import com.nicode.ibsspring.logic.Pet;
import com.nicode.ibsspring.logic.PetModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();

    @PostMapping(value = "/createPet", consumes = "application/json")
    public static String creatPet(@RequestBody Pet pet) {
        int id = petModel.add(pet);
        if (id == 1) {
            return "Поздравляем, вы создали своего первого питомца!";
        } else {
            return "Питомец № " + id + " успешно содан";
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
    public String deletePet(@RequestBody Map<String, String> map) {
        if (!petModel.getAll().containsKey(Integer.parseInt(map.get("id")))) {
            return "Питомца с id = " + map.get("id") + " не найдено";
        }
        petModel.getAll().remove(Integer.parseInt(map.get("id")));
        return "Питомец с id = " + map.get("id") + " был удалён";
    }

    @PutMapping(value = "/putPet", consumes = "application/json", produces = "application/json")
    public String putPet(@RequestBody Map<String, String> map) {
        if (!petModel.getAll().containsKey(Integer.parseInt(map.get("id")))) {
            return "Питомца с id = " + map.get("id") + " не найдено";
        }
        petModel.getAll().put(Integer.parseInt(map.get("id")), new Pet(map.get("name"), map.get("type"), Integer.parseInt(map.get("age"))));
        return "Питомец " + map.get("id") + " был успешно изменён";
    }
}
