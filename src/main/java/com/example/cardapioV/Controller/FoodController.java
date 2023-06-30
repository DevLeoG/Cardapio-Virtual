package com.example.cardapioV.Controller;

import com.example.cardapioV.Food.Food;
import com.example.cardapioV.Food.FoodRepository;
import com.example.cardapioV.Food.FoodRequestDTO;
import com.example.cardapioV.Food.FoodResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("food")
public class FoodController {
    @Autowired
    private FoodRepository repository;


    @PostMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food FoodData = new Food(data);
        repository.save(FoodData);
        return;
    }

    @GetMapping
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<FoodResponseDTO> getAll(){
        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;

    }
    @PutMapping
    @Transactional
    public ResponseEntity updateFood(@RequestBody FoodRequestDTO data){
        Optional<Food> optionalFood = repository.findById(data.id());
        if(optionalFood.isPresent()) {
            Food food = optionalFood.get();
            food.setPrice(data.price());
            food.setTitle(data.title());
            food.setImage(data.image());

            return ResponseEntity.ok(food);
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteFood(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.notFound().build();
    }




}
