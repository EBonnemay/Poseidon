package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;

import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller dedicated to BidList entities ; it handles user requests related to BidList :
 * displaying several pages (list, update form, add form), retrieving BidList data when necessary,
 * and handling crud requests by calling BidList Service.
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 *
 */
@Controller

public class BidListController {

    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired
    private BidListService bidListService;
    public BidListController(BidListService bidListService){
        this.bidListService = bidListService;
    }


//DISPLAY 'LIST OF BIDLISTS' PAGE
    @RequestMapping("/bidList/list")
    public String homeDisplayBidListsPage(Model model) {
            log.info("REQUEST /bidList/list");
            model.addAttribute("listOfBidList", bidListService.findAll());
            return "bidList/list";
    }
//DISPLAY 'ADD BIDLIST' FORM
    @GetMapping("/bidList/add")
    public String displayAddBidForm(BidList bid) {
        log.info("GET form /bidList/add");
        return "bidList/add";
    }
// CREATE NEW BIDLIST
    @PostMapping("/bidList/validate")
    public String validateBidList(@Valid BidList bidList, BindingResult result, Model model) {
        log.info("POST /bidList/validate");
        if(result.hasErrors()){
            log.error("BidList to create has errors");
            return ("bidList/add");
        }
        try {
            bidListService.validateNewBidList(bidList);
        } catch (Exception e ) {
            log.error("BidList could not be created");
            return "bidList/add";
        }
        return "redirect:/bidList/list";
    }
//DISPLAY 'UPDATE BIDLIST' FORM
    @GetMapping("/bidList/update/{id}")
    public String displayUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /bidList/update/{id} with id " + id);
            BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }catch(Exception e){
            log.error("bidList update form with id "+id+" could not be displayed");
            return "bidList/list";
        }
    }
//UPDATE BIDLIST
    @PostMapping("/bidList/update/{id}")// PUT MAPPING?
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList updatedBidListEntity,
                             BindingResult result, Model model) {
        log.info("POST /bidList/update/{id} with id " + id);
        try {
            if (result.hasErrors()) {
                log.error("bildlist to update has errors");
                throw new Exception();
            }
            BidList updatedAndSavedBidList = bidListService.updateBidList(id, updatedBidListEntity);
            model.addAttribute("listOfBidList", bidListService.findAll());

        }catch(Exception e){
            log.error("bidList with id "+ id+ " could not be updated");
            return "redirect:/bidList/update/"+id+"";
        }
        return "redirect:/bidList/list";
    }
//DELETE BIDLIST
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("GET /bidList/delete/{id} with id " + id);
            bidListService.deleteBidList(id);
            return "redirect:/bidList/list";
        }catch(Exception e){
            log.error("bidList with id "+ id+ " could not be deleted");
            return "bidList/list";
        }
    }
}
