package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.*;
import me.vitorcsouza.star_wars_api.domain.model.Planeta;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.repository.PersonagemRepository;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.convert.PlanetaConvert;
import me.vitorcsouza.star_wars_api.domain.service.impl.PlanetaServiceImpl;
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
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PlanetaServiceTest {
    @InjectMocks
    private PlanetaServiceImpl service;

    @Mock
    private PlanetaRepository repository;
    @Mock
    private SistemaEstelarRepository sistemaEstelarRepository;
    @Mock
    private PersonagemRepository personagemRepository;

    @Mock
    private PlanetaConvert convert;

    @Captor
    private ArgumentCaptor<Planeta> planetaArgumentCaptor;

    private Planeta planeta1;
    private Planeta planeta2;
    private PlanetaDtoReq dtoReq;
    private PlanetaDtoRes dtoRes;

    Long id;
    Pageable paginacao;
    Page<Planeta> planetaPage;

    private List<PersonagemDtoResPlaneta> personagemDtoResPlanetas;

    @BeforeEach
    void setUp() {
        SistemaEstelarDtoReq sistemaEstelarDtoReq = new SistemaEstelarDtoReq(
                "Sistema Estelar Coruscant",
                "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
        );
        SistemaEstelar sistemaEstelar = new SistemaEstelar(sistemaEstelarDtoReq);
        dtoReq = new PlanetaDtoReq(
                "D'rinba IV",
                "Frio",
                "Montanhas e Desertos Gelados",
                75000,
                4L
        );
        when(sistemaEstelarRepository.findById(dtoReq.sistema_id())).thenReturn(Optional.of(sistemaEstelar));

        planeta1 = new Planeta(dtoReq, sistemaEstelarRepository);
        planeta2 = new Planeta(dtoReq, sistemaEstelarRepository);

        personagemDtoResPlanetas = personagemRepository.findByPlaneta(planeta1.getId()).stream().map(PersonagemDtoResPlaneta::new).collect(Collectors.toList());

        dtoRes = new PlanetaDtoRes(planeta1, personagemDtoResPlanetas);

        lenient().when(convert.toDto(planeta1, personagemRepository)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq, sistemaEstelarRepository)).thenReturn(planeta1);

        id = 1L;

        List<Planeta> planetaList = Arrays.asList(planeta1, planeta2);
        paginacao = PageRequest.of(0, 10);
        planetaPage = new PageImpl<>(planetaList, paginacao, planetaList.size());


    }

    @Test
    void deveCriarUmPlaneta() {
        //ARRANGE

        //ACT
        PlanetaDtoRes planetaDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(planetaArgumentCaptor.capture());
        Planeta captorValue = planetaArgumentCaptor.getValue();

        assertEquals(planetaDtoRes.id(), captorValue.getId());
        assertEquals(planetaDtoRes.nome(), captorValue.getNome());
        assertEquals(planetaDtoRes.clima(), captorValue.getClima());
        assertEquals(planetaDtoRes.terreno(), captorValue.getTerreno());
        assertEquals(planetaDtoRes.populacao(), captorValue.getPopulacao());
        assertEquals(planetaDtoRes.sistema(), new SistemaEstelarDtoResPlaneta(captorValue.getSistema()));
    }

    @Test
    void deveRetornarUmPlaneta() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.ofNullable(planeta1));

        //ACT
        PlanetaDtoRes planetaDtoRes = service.findById(id);

        //ASSERT
        assertEquals(planetaDtoRes.id(), planeta1.getId());
        assertEquals(planetaDtoRes.nome(), planeta1.getNome());
        assertEquals(planetaDtoRes.clima(), planeta1.getClima());
        assertEquals(planetaDtoRes.terreno(), planeta1.getTerreno());
        assertEquals(planetaDtoRes.populacao(), planeta1.getPopulacao());
        assertEquals(planetaDtoRes.sistema(), new SistemaEstelarDtoResPlaneta(planeta1.getSistema()));
    }

    @Test
    void deveRetornarUmaPaginaDePlaneta() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(planetaPage);

        //ACT
        Page<PlanetaDtoRes> planetaDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(planetaPage.map(planeta -> new PlanetaDtoRes(planeta, personagemDtoResPlanetas)), planetaDtoResPage);
    }

    @Test
    void deveAtualizarUmPlaneta() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(planeta1);

        //ACT
        PlanetaDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(updated.id(), planeta1.getId());
        assertEquals(updated.nome(), planeta1.getNome());
        assertEquals(updated.clima(), planeta1.getClima());
        assertEquals(updated.terreno(), planeta1.getTerreno());
        assertEquals(updated.populacao(), planeta1.getPopulacao());
        assertEquals(updated.sistema(), new SistemaEstelarDtoResPlaneta(planeta1.getSistema()));
    }

    @Test
    void deveApagarUmPlaneta() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}