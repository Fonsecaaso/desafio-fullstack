package com.hubla.DesafioBack;

import com.hubla.DesafioBack.entity.Erro;
import com.hubla.DesafioBack.entity.UploadRequest;
import com.hubla.DesafioBack.entity.UserEntity;
import com.hubla.DesafioBack.repository.CursoRepositoryI;
import com.hubla.DesafioBack.repository.TransactionRepository;
import com.hubla.DesafioBack.repository.UserRepositoryI;
import com.hubla.DesafioBack.repository.VendaRepositoryI;
import com.hubla.DesafioBack.service.TransactionService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UploadServiceTests {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    UserRepositoryI userRepository;
    @Mock
    CursoRepositoryI cursoRepository;
    @Mock
    VendaRepositoryI vendaRepository;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void testaInputValido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/sales1.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
    }
    @Test
    public void testaInputInvalido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/sales2.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        List<Erro> erros = (List<Erro>)res.getBody();
        assertEquals(3, erros.size());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaCodigoInvalido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/codigo_invalido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        List<Erro> erros = (List<Erro>)res.getBody();
        assertEquals(1, erros.size());
        assertEquals("campo tipo com input inválido", erros.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaVendedorNaoPreenchido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/vendedor_naopreenchido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        List<Erro> erros = (List<Erro>)res.getBody();
        assertEquals(1, erros.size());
        assertEquals("campo vendedor não preenchido", erros.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaDataFormatoErrado() throws IOException {
        Path path = Paths.get("src/test/java/Resources/data_formato_errado.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        List<Erro> erros = (List<Erro>)res.getBody();
        assertEquals(1, erros.size());
        assertEquals("campo data em formato errado", erros.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }
    @Test
    public void testaPrdutoNaoPreenchido() throws IOException {
        Path path = Paths.get("src/test/java/Resources/produto_nao_preenchido.txt");
        UploadRequest req = new UploadRequest();
        req.setFile(Files.readString(path));
        when(userRepository.findByName(any())).thenReturn(new UserEntity());
        when(cursoRepository.save(any())).thenReturn(null);

        ResponseEntity<?> res = transactionService.processTransactions(req);

        List<Erro> erros = (List<Erro>)res.getBody();
        assertEquals(1, erros.size());
        assertEquals("campo produto não preenchido", erros.get(0).getMessage());
        assertEquals(HttpStatus.PARTIAL_CONTENT, res.getStatusCode());
    }




}
