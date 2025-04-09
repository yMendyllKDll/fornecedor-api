package org.com.accenture.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmpresaTest {

    @Test
    public void testCriarEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNomeFantasia("Empresa Teste");
        empresa.setCnpj("12345678901234");
        empresa.setCep("12345678");

        assertNotNull(empresa);
        assertEquals(1L, empresa.getId());
        assertEquals("Empresa Teste", empresa.getNomeFantasia());
        assertEquals("12345678901234", empresa.getCnpj());
        assertEquals("12345678", empresa.getCep());
    }
} 