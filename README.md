# SAC - Sistema de Atendimento ao Cliente

# VISÃO GERAL

A BEIBE é uma empresa de Embelezamento Artificial, voltada ao público jovem e adulto que quer fazer a diferença no mundo.

A Embuste é a marca de beleza preferida dos brasileiros (Fonte: Embuste Estatística, 2018). Todos os nossos produtos tocam sua beleza. É assim que a marca transforma momentos simples em momentos embusteiros há menos de 0 anos.

Para continuar o sucesso da empresa e modernizar nosso atendimento ao cliente, deve-se desenvolver um sistema que envolve o registro e solução de reclamações, sugestões, críticas e problemas.

# OBJETIVOS

1. Informatizar o atendimento ao cliente;
2. Rastrear problemas envolvendo nossos produtos;
3. Resolver problemas;
4. Emitir relatórios gerenciais.

# ESPECIFICAÇÕES

O sistema deve possuir:

- **Perfis** : Três perfis devem ser permitidos no sistema:
  - _Cliente_: que são as pessoas que vão efetuar as reclamações, sugestões, etc;
  - _Funcionário_: que são as pessoas que resolvem as demandas entradas pelos clientes;
  - _Gerente_: que analisam todo o processo e emitem relatórios gerenciais.
- **Autocadastro** : Um usuário que não está logado no sistema pode efetuar seu autocadastro, indicando todos os seus dados e uma senha. Este usuário será cadastrado com o perfil de Cliente.
- **Login** : Só é possível interagir com o sistema após o usuário ter se logado. Deve-se informar e-mail/senha (portanto, o e-mail do usuário deve ser único). Ao entrar no sistema, a tela inicial é diferenciada para cada perfil, a saber:
  - _Cliente_: aparecem todos os seus atendimentos, ordenados de forma decrescente por data;
  - _Funcionário_: aparecem todos os atendimentos não resolvidos, ordenados de forma crescente por data;
  - _Gerente_: aparecem informações gerenciais, a saber:
    - A quantidade de atendimentos efetuados até o momento;
    - A quantidade de atendimentos em aberto (e a porcentagem em relação ao total);
    - Para cada tipo de atendimento, deve aparecer seu nome e os números: XX/YY, onde XX é a quantidade de atendimentos em aberto desta categoria e YY é a quantidade total de atendimentos desta categoria;
- **Logout** : O usuário deve poder finalizar sua sessão.

Separando-se por perfis, as funcionalidades disponíveis são descritas a seguir

## Cliente

- **Alteração de dados** : uma tela onde os dados do cliente podem ser alterados, exceto seu e-mail e CPF;
- **Listagem de todos os seus atendimentos** : uma tela onde são mostrados todos os atendimentos do cliente, ordenados de forma decrescente em relação à data. O cliente poderá ver seu atendimento de forma completa através de um link. Se o atendimento ainda estiver aberto, poderá removê-lo.
- **Criação de Atendimento** : uma tela onde o cliente pode criar um atendimento para a empresa;

## Funcionário

- **Listagem de todos os atendimentos em Aberto** : uma tela indicando todos os atendimentos em aberto, para que o funcionário possa atuar junto à eles, ordenado de forma crescente em relação à data. Se o atendimento estiver há mais de uma semana em aberto, deve ser mostrado em vermelho, indicando criticidade. O funcionário pode clicar e entrar diretamente na tela de resolução a partir daqui.
- **Listagem de todos os atendimentos** : uma tela indicando todos os atendimentos, ordenados de forma crescente. Se o atendimento estiver aberto há mais de uma semana, deve ser mostrado em vermelho. Se estiver aberto há menos de uma semana, deve ser mostrado em amarelo. Para os atendimentos ainda abertos, o funcionário pode clicar e entrar diretamente na tela de resolução a partir daqui.
- **Resolução do atendimento** : O usuário visualiza o atendimento e resolve-o. Ao fazer isso, o atendimento sai do estado _aberto_ e passa para o estado _finalizado_.
- **Cadastro de Categorias** : deve-se apresentar uma tela contendo a listagem de todas as categorias de produtos cadastrados no sistema. Nesta tela deve-se permitir a alteração, visualização e remoção destes registros. Ademais, deve-se apresentar um link/botão para que seja redirecionado para uma tela para inclusão de uma nova categoria.
- **Cadastro de Produtos** : deve-se apresentar uma tela contendo a listagem de todos os produtos cadastrados no sistema. Nesta tela deve-se permitir a alteração, visualização e remoção destes registros. Ademais, deve-se apresentar um link/botão para que seja redirecionado para uma tela para inclusão de um novo produto.

## Gerente

- **Cadastro de Funcionários/Gerentes** : deve-se apresentar uma tela ao gerente contendo a listagem de todos os usuários (Funcionários e Gerentes) cadastrados no sistema. Nesta tela deve-se permitir a alteração, visualização e remoção de algum destes registros (exceto o seu próprio). Ademais, deve-se apresentar um link/botão para que seja redirecionado para uma tela para inclusão de um novo funcionário/gerente.
- **Listagem de todos os atendimentos em Aberto** : uma tela indicando todos os atendimentos em aberto, ordenado de forma crescente em relação à data. Se o atendimento estiver há mais de uma semana em aberto, deve ser mostrado em vermelho, indicando criticidade.
- **Listagem de todos os atendimentos** : uma tela indicando todos os atendimentos, ordenados de forma crescente. Se o atendimento estiver aberto há mais de uma semana, deve ser mostrado em vermelho. Se estiver aberto há menos de uma semana, deve ser mostrado em amarelo.
- **Relatório de Funcionários:** (PDF) deve-se apresentar um relatório contendo todos os funcionários e seus dados;
- **Relatório de Produtos Mais Reclamados** : (PDF) deve-se apresentar um relatório com os três produtos da empresa que mais tiveram reclamações;
- **Relatório de Atendimentos em Aberto Por Data** : (PDF) deve-se apresentar um relatório com todos os atendimentos em aberto, indicando quem é o cliente (nome, e-mail e CPF), e os dados do atendimento. Deve-se filtrar por Data Início e Data Fim, da abertura do atendimento;
- **Relatório de Reclamações:** (PDF) deve-se emitir um relatório contendo somente as reclamações. Deve-se filtrar por situação: Todas, Em Aberto, Finalizadas.

## Dados

Os dados que são armazenados e manipulados são:

- **Cliente, Funcionário e Gerente** : nome completo, CPF, e-mail, endereço completo (rua, número, complemento, bairro, cep, cidade, estado), telefone, senha
- **Produto** : nome, categoria, descrição, peso (em gramas)
- **Categoria do Produto** : nome
- **Tipo do Atendimento** : nome
- **Atendimento** : data/hora, cliente, situação do atendimento, produto, tipo do atendimento, descrição (texto explicativo), solução apresentada (texto explicativo)

# REQUISITOS NÃO-FUNCIONAIS

- Deve-se usar as tecnologias vistas em aula: Servlets, JSP, JSTL, EL. Bem como as boas práticas de programação: MVC, DAO, etc;
- As tabelas devem estar normalizadas, inclusive as que não possuem cadastro e devem estar previamente preenchidas: Cidade e Estado;
- Deve-se usar um framework para desenvolvimento das telas. Sugere-se o **Bootstrap**. Também deve-se usar um conjunto de bibliotecas javascript para alterar o comportamento de telas, de forma dinâmica, quando necessário. Sugere-se o **jQuery** ;
- Senhas devem ser criptografadas;
- O leiaute deve ser agradável;
- Todas as datas e valores monetários devem ser entrados e mostrados no formato brasileiro;
- Todos os campos que tiverem formatação devem possuir máscara;
- Todas as datas deverão ser entradas através de calendários;
- Qualquer tipo de remoção deve ser confirmada antes de ocorrer.
