package me.vitorcsouza.star_wars_api.controller;

import me.vitorcsouza.star_wars_api.domain.dto.*;
import me.vitorcsouza.star_wars_api.domain.model.*;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.PersonagemService;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonagemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetaRepository planetaRepository;
    @MockBean
    private SistemaEstelarRepository sistemaEstelarRepository;

    @MockBean
    private PersonagemService service;
    @MockBean
    private TokenService tokenService;

    private PersonagemDtoRes dtoRes;
    private PersonagemDtoReq dtoReq;

    private Long id;
    private String json;
    private String token;

    private PageRequest paginacao;
    private Page<Personagem> personagemPage;


    @BeforeEach
    void setUp(){
        id = 1L;
        CadastroDto cadastroDto = new CadastroDto(id, "vitor", "1234");
        Usuario login = new Usuario(cadastroDto.usuario(), "1234");
        token = tokenService.GerarToken(login);
        when(tokenService.GerarToken(login)).thenReturn(token);
        when(tokenService.getSubject(anyString())).thenReturn(login.getUsuario());

        json = """
                {
                    "nome": "Luke Skywalker",
                    "raca": "Humano",
                    "afiliacao": "ALIANCA_REBELDE",
                    "planetaNatal_id": 1
                }
                """;

        dtoReq = new PersonagemDtoReq(
                "Luke Skywalker",
                "Humano",
                "ALIANCA_REBELDE",
                1L
                );

        SistemaEstelarDtoReq sistemaEstelarDtoReq = new SistemaEstelarDtoReq(
                "Sistema Estelar Coruscant",
                "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
        );
        SistemaEstelar sistemaEstelar = new SistemaEstelar(sistemaEstelarDtoReq);

        PlanetaDtoReq planetaDtoReq = new PlanetaDtoReq(
                "D'rinba IV",
                "Frio",
                "Montanhas e Desertos Gelados",
                75000,
                1L);

        when(sistemaEstelarRepository.findById(anyLong())).thenReturn(Optional.of(sistemaEstelar));

        Planeta planeta = new Planeta(planetaDtoReq, sistemaEstelarRepository);

        when(planetaRepository.findById(anyLong())).thenReturn(Optional.of(planeta));

        Personagem personagem1 = new Personagem(dtoReq, planetaRepository);
        Personagem personagem2 = new Personagem(dtoReq, planetaRepository);





        dtoRes = new PersonagemDtoRes(personagem1);

        List<Personagem> personagemList = Arrays.asList(personagem2, personagem1);
        paginacao = PageRequest.of(0, 15);
        personagemPage = new PageImpl<>(personagemList, paginacao, personagemList.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmPersonagem() throws Exception {
        //ARRANGE
        when(service.create(any(PersonagemDtoReq.class))).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(post("/personagem")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEcontrarUmPersonagem() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(get("/personagem/"+ id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEcontrarUmaPageDePersonagem() throws Exception {
        //ARRANGE
        when(service.findAll(paginacao)).thenReturn(personagemPage.map(PersonagemDtoRes::new));


        //ACT
        var response = mockMvc.perform(get("/personagem")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaAtualizarUmPersonagem() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(put("/personagem/"+id)
                .header("Authorization", "Bearer " + token)
                        .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo204ParaDeletarUmPersonagem() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/personagem/"+id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}
