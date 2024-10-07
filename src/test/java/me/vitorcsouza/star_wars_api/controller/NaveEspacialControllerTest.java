package me.vitorcsouza.star_wars_api.controller;

import me.vitorcsouza.star_wars_api.domain.dto.CadastroDto;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import me.vitorcsouza.star_wars_api.domain.model.Usuario;
import me.vitorcsouza.star_wars_api.domain.service.NaveEspacialService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class NaveEspacialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NaveEspacialService service;
    @MockBean
    private TokenService tokenService;

    private NaveEspacialDtoRes dtoRes;
    private NaveEspacialDtoReq dtoReq;

    private Long id;
    private String json;
    private String token;

    private PageRequest paginacao;
    private Page<NaveEspacial> naveEspacialPage;

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
                    "nome": "X-Wing Fighter",
                    "modelo": "T-65B",
                    "fabricante": "Incom Corporation",
                    "capacidadePassageiros": 1
                }
                """;

        dtoReq = new NaveEspacialDtoReq(
                "X-Wing Fighter",
                "T-65B",
                "Incom Corporation",
                1
        );

        NaveEspacial naveEspacial1 = new NaveEspacial(dtoReq);
        NaveEspacial naveEspacial2 = new NaveEspacial(dtoReq);

        dtoRes = new NaveEspacialDtoRes(naveEspacial1);
        List<NaveEspacial> naveEspacialList = Arrays.asList(naveEspacial1, naveEspacial2);
        paginacao = PageRequest.of(0, 15);
        naveEspacialPage = new PageImpl<>(naveEspacialList, paginacao, naveEspacialList.size());
    }

    @Test
    void DeveRetornarCodigo201ParaCriarUmaNaveEspacial() throws Exception {
        //ARRANGE
        when(service.create(any(NaveEspacialDtoReq.class))).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(post("/nave-espacial")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT

        assertEquals(201, response.getStatus());
    }

    @Test
    void DeveRetornarCodigo200ParaEncontrarUmaNaveEspacial() throws Exception {
        //ARRANGE
        when(service.findById(anyLong())).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(get("/nave-espacial/" + anyLong())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveRetornarCodigo200ParaEncontrarUmaListaDeNaveEspaciais() throws Exception {
        //ARRANGE
        when(service.findAll(paginacao)).thenReturn(naveEspacialPage.map(NaveEspacialDtoRes::new));

        //ACT
        var response = mockMvc.perform(get("/nave-espacial")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "15")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveRetornarCodigo200ParaAtualizarUmaNaveEspaciais() throws Exception {
        //ARRANGE
        when(service.update(dtoReq, id)).thenReturn(dtoRes);

        //ACT
        var response = mockMvc.perform(put("/nave-espacial/" + id)
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveRetornarCodigo204ParaDeletarUmaNaveEspaciais() throws Exception {
        //ARRANGE

        //ACT
        var response = mockMvc.perform(delete("/nave-espacial/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(204, response.getStatus());
    }
}