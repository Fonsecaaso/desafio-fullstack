package com.hubla.DesafioBack.controller;

import com.hubla.DesafioBack.entity.Transaction;
import com.hubla.DesafioBack.entity.UploadRequest;
import com.hubla.DesafioBack.repository.TransactionRepository;
import com.hubla.DesafioBack.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<?> postText(@RequestBody UploadRequest req){
        return transactionService.processTransactions(req);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Transaction> getAll(){
        return transactionRepository.findAll();
    }
}
