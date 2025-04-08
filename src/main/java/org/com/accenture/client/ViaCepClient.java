package org.com.accenture.client;

import org.com.accenture.model.response.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    
    @GetMapping(value = "/{cep}/json", produces = "application/json")
    ViaCepResponse consultarCep(@PathVariable("cep") String cep);
} 