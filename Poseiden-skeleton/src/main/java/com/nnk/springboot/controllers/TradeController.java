package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
 * This class is a controller dedicated to Trade entities ; it handles user requests related to Trade :
 * displaying several pages (list, update form, add form), retrieving Trade data when necessary,
 * and handling crud requests by calling Trade Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */

@Controller
public class TradeController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private TradeService tradeService;

    public TradeController(TradeService tradeService){
        this.tradeService=tradeService;
    }
//DISPLAY LIST OF TRADES PAGE
    @RequestMapping("/trade/list")
    public String homeDisplayTradesList(Model model) {
        log.info ("REQUEST /trade/list");
        model.addAttribute("listOfTrades", tradeService.findAll());
        return "trade/list";
    }
//DISPLAY ADD TRADE FORM
    @GetMapping("/trade/add")
    public String displayAddUserForm(Trade trade) {
        log.info("GET form /trade/add");
        return "trade/add";
    }
//CREATE NEW TRADE
    @PostMapping("/trade/validate")
    public String validateTrade(@Valid Trade trade, BindingResult result, Model model) {
        log.info("POST /trade/validate");
        if (result.hasErrors()) {
            log.error("trade to create has errors");
            return "trade/add";

        }
        try {
            tradeService.validateNewTrade(trade);
        } catch (Exception e){
            log.error("registration was not possible");
            return "trade/add";
        }
        return "redirect:/trade/list";
    }
//DISPLAY TRADE UPDATE FORM
    @GetMapping("/trade/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /trade/update/{id} with id "+ id);
            Trade trade = tradeService.getTradeById(id);
            model.addAttribute("trade", trade);
            return "trade/update";
        }catch(Exception e){
            log.error("trade update form with id "+id+" could not be displayed");
            return "trade/list";
        }

    }
//UPDATE TRADE
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade updatedTradeEntity,
                             BindingResult result, Model model) {
        log.info("POST /trade/update{id} with id "+ id);

        try {
            if (result.hasErrors()) {
                log.error("trade to update has errors");
                throw new Exception();
            }
            Trade updatedAndSavedTrade = tradeService.updateTrade(id, updatedTradeEntity);
            model.addAttribute("listOfTrades", tradeService.findAll());
        }catch(Exception e){
            log.error("trade with id "+id+" could not be updated");
            return "redirect:/trade/update/"+id+"";
        }
        return "redirect:/trade/list";
    }
//DELETE TRADE
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /trade/delete/{id} with id " + id);
            tradeService.deleteTrade(id);
            return "redirect:/trade/list";
        }catch(Exception e){
            log.error("trade with id "+ id + "could not be deleted");
            return ("trade/list");
        }
    }
}
