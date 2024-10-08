package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.*;
import me.vitorcsouza.star_wars_api.domain.model.Afiliacao;
import me.vitorcsouza.star_wars_api.domain.model.Personagem;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.convert.PersonagemConvert;
import me.vitorcsouza.star_wars_api.domain.service.impl.PersonagemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PersonagemServiceTest {
    @InjectMocks
    private PersonagemServiceImpl service;

    @Mock
    private PersonagemRepository repository;
    @Mock
    private PlanetaRepository planetaRepository;
    @Mock
    private SistemaEstelarRepository sistemaEstelarRepository;

    @Mock
    private PersonagemConvert convert;

    @Captor
    private ArgumentCaptor<Personagem> personagemArgumentCaptor;

    private Personagem personagem1;
    private Personagem personagem2;
    private PersonagemDtoReq dtoReq;
    private PersonagemDtoRes dtoRes;

    Long id;
    Pageable paginacao;
    Page<Personagem> personagemPage;

    @BeforeEach
    void setUp(){
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
                4L
        );
        when(sistemaEstelarRepository.findById(planetaDtoReq.sistema_id())).thenReturn(Optional.of(sistemaEstelar));

        Planeta planeta = new Planeta(planetaDtoReq, sistemaEstelarRepository);

        dtoReq = new PersonagemDtoReq(
                "Luke Skywalker",
                "Humano",
                "ALIANCA_REBELDE",
                1L
        );

        when(planetaRepository.findById(dtoReq.planetaNatal_id())).thenReturn(Optional.of(planeta));

        personagem1 = new Personagem(dtoReq, planetaRepository);
        personagem2 = new Personagem(dtoReq, planetaRepository);

        dtoRes = new PersonagemDtoRes(personagem1);

        lenient().when(convert.toDto(personagem1)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq, planetaRepository)).thenReturn(personagem1);

        id = 1L;

        List<Personagem> personagemList = Arrays.asList(personagem1, personagem2);
        paginacao = PageRequest.of(0, 10);
        personagemPage = new PageImpl<>(personagemList, paginacao, personagemList.size());
    }

    @Test
    void deveCriarUmPersonagem() {
        //ARRANGE

        //ACT
        PersonagemDtoRes personagemDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(personagemArgumentCaptor.capture());
        Personagem captorValue = personagemArgumentCaptor.getValue();

        assertEquals(personagemDtoRes.id(), captorValue.getId());
        assertEquals(personagemDtoRes.nome(), captorValue.getNome());
        assertEquals(personagemDtoRes.raca(), captorValue.getRaca());
        assertEquals(Afiliacao.valueOf(personagemDtoRes.afiliacao()), captorValue.getAfiliacao());
        assertEquals(personagemDtoRes.planeta_natal(), new PlanetaDtoResPersonagem(captorValue.getPlanetaNatal()));
    }

    @Test
    void deveRetornarUmPersonagem() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.ofNullable(personagem1));

        //ACT
        PersonagemDtoRes personagemDtoRes = service.findById(id);

        //ASSERT
        assertEquals(personagemDtoRes.id(), personagem1.getId());
        assertEquals(personagemDtoRes.nome(), personagem1.getNome());
        assertEquals(personagemDtoRes.raca(), personagem1.getRaca());
        assertEquals(Afiliacao.valueOf(personagemDtoRes.afiliacao()), personagem1.getAfiliacao());
        assertEquals(personagemDtoRes.planeta_natal(), new PlanetaDtoResPersonagem(personagem1.getPlanetaNatal()));
    }

    @Test
    void deveRetornarUmaPaginaDePersonagem() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(personagemPage);

        //ACT
        Page<PersonagemDtoRes> dtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(personagemPage.map(PersonagemDtoRes::new), dtoResPage);
    }

    @Test
    void deveAtualizarUmPersonagem() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(personagem1);

        //ACT
        PersonagemDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(updated.id(), personagem1.getId());
        assertEquals(updated.nome(), personagem1.getNome());
        assertEquals(updated.raca(), personagem1.getRaca());
        assertEquals(Afiliacao.valueOf(updated.afiliacao()), personagem1.getAfiliacao());
        assertEquals(updated.planeta_natal(), new PlanetaDtoResPersonagem(personagem1.getPlanetaNatal()));
    }

    @Test
    void deveApagarUmPersonagem() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}