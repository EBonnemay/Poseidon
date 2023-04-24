package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * This class handles Trade CRUD operations by calling Trade Repository : save, read (get Trade by Id , find All Trades), update, delete.
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */

@Service
public class TradeService{
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    private TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository){
        this. tradeRepository= tradeRepository;
    }

    public List<Trade> findAll(){
        return tradeRepository.findAll();
    }



    public Trade getTradeById(Integer i) throws Exception{

        Optional<Trade> opt= tradeRepository.findById(i);
        return opt.get();

    }

    //CREATE NEW BIDLIST
    public Trade validateNewTrade(Trade trade) throws Exception{
        return tradeRepository.save(trade);
    }
    //UPDATE BIDLIST
    public Trade updateTrade(Integer id, Trade updatedTradeEntity) throws Exception{
        Optional<Trade> opt = tradeRepository.findById(id);
        Trade formerTrade = opt.get();
        formerTrade.setAccount(updatedTradeEntity.getAccount());
        formerTrade.setBuy_quantity(updatedTradeEntity.getBuy_quantity());
        formerTrade.setType(updatedTradeEntity.getType());
        return tradeRepository.save(formerTrade);

    }


    //DELETE BIDLIST
    public void deleteTrade(Integer id) throws Exception{
        Optional<Trade> opt = tradeRepository.findById(id);
        Trade tradeToDelete = opt.get();
        tradeRepository.delete(tradeToDelete);
    }

}


