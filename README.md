# Microservices-and-Communication

Para a implementação do projeto eu escolhi utilizar o framework Spring Boot por ser uma das mais utilizadas no mercado e 
também pela facilidade de implementações e configurações;

Obtei por utilizar dependencias da familia do Spring, como o feign Client para prover a comunicação entre as API's;

Utilizei as bibliotecas mapstruct e lombok para diminuir a verbosidade e também a agilidade no desenvolvimento;

Utilizei o banco de dados relacional PostgreSQL;

Utilizei o test container seguindo o mesmo padrão de utilização de docker para testar os recursos dos serviços das API's;

Optei pela implementação de um novo docker-compose para a unificação da inicialização das API's e de suas dependencias, como banco de dados e redis;

Para a parte de documentação e padronização do projeto utilizei o Swagger;


# Instruções da Aplicação:

1- Build do projeto para gerar o arquivo .jar na pasta target:
mvn clean install -U

2- Executar o build da imagem de cada projeto:
docker build -t user-api .
e
docker build -t order-api .

3 - Rodar o docker-compose (raiz projeto):
docker-compose up
