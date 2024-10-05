# Star Wars API

## Descrição
Criação, gerenciamento e visualização de uma galáxia inspirada em Star Wars, incluindo planetas, sistemas estelares, personagens e naves espaciais.

## Tecnologias
- Java 17
- Mysql
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- SpringDoc
- Validation
- Spring Security
- Railway
- OpenAPI (Swagger)

## Diagrama de Classes (Domínio da API)
```mermaid
    classDiagram
    Usuario <<implements>> UserDetails
    Usuario --> |created by| SistemaEstelar
    SistemaEstelar --> |has many| Planeta
    Planeta --> |has one| Personagem
    Personagem --> |belongs to| Planeta
    Planeta --> |belongs to| SistemaEstelar
    SistemaEstelar use : SistemaEstelarDtoReq
    Planeta use : PlanetaDtoReq
    Personagem use : PersonagemDtoReq
    NaveEspacial use : NaveEspacialDtoReq
    
    Usuario : String usuario
    Usuario : String senha

    SistemaEstelar : Long id
    SistemaEstelar : String nome
    SistemaEstelar : String descricao
    SistemaEstelar : List<Planeta> planetas

    Planeta : Long id
    Planeta : String nome
    Planeta : String clima
    Planeta : String terreno
    Planeta : Integer populacao
    Planeta : SistemaEstelar sistema
    Planeta : List<Personagem> personagem

    Personagem : Long id
    Personagem : String nome
    Personagem : String raca
    Personagem : Afiliacao afiliacao

    NaveEspacial : Long id
    NaveEspacial : String nome
    NaveEspacial : String modelo
    NaveEspacial : String fabricante
    NaveEspacial : Integer capacidadePassageiros
```

## Testes
Para testar a aplicação basta acessar esta URL: https://star-wars-api-star-wars-api.up.railway.app/swagger-ui/index.html#/

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:

1. Fork o repositório.
2. Crie uma nova branch (git checkout -b feature/nova-feature).
3. Commit suas alterações (git commit -m 'Adiciona nova feature').
4. Push para a branch (git push origin feature/nova-feature).
5. Abra um Pull Request.

