package org.com.accenture.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class FornecedorTest {

    @Test
    public void testCriarFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setDocumento("12345678901");
        fornecedor.setEmail("contato@fornecedorteste.com");
        fornecedor.setCep("12345678");
        fornecedor.setTipoPessoa(Fornecedor.TipoPessoa.FISICA);
        fornecedor.setRg("123456789");
        fornecedor.setDataNascimento(LocalDate.of(1990, 1, 1));

        assertNotNull(fornecedor);
        assertEquals(1L, fornecedor.getId());
        assertEquals("Fornecedor Teste", fornecedor.getNome());
        assertEquals("12345678901", fornecedor.getDocumento());
        assertEquals("contato@fornecedorteste.com", fornecedor.getEmail());
        assertEquals("12345678", fornecedor.getCep());
        assertEquals(Fornecedor.TipoPessoa.FISICA, fornecedor.getTipoPessoa());
        assertEquals("123456789", fornecedor.getRg());
        assertEquals(LocalDate.of(1990, 1, 1), fornecedor.getDataNascimento());
    }
} 