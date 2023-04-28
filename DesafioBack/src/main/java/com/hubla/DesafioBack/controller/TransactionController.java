package com.hubla.DesafioBack.controller;

import com.hubla.DesafioBack.entity.Transaction;
import com.hubla.DesafioBack.entity.UploadRequest;
import com.hubla.DesafioBack.repository.TransactionRepository;
import com.hubla.DesafioBack.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Transaction Controller", description = "Controller that exposes the endpoints for logging and searching transactions")
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;

    @Operation(summary = "Upload finance transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "the entire input is valid and has been processed",content = @Content),
            @ApiResponse(responseCode = "206", description = "the valid part of the input is valid and has been processed",content = @Content),
            @ApiResponse(responseCode = "422", description = "none of the input is valid",content = @Content)})
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<?> postText(@RequestBody UploadRequest req){
        return transactionService.processTransactions(req);
    }

    @Operation(summary = "Download list of processed transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of processed transactions")})
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Transaction> getAll(){
        return transactionRepository.findAll();
    }
}
