package me.vitorcsouza.star_wars_api.controller;

import me.vitorcsouza.star_wars_api.domain.dto.*;
import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.PlanetaService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlanetaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SistemaEstelarRepository sistemaEstelarRepository;
    @MockBean
    private PersonagemRepository personagemRepository;
    @MockBean
    private PlanetaRepository planetaRepository;

    @MockBean
    private PlanetaService service;
    @MockBean
    private TokenService tokenService;

    private PlanetaDtoReq dtoReq;
    private PlanetaDtoRes dtoRes;

    private Long id;
    private String json;
    private String token;

    private PageRequest paginacao;
    private Page<Planeta> planetaPage;


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
                  "nome": "D'rinba IV",
                  "clima": "Frio",
                  "terreno": "Montanhas e Desertos Gelados",
                  "populacao": 75000,
                  "sistema_id": 4
                }
                """;
        dtoReq = new PlanetaDtoReq(
                "D'rinba IV",
                "Frio",
                "Montanhas e Desertos Gelados",
                75000,
                1L
        );

        SistemaEstelarDtoReq sistemaEstelarDtoReq = new SistemaEstelarDtoReq(
                "Sistema Estelar Coruscant",
                "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
        );
        SistemaEstelar sistemaEstelar = new SistemaEstelar(sistemaEstelarDtoReq);
        when(sistemaEstelarRepository.findById(anyLong())).thenReturn(Optional.of(sistemaEstelar));

        Planeta planeta1 = new Planeta(dtoReq, sistemaEstelarRepository);
        Planeta planeta2 = new Planeta(dtoReq, sistemaEstelarRepository);

        when(planetaRepository.findById(anyLong())).thenReturn(Optional.of(planeta1));

        PersonagemDtoReq personagemDtoReq = new PersonagemDtoReq(
                "Luke Skywalker",
                "Humano",
                "ALIANCA_REBELDE",
                1L
        );
        Personagem personagem1 = new Personagem(personagemDtoReq, planetaRepository);
        Personagem personagem2 = new Personagem(personagemDtoReq, planetaRepository);
        PersonagemDtoResPlaneta personagemDtoResPlaneta1 = new PersonagemDtoResPlaneta(personagem1);
        PersonagemDtoResPlaneta personagemDtoResPlaneta2 = new PersonagemDtoResPlaneta(personagem1);

        List<Personagem> personagemList = Arrays.asList(personagem2, personagem1);

        when(personagemRepository.findByPlaneta(anyLong())).thenReturn(personagemList);
        List<PersonagemDtoResPlaneta> personagemDtoResPlanetaList = Arrays.asList(personagemDtoResPlaneta1, personagemDtoResPlaneta2);

        dtoRes = new PlanetaDtoRes(planeta1, personagemDtoResPlanetaList);

        List<Planeta> planetaList = Arrays.asList(planeta1, planeta2);
        paginacao = PageRequest.of(0, 15);
        planetaPage = new PageImpl<>(planetaList, paginacao, planetaList.size());
    }

    @Test
    void deveRetornarCodigo201ParaSalvarUmPlaneta() throws Exception {
        //ARRANGE
        when(service.create(any(PlanetaDtoReq.class))).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(post("/planeta")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmPlaneta() throws Exception {
        //ARRANGE
        when(service.findById(id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(get("/planeta/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaEncontrarUmaPagePlaneta() throws Exception {
        //ARRANGE
        Page<PlanetaDtoRes> planetaDtoResPage = planetaPage.map(planeta -> {
            List<PersonagemDtoResPlaneta> personagemDtoResPlanetas = personagemRepository.findByPlaneta(planeta.getId())
                    .stream().map(PersonagemDtoResPlaneta::new).toList();
            return new PlanetaDtoRes(planeta, personagemDtoResPlanetas);
        });

        when(service.findAll(paginacao)).thenReturn(planetaDtoResPage);

        //ACT
        var response = mockMvc.perform(get("/planeta")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaAtualizarUmPlaneta() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);


        //ACT
        var response = mockMvc.perform(put("/planeta/" + id)
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
        var response = mockMvc.perform(delete("/planeta/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}