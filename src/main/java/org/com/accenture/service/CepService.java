package org.com.accenture.service;

import org.com.accenture.client.ViaCepClient;
import org.com.accenture.model.response.ViaCepResponse;
import org.springframework.stereotype.Service;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CepService {
    private static final Logger logger = LoggerFactory.getLogger(CepService.class);
    
    private final ViaCepClient viaCepClient;
    
    public CepService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }
    
    public boolean validarCep(String cep) {
        try {
            logger.info("Validando CEP: {}", cep);
            ViaCepResponse response = viaCepClient.consultarCep(cep);
            logger.info("Resposta para {}: erro={}", cep, response.getErro());
            return response != null && response.getErro() == null;
        } catch (FeignException.NotFound e) {
            logger.error("CEP {} não encontrado", cep);
            return false;
        } catch (Exception e) {
            logger.error("Erro ao validar CEP {}: {}", cep, e.getMessage());
            return false;
        }
    }
}