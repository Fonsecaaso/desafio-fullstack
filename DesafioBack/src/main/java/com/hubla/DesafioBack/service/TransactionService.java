package com.hubla.DesafioBack.service;

import com.hubla.DesafioBack.entity.*;
import com.hubla.DesafioBack.entity.Error;
import com.hubla.DesafioBack.repository.*;
import com.hubla.DesafioBack.utils.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public ResponseEntity<?> processTransactions(UploadRequest request) {
        List<Error> errors = new ArrayList<>();
        List<Transaction> transactions = sanitizeInput(request, errors);

        int success = 0;
        success += processSales(transactions);
        success += processPayments(transactions);

        transactionRepository.saveAll(transactions);

        UploadResponse response = new UploadResponse(errors,success);

        if(success==0) return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        if(errors.size()>0) return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private int processSales(List<Transaction> transactions) {
        List<Transaction> sales = transactions.stream()
                .filter(transaction -> transaction.getType().startsWith("Venda"))
                .toList();

        sales.forEach(sale -> {
            UserEntity productOwner = sale.getType().equals("Venda Produtor")
                    ? userRepository.findByName(sale.getSellerName())
                        .orElseGet(() -> userRepository.save(new UserEntity(sale.getSellerName(), sale.getValue(), true)))
                    : productRepository.findUserByProduct(sale.getProduct())
                        .orElseGet(() -> userRepository.save(new UserEntity(sale.getSellerName(), sale.getValue(), true)));

            UserEntity seller = userRepository.findByName(sale.getSellerName())
                    .orElseGet(() -> {
                        UserEntity newSeller = new UserEntity(sale.getSellerName(), 0.0, false);
                        userService.updateBalance(newSeller, 0.0);
                        return newSeller;
                    });

            Product product = productRepository.existsByName(sale.getProduct())
                    ? productRepository.findByName(sale.getProduct())
                    : productRepository.save(
                    Product.builder()
                            .name(sale.getProduct())
                            .owner(productOwner)
                            .build()
            );

            saveSell(Sale.builder().seller(seller).product(product).value(sale.getValue()).build(), productOwner);
        });

        return sales.size();
    }


    private int processPayments(List<Transaction> transactions) {
        List<Transaction> payments = transactions.stream()
                .filter(transaction -> transaction.getType().startsWith("Comissão"))
                .toList();

        payments.forEach(payment -> {
            Optional<UserEntity> user = userRepository.findByName(payment.getSellerName());
            if(payment.getType().equals("Commissão Paga"))
                userService.updateBalance(user.get(), -1*payment.getValue());
            if(payment.getType().equals("Commissão Recebida"))
                userService.updateBalance(user.get(), -payment.getValue());
        });

        return payments.size();
    }

    @Transactional
    private void saveSell(Sale sale, UserEntity productOwner){
        userService.updateBalance(productOwner, sale.getValue());
        saleRepository.save(sale);
    }

    private List<Transaction> sanitizeInput(UploadRequest req, List<Error> errors){
        String[] registers = req.getFile().split("\n");

        List<Transaction> transactions = new ArrayList<>();

        for(int i=0; i<registers.length; i++){
            String register = registers[i];
            Transaction temp = Transaction.builder()
                    .type(checkType(register.charAt(0)))
                    .date(register.substring(1,26))
                    .product(register.substring(26,56).trim())
                    .value(Double.parseDouble(register.substring(56,66)))
                    .sellerName(register.substring(66))
                    .build();
            handleErrors(errors,temp,register,i);
            transactions.add(temp);
        }

        return transactions;
    }

    private void handleErrors(List<Error> errors, Transaction temp, String register, int i) {
        if(temp.getType().equals("erro")){
            Error error = Error.builder()
                    .line(String.valueOf(i+1))
                    .message("campo tipo inválido")
                    .lineContent(register)
                    .build();
            errors.add(error);
        }
        if(temp.getSellerName()==null || temp.getSellerName().trim().length() == 0){
            temp.setType("erro");
            Error error = Error.builder()
                    .line(String.valueOf(i+1))
                    .message("campo vendedor não preenchido")
                    .lineContent(register)
                    .build();
            errors.add(error);
        }
        if(temp.getProduct()==null || temp.getProduct().trim().length() == 0){
            temp.setType("erro");
            Error error = Error.builder()
                    .line(String.valueOf(i+1))
                    .message("campo produto não preenchido")
                    .lineContent(register)
                    .build();
            errors.add(error);
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setLenient(false);
        try {
            sdf.parse(temp.getDate());
        } catch (ParseException e) {
            temp.setType("erro");
            Error error = Error.builder()
                    .line(String.valueOf(i+1))
                    .message("campo data em formato errado")
                    .lineContent(register)
                    .build();
            errors.add(error);
        }
    }

    private String checkType(char ch){
        return TransactionType.checkType(ch);
    }
}
