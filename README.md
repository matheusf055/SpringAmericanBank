# Projeto Banco Americano

Este repositório contém o código-fonte do projeto "Banco Americano", desenvolvido durante o estágio na Compass UOL. O projeto é composto por três microserviços: MS Customer, MS Calculate e MS Payment. Ele visa oferecer funcionalidades de gestão de usuários, cálculo de pontos e simulação de pagamentos em um ambiente distribuído.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Java 17**: Linguagem de programação principal utilizada.
- **Docker**: Para empacotamento e distribuição dos microserviços em containers.
- **MySQL**: Banco de dados relacional para persistência de dados.
- **RabbitMQ**: Mensageria para comunicação assíncrona entre microserviços.
- **AWS S3**: Armazenamento de imagens de perfil dos usuários em nuvem.
- **Eureka**: Serviço de descoberta para registro e localização de serviços na rede.
- **OpenFeign**: Cliente HTTP para comunicação entre microserviços.
- **Maven**: Gerenciamento de dependências e build do projeto.
- **JUnit**: Framework para testes unitários em Java.

## Microserviços

### MS Customer

Responsável pela gestão de dados de usuários e seus pontos acumulados.

- Endpoints principais: `POST`, `GET`, `PUT`, `DELETE` para gerenciar usuários.
- Utiliza MySQL para persistência de dados dos usuários.
- Recebe imagens em base64 e armazena a URL da imagem no AWS S3.
- Integração com RabbitMQ para atualização assíncrona dos pontos acumulados.
- Registrado no Eureka para descoberta e comunicação entre microserviços.

### MS Calculate

Calcula os pontos totais a serem acumulados em uma compra com base em regras definidas.

- Endpoints principais: `POST`, `GET` para calcular pontos e gerenciar regras.
- Define regras que determinam a paridade de acumulação de pontos por categoria de compra.
- Utiliza MySQL para persistência das regras de cálculo.
- Registrado no Eureka para descoberta e comunicação entre microserviços.
- Utiliza OpenFeign para comunicação com MS Payment.

### MS Payment

Simula o pagamento de uma compra com cartão de crédito do cliente.

- Endpoints principais: `POST`, `GET` para gerenciar pagamentos.
- Utiliza MySQL para persistência de dados dos pagamentos.
- Gera um UUID para o campo `ID` de pagamento.
- Chama o MS Calculate para calcular os pontos acumulados após salvar o pagamento.
- Envia uma mensagem via RabbitMQ para o MS Customer após receber o cálculo dos pontos.
- Registrado no Eureka para descoberta e comunicação entre microserviços.
- Utiliza OpenFeign para comunicação com o Ms Calculate.

## Testes e Cobertura

Resultados dos testes utilizando Jacoco:

![e480d632-13f4-487d-a899-2d456fdded73](https://github.com/matheusf055/PbAbrilDes3_MatheusCesar/assets/148828351/44f81ac5-feae-4a4b-856c-e1cccd7dc152)

![ca729fb6-c959-4814-bc2d-141e6cec8e8b](https://github.com/matheusf055/PbAbrilDes3_MatheusCesar/assets/148828351/6841adfc-97f5-4720-877f-06d9c03e9297)

![1461091a-e6e3-42ac-8fd5-b70effc45593](https://github.com/matheusf055/PbAbrilDes3_MatheusCesar/assets/148828351/fbcfcbcf-ffd9-4947-a6cf-6d217cad29ad)

## Execução do Projeto

Para executar o projeto localmente, siga os passos abaixo:

1. Clone este repositório.
2. Configure os arquivos de propriedades (`application.yml`) para cada microserviço conforme necessário (por exemplo, configurações de banco de dados, RabbitMQ, AWS S3).
3. Utilize Docker para empacotar cada microserviço em um container separado.
4. Execute os containers utilizando `docker-compose up --build` para facilitar a inicialização e interconexão dos microserviços.
