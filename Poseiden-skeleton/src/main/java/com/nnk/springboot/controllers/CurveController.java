package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * This class is a controller dedicated to Curve entities ; it handles user requests related to Curve :
 * displaying several pages (list, update form, add form), retrieving Curve data when necessary,
 * and handling crud requests by calling Curve Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */

@Controller
public class CurveController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private CurvePointService curvePointService;
    public CurveController(CurvePointService curvePointService){
        this.curvePointService = curvePointService;
    }
//DISPLAY LIST OF CURVEPOINTS PAGE
    @RequestMapping("/curvePoint/list")
    public String homeDisplayCurvePointsPage(Model model) {
        log.info("REQUEST /curvePoint/list");
        model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        return "curvePoint/list";
    }
//DISPLAY ADD CURVEPOINT FORM
    @GetMapping("/curvePoint/add")
    public String displayAddCurvePointForm(CurvePoint curvePoint) {
        log.info("GET form /curvePoint/add");
        return "curvePoint/add";
    }


//CREATE NEW CURVEPOINT
    @PostMapping("/curvePoint/validate")
    public String validateCurvePoint(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("POST /curvePoint/validate");
        if(result.hasErrors()){
            log.error("curvePoint to create has errors");
            return "curvePoint/add";
        }
        try{
            curvePointService.validateNewCurvePoint(curvePoint);
        }catch(Exception e){
            log.error("curvepoint could not be create");
            return "curvePoint/add";
        }
        return "redirect:/curvePoint/list";

    }
//DISPLAY CURVEPOINT UPDATE FORM
    @GetMapping("/curvePoint/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /curvePoint/update/{id} with id " + id);
            CurvePoint curvePoint = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }catch(Exception e){
            log.error("curvePoint form with id "+id+" could not be displayed");
            return "curvePoint/list";
        }
    }
//UPDATE CURVEPOINT
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint updateCurvePointEntity,BindingResult result, Model model) {
        log.info("POST /curvePoint/update/{id} with id " + id);

        try {
            if (result.hasErrors()) {
                log.error("curvepoint to update has errors");
                throw new Exception();
            }
            CurvePoint updatedAndSavedCurvePoint = curvePointService.updateCurvePoint(id, updateCurvePointEntity);
            model.addAttribute("listOfCurvepoints", curvePointService.findAll());
        } catch (Exception e) {
            log.error("curvepoint with id " + id + "could not be update");
            return "redirect:/curvePoint/update/"+id+"";
        }
        return "redirect:/curvePoint/list";
    }
//DELETE CURVEPOINT
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /curvePoint/delete/{id} with id " + id);
            curvePointService.deleteCurvePoint(id);
            //model.addAttribute("listOfCurvepoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }catch(Exception e){
            log.error("curvepoint with id "+id+ " could not be deleted");
            return "curvePoint/list";
        }
    }
}
