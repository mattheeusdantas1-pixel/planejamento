# Planejamento Estratégico (BSC) da Sopa da Roxa

Backend feito para a atividade extra da primeira unidade de **Desenvolvimento Web III** (ADS, IFPE Campus Recife, 2026.2).

O tema era livre, então usei o meu próprio negócio: a **Sopa da Roxa**, uma soparia e tapiocaria aqui em Recife que eu toco com a minha esposa. Os dados que estão no banco são metas e números que fazem sentido pra realidade da Sopa: crescer o delivery, diminuir a sobra de sopa no fim do dia, crescer no Instagram e treinar a equipe.

## O que é o BSC

BSC (Balanced Scorecard) é um jeito de transformar a estratégia da empresa em coisas que dá pra medir. A empresa tem uma **missão** (por que existe) e uma **visão** (onde quer chegar), e os **objetivos** ficam divididos em 4 perspectivas:

| Perspectiva | Pergunta | Objetivo da Sopa da Roxa |
|---|---|---|
| Financeira | como estamos com o dinheiro? | Aumentar as vendas do delivery |
| Clientes | como o cliente vê a gente? | Fortalecer a marca nas redes sociais |
| Processos internos | no que precisamos ser bons por dentro? | Reduzir o desperdício na cozinha |
| Aprendizado e crescimento | como a equipe melhora? | Capacitar a equipe |

Cada objetivo vira **metas** (com prazo e um departamento responsável), cada meta tem **métricas** (o que vai ser medido e qual o alvo) e cada métrica tem **indicadores** (as medições de verdade, com data).

## Modelo

Segui o diagrama passado em sala:

```
Empresa (missão, visão)
  └── Objetivo (perspectiva do BSC)
        └── Meta (prazo) ─────── Departamento
              └── Métrica (unidade, valor alvo)
                    └── Indicador (valor medido, data)
```

| Entidade | Campos |
|---|---|
| Empresa | nome, missão, visão |
| Departamento | nome, responsável |
| Objetivo | título, descrição, perspectiva, empresa |
| Meta | descrição, prazo, objetivo, departamento |
| Métrica | descrição, unidade, valor alvo, meta |
| Indicador | valor, data da medição, observação, métrica |

- Objetivo, meta, métrica e indicador são **composição**: se apagar o pai, os filhos vão junto (`ON DELETE CASCADE` no banco).
- Meta e departamento é só uma **associação**: o departamento é responsável pela meta, mas não é dono dela. Por isso não dá pra excluir um departamento que tem meta (a API responde 409).

Exemplo de uma linha completa:

> Objetivo **Aumentar as vendas do delivery** (Financeira) → meta **Delivery chegar a 40% das vendas até março** (departamento Delivery) → métrica **% das vendas pelo delivery**, alvo 40 → indicadores **22% em setembro** e **27% em outubro**.

## Tecnologias

- Java 21
- Spring Boot 4 (Spring Web)
- JPA com Hibernate (repositórios usando o `EntityManager`)
- Banco H2 em memória
- Maven

## Como rodar

Pelo Eclipse: abrir o projeto como projeto Maven e rodar a classe `PlanejamentoApplication` (Run As > Java Application).

Ou pelo terminal, dentro da pasta do projeto:

```bash
./mvnw spring-boot:run
```

No Windows (cmd ou PowerShell):

```
mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`.

O banco é em memória: toda vez que o sistema sobe, as tabelas são criadas pelo `schema.sql` e preenchidas com os dados da Sopa da Roxa pelo `data.sql`. O que for criado ou apagado volta ao original quando reiniciar.

Para ver o banco pelo navegador: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:planejamento`
- Usuário: `sa`
- Senha: em branco

## Rotas da API

Todas as entidades têm as mesmas 5 rotas:

| Método | Rota | O que faz |
|---|---|---|
| GET | `/empresas` | lista todas |
| GET | `/empresas/{id}` | busca uma (404 se não existir) |
| POST | `/empresas` | cria (201) |
| PUT | `/empresas/{id}` | altera |
| DELETE | `/empresas/{id}` | exclui (204) |

Trocando `empresas` por `departamentos`, `objetivos`, `metas`, `metricas` ou `indicadores`.

Rotas para descer pelo modelo, do pai para os filhos:

| Rota | O que mostra |
|---|---|
| `GET /empresas/1/objetivos` | os objetivos da empresa |
| `GET /objetivos/1/metas` | as metas do objetivo |
| `GET /metas/1/metricas` | as métricas da meta |
| `GET /metricas/1/indicadores` | as medições da métrica |

## Exemplos com curl

Criar um objetivo (as perspectivas aceitas são `FINANCEIRA`, `CLIENTES`, `PROCESSOS_INTERNOS` e `APRENDIZADO_CRESCIMENTO`):

```bash
curl -X POST http://localhost:8080/objetivos -H "Content-Type: application/json" -d '{"titulo":"Lançar sopa nova no inverno","descricao":"Sopa sazonal pra junho","perspectiva":"CLIENTES","empresa":{"id":1}}'
```

Criar uma meta para esse objetivo, com a Cozinha como responsável:

```bash
curl -X POST http://localhost:8080/metas -H "Content-Type: application/json" -d '{"descricao":"Vender 300 potes da sopa nova em junho","prazo":"2027-06-30","objetivo":{"id":5},"departamento":{"id":1}}'
```

Tentar excluir um departamento que tem meta (responde 409):

```bash
curl -i -X DELETE http://localhost:8080/departamentos/3
```

## Respostas da API

| Código | Quando |
|---|---|
| 200 | listou, buscou ou alterou |
| 201 | criou |
| 204 | excluiu |
| 400 | o pai informado não existe, ou a perspectiva é inválida |
| 404 | o id não existe |
| 409 | tentou excluir departamento que tem meta |

## Estrutura

```
src/main/java/br/edu/ifpe/recife/planejamento/
├── model/        Empresa, Departamento, Objetivo, Meta, Metrica, Indicador, Perspectiva
├── repository/   acesso ao banco com o EntityManager do JPA
└── controller/   rotas da API (REST)

src/main/resources/
├── application.properties
├── schema.sql    tabelas
└── data.sql      dados da Sopa da Roxa
```

## Autor

Mattheeus Dantas, ADS IFPE Recife.
