# Baselines do Projeto

## BL0: Criação do Repositório
Esta baseline representa o estado inicial do projeto, onde a estrutura básica foi criada, sem nenhuma funcionalidade de negócio implementada.
* **Data:** 02/06/2026
* **Commit de Referência:**: 69e78c6
* **Descrição:** Implementação de Scaffolding

---

## BL1: Release 1.0.0 (Entrega da Primeira Versão)
Esta baseline representa a primeira entrega oficial do sistema, contendo as funcionalidades operacionais básicas de gerenciamento (CRUDs), autenticação e padrões de projeto aplicados.
* **Data:** 14/06/2026
* **Tag/Versão:** v1.0.0
* **Commit de Referência:** 077b101
* **Escopo Entregue:** 
  * Backend: Implementação dos CRUDs (Ambulância, Funcionário, Ocorrência, etc) e aplicação de Design Patterns (Singleton, Decorator, Adapter, etc).
  * Frontend: Telas de autenticação, relatórios, ocorrências, insumos e gerenciamento de equipes.

### Matriz de Rastreabilidade (BL1)
Abaixo estão os requisitos/issues que compõem a baseline atual (BL1) e seus respectivos artefatos no código:

| Issue / Funcionalidade | Commit(s) | Principais Arquivos Modificados | Status |
| --- | --- | --- | --- |
| **#81** [FEATURE/BUGFIX] Refatoração Fluxo de Despacho | `0f90e4f`<br>

<br>`b137fde` | `OcorrenciaController.java`<br>

<br>`DespachoDTO.java`<br>

<br>`ocorrencia-lista.component.ts` | Fechado |
| **#79** [BUG] Inconsistência Vínculo Usuário/Funcionário | `847016a`<br>

<br>`d16cc8c`<br>

<br>`c06a102` | `cadastro-usuario.ts`<br>

<br>`UsuarioService.java`<br>

<br>`GlobalExceptionHandler.java` | Fechado |
| **#77** [BUG] Sistema ejetando usuário (Erro 401) | `b137fde` | `SecurityConfig.java`<br>

<br>`auth.interceptor.ts` | Fechado |
| **#71** [FEATURE] Tela de Funcionários (CRUD) | `7643654`<br>

<br>`4ed31a8`<br>

<br>`a0f6392`<br>

<br>`1fbefb4` | `funcionario.service.ts`<br>

<br>`funcionarios-list.ts`<br>

<br>`FuncionarioController.java` | Fechado |
| **#52** [REFACTOR] Reorganização dos Enums e Estrutura | `096c621`<br>

<br>`e636494` | `StatusAmbulancia.java`<br>

<br>`GravidadeOcorrencia.java`<br>

<br>`AmbulanciaController.java` | Fechado |
| **#51** [FEATURE] Gerenciamento de Ambulâncias (Front) | `816d461` | `ambulancia-form.component.ts`<br>

<br>`ambulancia-lista.component.ts` | Fechado |
| **#50** [REFACTOR] Padrão Adapter - Geolocalização | `9170bcd` | `CalculadorDeDistancia.java`<br>

<br>`HaversineAdapter.java` | Fechado |
| **#49** [FEATURE] Tela Relatório e Dashboard | `18d04c9`<br>

<br>`47c7925` | `DashboardController.java`<br>

<br>`RelatorioController.java` | Fechado |
| **#48** [FEATURE] Tela Insumos e Equipamentos | `7b36ae2` | `equipamento-lista.component.ts`<br>

<br>`equipamento-form.component.ts` | Fechado |
| **#47** [FEATURE] Tela Equipes | `027b1cf` | `equipe-form.component.ts`<br>

<br>`equipe-lista.component.ts`<br>

<br>`equipe.service.ts` | Fechado |
| **#46** [FEATURE] Tela Ambulâncias | `816d461` | `ambulancia-form.component.ts`<br>

<br>`ambulancia-lista.component.ts` | Fechado |
| **#45** [FEATURE] Tela Ocorrências e Despacho | `fc589ba`<br>

<br>`0f90e4f` | `ocorrencia-lista.component.ts`<br>

<br>`ocorrencia-form.component.ts`<br>

<br>`OcorrenciaService.java` | Fechado |
| **#42** [FEAT] Padrão Adapter - Calculador de Rota | `9170bcd` | `RotaController.java`<br>

<br>`HaversineAdapter.java`<br>

<br>`HaversineCalculador.java` | Fechado |
| **#40** [FEAT] Padrão Factory Method - Relatórios | `47c7925` | `RelatorioFactory.java`<br>

<br>`RelatorioController.java`<br>

<br>`RelatorioImpl.java` | Fechado |
| **#38** [FEATURE] Tela de Gerenciamento de Usuários | `a5b3038`<br>

<br>`e2841c8`<br>

<br>`f35ce9d` | `gerenciamento-usuario.ts`<br>

<br>`usuario.service.ts`<br>

<br>`UsuarioController.java` | Fechado |
| **#37** [FEAT] Padrão Iterator - Ocorrências | `253cbd8` | `OcorrenciaIteradorPorPrioridade.java`<br>

<br>`ListaOcorrencias.java` | Fechado |
| **#35** [FEAT] Padrão Singleton - GestorDeEmergencia | `18d04c9` | `GestorDeEmergencia.java`<br>

<br>`DashboardResumoDTO.java` | Fechado |
| **#26** [FEAT] Padrão Decorator - Equipar ambulância | `4ba33a7` | `AmbulanciaController.java`<br>

<br>`AmbulanciaEquipadaDTO.java` | Fechado |
| **#25** [FEAT] Padrão Decorator - Estrutura Equipamentos | `54d488c` | `AmbulanciaEquipavel.java`<br>

<br>`ItemMedicoDecorator.java`<br>

<br>`DesfibrilladorDecorator.java` | Fechado |
| **#24** [REFACTOR] Ambulância CrudBaseService | `b8b6d54` | `AmbulanciaController.java`<br>

<br>`AmbulanciaService.java` | Fechado |
| **#23** [REFACTOR] Padrão Template Method - CrudBase | `0f12c0a` | `CrudBaseService.java`<br>

<br>`EntidadeGerenciavel.java` | Fechado |
| **#14** [FEATURE] Telas de Autenticação e Cadastro | `fcb17c2`<br>

<br>`3cf1f58`<br>

<br>`c0f2540` | `login-form.component.ts`<br>

<br>`auth.service.ts`<br>

<br>`SecurityFilter.java` | Fechado |
| **#13** [FEATURE] Verificação de regras de negócio | `980edc1` | `AmbulanciaService.java`<br>

<br>`EquipeService.java` | Fechado |
| **#12** e **#11** [FEATURE] Endpoints FindBy e Listagem | `3bef8bb` | `*Controller.java` (Múltiplos)<br>

<br>`*Repository.java` (Múltiplos) | Fechado |
| **#10** [FEATURE] Permissões (Standby) | `478bcc3` | `PermisssaoController.java`<br>

<br>`Permissao.java` | Fechado |
| **#9** [FEATURE] CRUD Ocorrência (API) | `6f6a5d6` | `OcorrenciaController.java`<br>

<br>`Ocorrencia.java` | Fechado |
| **#6** [FEATURE] CRUD Ambulância (API) | `e363022` | `AmbulanciaController.java`<br>

<br>`Ambulancia.java` | Fechado |
| **#5** [FEATURE] CRUD Funcionários (API) | `73eab1c`<br>

<br>`6e86551` | `FuncionarioController.java`<br>

<br>`Funcionario.java` | Fechado |
| **#4** [FEATURE] CRUD Permissões (API) | `478bcc3` | `PermisssaoController.java`<br>

<br>`PermissaoService.java` | Fechado |
| **#3** [FEATURE] CRUD Equipamento (API) | `b9d02c3` | `EquipamentoController.java`<br>

<br>`Equipamento.java` | Fechado |
| **#2** [FEATURE] CRUD Categorias (API) | `994bbb4` | `CategoriaController.java`<br>

<br>`Categoria.java` | Fechado |
| **#1** [FEATURE] CRUD Usuários (API) | `4d8fa5b`<br>

<br>`e52a822`<br>

<br>`d12ff4a` | `UsuarioController.java`<br>

<br>`Usuario.java`<br>

<br>`SecurityConfig.java` | Fechado |

---

Se precisar adicionar mais alguma informação ou ajustar o formato, é só avisar!
