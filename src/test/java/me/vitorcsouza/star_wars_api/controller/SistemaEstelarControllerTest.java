package me.vitorcsouza.star_wars_api.controller;

import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;
import me.vitorcsouza.star_wars_api.domain.dto.PersonagemDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.SistemaEstelarService;
import me.vitorcsouza.star_wars_api.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SistemaEstelarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SistemaEstelarService service;
    @MockBean
    private PlanetaRepository planetaRepository;

    @MockBean
    private TokenService tokenService;

    private SistemaEstelarDtoReq dtoReq;
    private SistemaEstelarDtoRes dtoRes;

    private Long id;
    private String json;
    private String token;

    private PageRequest paginacao;
    private Page<SistemaEstelar> sistemaEstelarPage;

    @BeforeEach
    void setUp() {
        id = 1L;
        CadastroDto cadastroDto = new CadastroDto(id, "vitor", "1234");
        Usuario login = new Usuario(cadastroDto.usuario(), "1234");
        token = tokenService.GerarToken(login);
        when(tokenService.GerarToken(login)).thenReturn(token);
        when(tokenService.getSubject(anyString())).thenReturn(login.getUsuario());

        json = """
                {
                  "nome": "Sistema Estelar Coruscant",
                  "descricao": "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
                }
                """;

        dtoReq = new SistemaEstelarDtoReq(
                "Sistema Estelar Coruscant",
                "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
        );

        SistemaEstelar sistemaEstelar1 = new SistemaEstelar(dtoReq);
        SistemaEstelar sistemaEstelar2 = new SistemaEstelar(dtoReq);

        dtoRes = new SistemaEstelarDtoRes(sistemaEstelar1, planetaRepository);

        List<SistemaEstelar> sistemaEstelarList = Arrays.asList(sistemaEstelar1, sistemaEstelar2);

        paginacao = PageRequest.of(0, 15);
        sistemaEstelarPage = new PageImpl<>(sistemaEstelarList, paginacao, sistemaEstelarList.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmSistemaEstelar() throws Exception {
        //ARRANGE
        when(service.create(any(SistemaEstelarDtoReq.class))).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(post("/sistema-estelar")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmSistemaEstelar() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(get("/sistema-estelar/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmaPageSistemaEstelar() throws Exception {
        //ARRANGE
        Page<SistemaEstelarDtoRes> sistemaEstelarDtoRes = sistemaEstelarPage.map(sistemaEstelar -> new SistemaEstelarDtoRes(sistemaEstelar, planetaRepository));
        when(service.findAll(paginacao)).thenReturn(sistemaEstelarDtoRes);


        //ACT
        var response = mockMvc.perform(get("/sistema-estelar")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaAtualizarUmSistemaEstelar() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(put("/sistema-estelar/" + id)
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmSistemaEstelar() throws Exception {
        //ARRANGE


        //ACT
        var response = mockMvc.perform(delete("/sistema-estelar/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}