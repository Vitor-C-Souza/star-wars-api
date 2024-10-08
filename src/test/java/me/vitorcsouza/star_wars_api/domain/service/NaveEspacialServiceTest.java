package me.vitorcsouza.star_wars_api.domain.service;

import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoReq;
import me.vitorcsouza.star_wars_api.domain.dto.NaveEspacialDtoRes;
import me.vitorcsouza.star_wars_api.domain.model.NaveEspacial;
import me.vitorcsouza.star_wars_api.domain.repository.NaveEspacialRepository;
import me.vitorcsouza.star_wars_api.domain.service.convert.NaveEspacialConvert;
import me.vitorcsouza.star_wars_api.domain.service.impl.NaveEspacialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class NaveEspacialServiceTest {
    @InjectMocks
    private NaveEspacialServiceImpl service;

    @Mock
    private NaveEspacialRepository repository;

    @Mock
    private NaveEspacialConvert convert;

    @Captor
    private ArgumentCaptor<NaveEspacial> naveEspacialArgumentCaptor;

    private NaveEspacial naveEspacial1;
    private NaveEspacial naveEspacial2;
    private NaveEspacialDtoReq dtoReq;
    private NaveEspacialDtoRes dtoRes;

    Long id;
    Pageable paginacao;
    Page<NaveEspacial> naveEspacialPage;

    @BeforeEach
    void setUp(){
        dtoReq = new NaveEspacialDtoReq(
                "X-Wing Fighter",
                "T-65B",
                "Incom Corporation",
                1
        );

        naveEspacial1 = new NaveEspacial(dtoReq);
        naveEspacial2 = new NaveEspacial(dtoReq);

        dtoRes = new NaveEspacialDtoRes(naveEspacial1);

        lenient().when(convert.toDto(naveEspacial1)).thenReturn(dtoRes);
        lenient().when(convert.toModel(dtoReq)).thenReturn(naveEspacial1);
        id = 1L;

        List<NaveEspacial> naveEspacialList = Arrays.asList(naveEspacial1, naveEspacial2);
        paginacao = PageRequest.of(0, 10);
        naveEspacialPage = new PageImpl<>(naveEspacialList, paginacao, naveEspacialList.size());
    }

    @Test
    void deveCriarUmaNaveEspacial() {
        //ARRANGE

        //ACT
        NaveEspacialDtoRes naveEspacialDtoRes = service.create(dtoReq);

        //ASSERT
        then(repository).should().save(naveEspacialArgumentCaptor.capture());
        NaveEspacial captorValue = naveEspacialArgumentCaptor.getValue();

        assertEquals(naveEspacialDtoRes.id(), captorValue.getId());
        assertEquals(naveEspacialDtoRes.nome(), captorValue.getNome());
        assertEquals(naveEspacialDtoRes.modelo(), captorValue.getModelo());
        assertEquals(naveEspacialDtoRes.fabricante(), captorValue.getFabricante());
        assertEquals(naveEspacialDtoRes.capacidadePassageiros(), captorValue.getCapacidadePassageiros());
    }

    @Test
    void deveRetornarUmaNaveEspacial() {
        //ARRANGE
        given(repository.findById(id)).willReturn(Optional.ofNullable(naveEspacial1));

        //ACT
        NaveEspacialDtoRes naveEspacialDtoRes = service.findById(id);

        //ASSERT
        assertEquals(naveEspacial1.getId(), naveEspacialDtoRes.id());
        assertEquals(naveEspacial1.getNome(), naveEspacialDtoRes.nome());
        assertEquals(naveEspacial1.getModelo(), naveEspacialDtoRes.modelo());
        assertEquals(naveEspacial1.getFabricante(), naveEspacialDtoRes.fabricante());
        assertEquals(naveEspacial1.getCapacidadePassageiros(), naveEspacialDtoRes.capacidadePassageiros());
    }

    @Test
    void deveRetornarUmaPaginaDeNaveEspacial() {
        //ARRANGE
        given(repository.findAll(paginacao)).willReturn(naveEspacialPage);

        //ACT
        Page<NaveEspacialDtoRes> naveEspacialDtoResPage = service.findAll(paginacao);

        //ASSERT
        assertEquals(naveEspacialPage.map(NaveEspacialDtoRes::new), naveEspacialDtoResPage);
    }

    @Test
    void deveRetornarUmAbrigoAtualizado() {
        //ARRANGE
        given(repository.getReferenceById(id)).willReturn(naveEspacial1);

        //ACT
        NaveEspacialDtoRes updated = service.update(dtoReq, id);

        //ASSERT
        assertEquals(naveEspacial1.getId(), updated.id());
        assertEquals(naveEspacial1.getNome(), updated.nome());
        assertEquals(naveEspacial1.getModelo(), updated.modelo());
        assertEquals(naveEspacial1.getFabricante(), updated.fabricante());
        assertEquals(naveEspacial1.getCapacidadePassageiros(), updated.capacidadePassageiros());
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