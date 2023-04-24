package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * this class is a controller dedicated to Rating entities ; it handles user requests related to Rating :
 * displaying several pages (list, update form, add form), retrieving Rating data when necessary,
 * and handling crud requests by calling Rating Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */

@Controller
public class RatingController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private RatingService ratingService;
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @RequestMapping("/rating/list")
    public String homeDisplayRatingList(Model model) {
        log.info("REQUEST /rating/list");
        model.addAttribute("listOfRatings", ratingService.findAll());
        return "rating/list";
    }
//DISPLAY ADD RATING FORM
    @GetMapping("/rating/add")
    public String displayAddRatingForm(Rating rating) {
        log.info("GET form /rating/add");
        return "rating/add";
    }
//CREATE NEW RATING
    @PostMapping("/rating/validate")
    public String validateRating(@Valid Rating rating, BindingResult result, Model model)  {
        log.info("POST /rating/validate");
        if (result.hasErrors()) {
            log.error("rating to add has errors");
            return "rating/add";
        }
        try{
            ratingService.validateNewRating(rating);
        }catch(Exception e){
            log.error("rating could not be created" );
            return "rating/add";
        }
        return "redirect:/rating/list";
    }
//DISPLAY UPDATE RATING FORM
    @GetMapping("/rating/update/{id}")
    public String displayUpdateRatingForm(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /rating/update/{id} with id " + id);
            Rating rating = ratingService.getRatingById(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        }catch(Exception e){
            log.error("rating update form with id "+id+" could not be displayed");
            return "rating/list";
        }

    }
//UPDATE RATING
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating updatedRatingEntity,
                             BindingResult result, Model model) {
        log.info("POST /rating/update/{id} with id " + id);
        try {
            if (result.hasErrors()) {
                log.error("rating to update has errors");
                throw new Exception();
            }
            Rating updatedAndSavedRating = ratingService.updateRating(id, updatedRatingEntity);
            model.addAttribute("listOfRatings", ratingService.findAll());
        } catch (Exception e) {
            log.error("rating with id " + id + " could not be update");
            return "redirect:/rating/update/"+id+"";
        }
        return "redirect:/rating/list";
    }
//DELETE RATING
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /rating/delete/{id} with id " + id);
            ratingService.deleteRating(id);
            return "redirect:/rating/list";
        }catch(Exception e){
            log.error("rating with id "+id+" could not be deleted");
            return "rating/list";
        }
    }
}
