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

Issue / Funcionalidade,Commit(s),Principais Arquivos Modificados,Status
#81 [FEATURE/BUGFIX] Refatoração Fluxo de Despacho,0f90e4fb137fde,OcorrenciaController.javaDespachoDTO.javaocorrencia-lista.component.ts,Fechado
#79 [BUG] Inconsistência Vínculo Usuário/Funcionário,847016ad16cc8cc06a102,cadastro-usuario.tsUsuarioService.javaGlobalExceptionHandler.java,Fechado
#77 [BUG] Sistema ejetando usuário (Erro 401),b137fde,SecurityConfig.javaauth.interceptor.ts,Fechado
#71 [FEATURE] Tela de Funcionários (CRUD),76436544ed31a8a0f63921fbefb4,funcionario.service.tsfuncionarios-list.tsFuncionarioController.java,Fechado
#52 [REFACTOR] Reorganização dos Enums e Estrutura,096c621e636494,StatusAmbulancia.javaGravidadeOcorrencia.javaAmbulanciaController.java,Fechado
#51 [FEATURE] Gerenciamento de Ambulâncias (Front),816d461,ambulancia-form.component.tsambulancia-lista.component.ts,Fechado
#50 [REFACTOR] Padrão Adapter - Geolocalização,9170bcd,CalculadorDeDistancia.javaHaversineAdapter.java,Fechado
#49 [FEATURE] Tela Relatório e Dashboard,18d04c947c7925,DashboardController.javaRelatorioController.java,Fechado
#48 [FEATURE] Tela Insumos e Equipamentos,7b36ae2,equipamento-lista.component.tsequipamento-form.component.ts,Fechado
#47 [FEATURE] Tela Equipes,027b1cf,equipe-form.component.tsequipe-lista.component.tsequipe.service.ts,Fechado
#46 [FEATURE] Tela Ambulâncias,816d461,ambulancia-form.component.tsambulancia-lista.component.ts,Fechado
#45 [FEATURE] Tela Ocorrências e Despacho,fc589ba0f90e4f,ocorrencia-lista.component.tsocorrencia-form.component.tsOcorrenciaService.java,Fechado
#42 [FEAT] Padrão Adapter - Calculador de Rota,9170bcd,RotaController.javaHaversineAdapter.javaHaversineCalculador.java,Fechado
#40 [FEAT] Padrão Factory Method - Relatórios,47c7925,RelatorioFactory.javaRelatorioController.javaRelatorioImpl.java,Fechado
#38 [FEATURE] Tela de Gerenciamento de Usuários,a5b3038e2841c8f35ce9d,gerenciamento-usuario.tsusuario.service.tsUsuarioController.java,Fechado
#37 [FEAT] Padrão Iterator - Ocorrências,253cbd8,OcorrenciaIteradorPorPrioridade.javaListaOcorrencias.java,Fechado
#35 [FEAT] Padrão Singleton - GestorDeEmergencia,18d04c9,GestorDeEmergencia.javaDashboardResumoDTO.java,Fechado
#26 [FEAT] Padrão Decorator - Equipar ambulância,4ba33a7,AmbulanciaController.javaAmbulanciaEquipadaDTO.java,Fechado
#25 [FEAT] Padrão Decorator - Estrutura Equipamentos,54d488c,AmbulanciaEquipavel.javaItemMedicoDecorator.javaDesfibrilladorDecorator.java,Fechado
#24 [REFACTOR] Ambulância CrudBaseService,b8b6d54,AmbulanciaController.javaAmbulanciaService.java,Fechado
#23 [REFACTOR] Padrão Template Method - CrudBase,0f12c0a,CrudBaseService.javaEntidadeGerenciavel.java,Fechado
#14 [FEATURE] Telas de Autenticação e Cadastro,fcb17c23cf1f58c0f2540,login-form.component.tsauth.service.tsSecurityFilter.java,Fechado
#13 [FEATURE] Verificação de regras de negócio,980edc1,AmbulanciaService.javaEquipeService.java,Fechado
#12 e #11 [FEATURE] Endpoints FindBy e Listagem,3bef8bb,*Controller.java (Múltiplos)*Repository.java (Múltiplos),Fechado
#10 [FEATURE] Permissões (Standby),478bcc3,PermisssaoController.javaPermissao.java,Fechado
#9 [FEATURE] CRUD Ocorrência (API),6f6a5d6,OcorrenciaController.javaOcorrencia.java,Fechado
#6 [FEATURE] CRUD Ambulância (API),e363022,AmbulanciaController.javaAmbulancia.java,Fechado
#5 [FEATURE] CRUD Funcionários (API),73eab1c6e86551,FuncionarioController.javaFuncionario.java,Fechado
#4 [FEATURE] CRUD Permissões (API),478bcc3,PermisssaoController.javaPermissaoService.java,Fechado
#3 [FEATURE] CRUD Equipamento (API),b9d02c3,EquipamentoController.javaEquipamento.java,Fechado
#2 [FEATURE] CRUD Categorias (API),994bbb4,CategoriaController.javaCategoria.java,Fechado
#1 [FEATURE] CRUD Usuários (API),4d8fa5be52a822d12ff4a,UsuarioController.javaUsuario.javaSecurityConfig.java,Fechado
