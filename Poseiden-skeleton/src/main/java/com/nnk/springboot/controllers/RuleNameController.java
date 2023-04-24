package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * This class is a controller dedicated to RuleName entities ; it handles user requests related to RuleName :
 * displaying several pages (list, update form, add form), retrieving RuleName data when necessary,
 * and handling crud requests by calling RuleName Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */

@Controller
public class RuleNameController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private RuleNameService ruleNameService;
    public RuleNameController(RuleNameService ruleNameService){
        this.ruleNameService = ruleNameService;
    }
//DISPLAY LIST OF RULENAMES PAGE
    @RequestMapping("/ruleName/list")
    public String homeDisplayRuleNamesList(Model model) {
        log.info("REQUEST /ruleName/list");
        model.addAttribute("listOfRulenames", ruleNameService.findAll());
        return "ruleName/list";
    }
//DISPLAY ADD RULENAME FORM
    @GetMapping("/ruleName/add")
    public String displayAddRuleForm(RuleName ruleName) {
        log.info("GET form /ruleName/add");
        return "ruleName/add";
    }


//CREATE NEW RULENAME
    @PostMapping("/ruleName/validate")
    public String validateRuleName(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("POST /ruleName/validate");
        if(result.hasErrors()){
            log.error("ruleName to create has errors");
            return "ruleName/add";
        }
        try{
            ruleNameService.validateNewRuleName(ruleName);
        }catch(Exception e){
            log.error("ruleName could not be created");
            return "ruleName/add";
        }
    return "redirect:/ruleName/list";
    }
//DISPLAY UPDATE RULENAME FORM
    @GetMapping("/ruleName/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /ruleName/update/{id} with id "+ id);
            RuleName ruleName = ruleNameService.getRuleNameById(id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }catch(Exception e){
            log.error("ruleName update form with id "+id+" could not be displayed");
            return "ruleName/list";
        }

    }
//UPDATE RULENAME
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName updatedRuleNameEntity,
                             BindingResult result, Model model) {
        log.info("POST /ruleName/update/{id} with id "+id);
        try{
            if (result.hasErrors()) {
                log.error("ruleName to update has errors");
                throw new Exception();
            }
            RuleName updatedAndSavedRuleName = ruleNameService.updateRuleName(id, updatedRuleNameEntity );
            model.addAttribute("listOfRulenames", ruleNameService.findAll());
        }catch(Exception e){
            log.error("ruleName with id "+ id+ " could not be updated");
            return "redirect:/ruleName/update/"+id+"";
        }
        return "redirect:/ruleName/list";

    }
//DELETE RULENAME
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /ruleName/delete/{id}");
            ruleNameService.deleteRuleName(id);
            return "redirect:/ruleName/list";
        }catch(Exception e){
            log.error("ruleName with id "+id+ " could not be deleted");
            return "ruleName/list";
        }

    }
}
