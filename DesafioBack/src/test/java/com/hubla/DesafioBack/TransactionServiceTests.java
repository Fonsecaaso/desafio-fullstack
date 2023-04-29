package com.hubla.DesafioBack;

import com.hubla.DesafioBack.entity.Error;
import com.hubla.DesafioBack.entity.UploadRequest;
import com.hubla.DesafioBack.entity.UploadResponse;
import com.hubla.DesafioBack.entity.UserEntity;
import com.hubla.DesafioBack.repository.*;
import com.hubla.DesafioBack.service.TransactionService;
import com.hubla.DesafioBack.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionServiceTests {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    SaleRepository saleRepository;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void testaInputValido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/good_database.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }
    @Test
    public void testaInputInvalido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/3_errors.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(20,response.getSuccessfulCases());
        assertEquals(3, errors.size());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaCodigoInvalido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/codigo_invalido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(19,response.getSuccessfulCases());
        assertEquals(1, errors.size());
        assertEquals("campo tipo inválido", errors.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaInputTotalmenteInvalido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/only_errors.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(0,response.getSuccessfulCases());
        assertEquals(5, errors.size());
        assertEquals("campo tipo inválido", errors.get(0).getMessage());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, res.getStatusCode());
    }
    @Test
    public void testaVendedorNaoPreenchido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/vendedor_naopreenchido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(19,response.getSuccessfulCases());
        assertEquals(1, errors.size());
        assertEquals("campo vendedor não preenchido", errors.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaDataFormatoErrado() throws IOException {
        Path path = Paths.get("src/test/java/Resources/data_formato_errado.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(19,response.getSuccessfulCases());
        assertEquals(1, errors.size());
        assertEquals("campo data em formato errado", errors.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaPrdutoNaoPreenchido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/produto_nao_preenchido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        UploadResponse response = (UploadResponse)res.getBody();
        List<Error> errors = response.getErrors();
        assertEquals(19,response.getSuccessfulCases());
        assertEquals(1, errors.size());
        assertEquals("campo produto não preenchido", errors.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }




}
