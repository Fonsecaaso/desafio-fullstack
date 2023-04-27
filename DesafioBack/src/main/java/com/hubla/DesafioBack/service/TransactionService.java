package com.hubla.DesafioBack.service;

import com.hubla.DesafioBack.entity.*;
import com.hubla.DesafioBack.repository.CursoRepositoryI;
import com.hubla.DesafioBack.repository.TransactionRepository;
import com.hubla.DesafioBack.repository.UserRepositoryI;
import com.hubla.DesafioBack.repository.VendaRepositoryI;
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

@Service
public class TransactionService {
    @Autowired
    UserRepositoryI userRepository;
    @Autowired
    CursoRepositoryI cursoRepository;
    @Autowired
    VendaRepositoryI vendaRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public ResponseEntity<?> processTransactions(UploadRequest req) {
        List<Erro> erros = new ArrayList<>();
        List<Transaction> transactions = sanitizeInput(req, erros);


        processaVendasProdutor(transactions);
        processaVendasAfiliado(transactions);

        processaPagamentos(transactions);
        processaRecebimentos(transactions);

        if(erros.size()>0) return new ResponseEntity<>(erros, HttpStatus.PARTIAL_CONTENT);

        transactionRepository.saveAll(transactions);

        return new ResponseEntity<>(transactions, HttpStatus.ACCEPTED);
    }
    private void processaVendasProdutor(List<Transaction> transactions) {
        List<Transaction> vendaProdutor = transactions.stream()
                .filter(transaction -> transaction.getTipo().equals("Venda Produtor"))
                .toList();

        vendaProdutor.forEach(venda -> {
            User produtor = userRepository.findByName(venda.getVendedor());
            if(produtor == null)
                produtor = new User(venda.getVendedor(),venda.getValor(),true);

            Curso curso = Curso.builder().nome(venda.getProduto()).responsavel(produtor).build();
            saveVenda(Venda.builder().vendedor(produtor).curso(curso).valor(venda.getValor()).build(), produtor);
        });
    }

    private void processaVendasAfiliado(List<Transaction> transactions) {
        List<Transaction> vendaAfiliado = transactions.stream()
                .filter(transaction -> transaction.getTipo().equals("Venda Afiliado"))
                .toList();

        vendaAfiliado.forEach(venda -> {
            User produtor = cursoRepository.findByNomeCurso(venda.getProduto());
            User vendedor = userRepository.findByName(venda.getVendedor());
            if(vendedor == null){
                vendedor = new User(venda.getVendedor(),0.0,false);
                userRepository.save(vendedor, 0.0);
            }

            Curso curso = Curso.builder().nome(venda.getProduto()).responsavel(produtor).build();
            saveVenda(Venda.builder().vendedor(vendedor).curso(curso).valor(venda.getValor()).build(), produtor);
        });
    }

    private void processaPagamentos(List<Transaction> transactions) {
        List<Transaction> pagamentos = transactions.stream()
                .filter(transaction -> transaction.getTipo().equals("Comissão Paga"))
                .toList();

        pagamentos.forEach(pagamento -> {
            User produtor = userRepository.findByName(pagamento.getVendedor());
            userRepository.save(produtor, -1*pagamento.getValor());
        });
    }

    private void processaRecebimentos(List<Transaction> transactions) {
        List<Transaction> recebimentos = transactions.stream()
                .filter(transaction -> transaction.getTipo().equals("Comissão Recebida"))
                .toList();

        recebimentos.forEach(pagamento -> {
            User afiliado = userRepository.findByName(pagamento.getVendedor());
            userRepository.save(afiliado, pagamento.getValor());
        });
    }

    @Transactional
    private void saveVenda(Venda venda, User produtor){
        userRepository.save(produtor, venda.getValor());
        venda.setCurso(cursoRepository.save(venda.getCurso()));
        vendaRepository.save(venda);
    }

    private List<Transaction> sanitizeInput(UploadRequest req, List<Erro> erros){
        String[] registers = req.getFile().split("\n");

        List<Transaction> transactions = new ArrayList<>();

        for(int i=0; i<registers.length; i++){
            String register = registers[i];
            Transaction temp = Transaction.builder()
                    .tipo(checkType(register.charAt(0)))
                    .data(register.substring(1,26))
                    .produto(register.substring(26,56).trim())
                    .valor(Double.parseDouble(register.substring(56,66)))
                    .vendedor(register.substring(66))
                    .build();
            handleErrors(erros,temp,register,i);
            transactions.add(temp);
        }

        return transactions;
    }

    private void handleErrors(List<Erro> erros, Transaction temp, String register, int i) {
        if(temp.getTipo().equals("erro")){
            Erro erro = Erro.builder()
                    .linha(String.valueOf(i+1))
                    .message("campo tipo com input inválido")
                    .conteudoInput(register)
                    .build();
            erros.add(erro);
        }
        if(temp.getVendedor()==null || temp.getVendedor().trim().length() == 0){
            Erro erro = Erro.builder()
                    .linha(String.valueOf(i+1))
                    .message("campo vendedor não preenchido")
                    .conteudoInput(register)
                    .build();
            erros.add(erro);
        }
        if(temp.getProduto()==null || temp.getProduto().trim().length() == 0){
            Erro erro = Erro.builder()
                    .linha(String.valueOf(i+1))
                    .message("campo produto não preenchido")
                    .conteudoInput(register)
                    .build();
            erros.add(erro);
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setLenient(false);
        try {
            sdf.parse(temp.getData());
        } catch (ParseException e) {
            Erro erro = Erro.builder()
                    .linha(String.valueOf(i+1))
                    .message("campo data em formato errado")
                    .conteudoInput(register)
                    .build();
            erros.add(erro);
        }
    }

    private String checkType(char ch){
        return TransactionType.checkType(ch);
    }
}
