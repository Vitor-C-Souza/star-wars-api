package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.SistemaEstelarDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import me.vitorcsouza.star_wars_api.domain.model.SistemaEstelar;
import me.vitorcsouza.star_wars_api.domain.repository.PlanetaRepository;
import me.vitorcsouza.star_wars_api.domain.repository.SistemaEstelarRepository;
import me.vitorcsouza.star_wars_api.domain.service.convert.SistemaEstelarConvert;
import me.vitorcsouza.star_wars_api.domain.service.impl.SistemaEstelarServiceImpl;
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

@ExtendWith(MockitoExtension.class)
class SistemaEstelarServiceTest {
    @InjectMocks
    private SistemaEstelarServiceImpl service;

    @Mock
    private SistemaEstelarRepository repository;
    @Mock
    private PlanetaRepository planetaRepository;

    @Mock
    private SistemaEstelarConvert convert;

    @Captor
    private ArgumentCaptor<SistemaEstelar> sistemaEstelarArgumentCaptor;

    private SistemaEstelar sistemaEstelar1;
    private SistemaEstelar sistemaEstelar2;
    private SistemaEstelarDtoReq dtoReq;
    private SistemaEstelarDtoRes dtoRes;

    Long id;
    Pageable paginacao;
    Page<SistemaEstelar> sistemaEstelarPage;

    @BeforeEach
    void setUp(){
        dtoReq = new SistemaEstelarDtoReq(
                "Sistema Estelar Coruscant",
                "Centro político da galáxia e lar da cidade-planeta Coruscant, sede da República Galáctica."
        );

        sistemaEstelar1 = new SistemaEstelar(dtoReq);
        sistemaEstelar2 = new SistemaEstelar(dtoReq);

        dtoRes = new SistemaEstelarDtoRes(sistemaEstelar1, planetaRepository);

        lenient().when(convert.toDto(sistemaEstelar1, planetaRepository)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq)).thenReturn(sistemaEstelar1);
        id = 1L;

        List<SistemaEstelar> sistemaEstelarList = Arrays.asList(sistemaEstelar1, sistemaEstelar2);
        paginacao = PageRequest.of(0, 10);
        sistemaEstelarPage = new PageImpl<>(sistemaEstelarList, paginacao, sistemaEstelarList.size());
    }

    @Test
    void deveCriarUmSistemaEstelar() {
        //ARRANGE

        //ACT
        SistemaEstelarDtoRes sistemaEstelarDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(sistemaEstelarArgumentCaptor.capture());
        SistemaEstelar captorValue = sistemaEstelarArgumentCaptor.getValue();

        assertEquals(sistemaEstelarDtoRes.id(), captorValue.getId());
        assertEquals(sistemaEstelarDtoRes.nome(), captorValue.getNome());
        assertEquals(sistemaEstelarDtoRes.descricao(), captorValue.getDescricao());
    }

    @Test
    void deveRetornarUmSistemaEstelar() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.ofNullable(sistemaEstelar1));

        //ACT
        SistemaEstelarDtoRes sistemaEstelarDtoRes = service.findById(id);

        //ASSERT

        assertEquals(sistemaEstelarDtoRes.id(), sistemaEstelar1.getId());
        assertEquals(sistemaEstelarDtoRes.nome(), sistemaEstelar1.getNome());
        assertEquals(sistemaEstelarDtoRes.descricao(), sistemaEstelar1.getDescricao());
    }

    @Test
    void deveRetornarUmaPaginaDeSistemaEstelar() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(sistemaEstelarPage);

        //ACT
        Page<SistemaEstelarDtoRes> sistemaEstelarDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(sistemaEstelarPage.map(sistemaEstelar -> new SistemaEstelarDtoRes(sistemaEstelar, planetaRepository)), sistemaEstelarDtoResPage);
    }

    @Test
    void deveRetornarUmAbrigoAtualizado() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(sistemaEstelar1);

        //ACT
        SistemaEstelarDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(sistemaEstelar1.getId(), updated.id());
        assertEquals(sistemaEstelar1.getNome(), updated.nome());
        assertEquals(sistemaEstelar1.getDescricao(), updated.descricao());
    }

    @Test
    void deveApagarUmaNaveEspacial() {
        //ARRANGE

        //ACT
        service.delete(id);

        //ASSERT
        verify(repository, times(1)).deleteById(id);
    }
}