Visão Geral Este projeto é um site completo desenvolvido em Java com Spring Boot, que oferece funcionalidades de cadastro, login, envio de e-mails e recuperação de senha. O objetivo principal é proporcionar uma experiência de usuário fluida e segura, permitindo que os usuários se registrem, façam login e obtenham suas senhas de maneira fácil e eficiente.

Funcionalidades

Cadastro de Usuários: Permite que novos usuários se registrem no site fornecendo informações básicas como nome, e-mail e senha. Login: Permite que os usuários cadastrados façam login no site utilizando seu e-mail e senha. Envio de E-mails: Envia e-mails de confirmação de cadastro, notificações e instruções para recuperação de senha. Recuperação de Senha: Permite que os usuários recebam um email contém as seguintes informações, USuário: "usuario", Senha="senha do usuario" Tecnologias Utilizadas

Java: Linguagem de programação principal. Spring Boot: Framework utilizado para construção do backend. Thymeleaf: Motor de templates utilizado para renderizar as páginas HTML. Spring Data JPA: Utilizado para interagir com o banco de dados. MySQL: Banco de dados utilizado para armazenar as informações dos usuários. Maven: Ferramenta de gerenciamento de dependências e build do projeto.

Configuração do Projeto Pré-requisitos Java 8+ Maven 3+ MySQL Passos para Configuração Clone o repositório:

bash Copiar código git clone <URL do Repositório> cd Configuração do Banco de Dados:

Crie um banco de dados MySQL: sql Copiar código CREATE DATABASE nome_do_banco_de_dados; Configure as credenciais do banco de dados no arquivo application.properties: properties Copiar código spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco_de_dados spring.datasource.username=seu_usuario spring.datasource.password=sua_senha spring.jpa.hibernate.ddl-auto=update Build e Execute o Projeto:

bash Copiar código mvn clean install mvn spring-boot:run Estrutura do Projeto css Copiar código

Pacotes

controller: Contém os controladores responsáveis por gerenciar as requisições HTTP. model: Contém as entidades JPA. repository: Contém as interfaces de repositório que estendem JpaRepository. dto : Contém as classes Data Transfer Objects (DTOs) utilizadas para transferência de dados entre as camadas da aplicação. service: Contém as classes de serviço que implementam a lógica de negócio.

Funcionalidades Detalhadas Cadastro de Usuários Endpoint: /register Método: POST Descrição: Registra um novo usuário no sistema. Parâmetros: nome (String) email (String) senha (String) Login Endpoint: /login Método: POST Descrição: Autentica um usuário no sistema. Parâmetros: email (String) senha (String) Envio de E-mails Endpoint: /send-email Método: POST

Descrição: Envia um e-mail de confirmação ou recuperação de senha. Parâmetros: email (String) tipo (String) - confirmation ou recovery Recuperação de Senha Endpoint: /reset-password Método: POST Descrição: Envia um e-mail com instruções para redefinição de senha. Parâmetros: email (String)

Notas Adicionais

Certifique-se de que o servidor de e-mail esteja configurado corretamente para envio de e-mails. Utilize variáveis de ambiente para armazenar credenciais sensíveis, como senhas e chaves de API. Contribuição Faça um fork do projeto. Crie uma branch para sua feature (git checkout -b feature/nova-feature). Faça commit de suas alterações (git commit -am 'Adiciona nova feature'). Faça push para a branch (git push origin feature/nova-feature). Abra um Pull Request.

Licença Este projeto é licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.
