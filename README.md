# 📈 Stock & FII Price Monitor

Aplicação desenvolvida em **Spring Boot** para monitorar automaticamente
preços de **ações e FIIs da B3** e enviar alertas por email quando o
preço atingir um valor configurado.

O sistema consulta periodicamente os preços utilizando uma **API
gratuita de mercado financeiro** e compara o preço atual com o preço
alvo definido na configuração da aplicação.

------------------------------------------------------------------------

# 🚀 Tecnologias utilizadas

-   Java 21
-   Spring Boot 3
-   Spring WebFlux
-   Scheduler (`@Scheduled`)
-   JavaMailSender
-   Lombok
-   API de mercado financeiro **BRAPI (https://brapi.dev)**

------------------------------------------------------------------------

# 🌐 API utilizada

Este projeto utiliza a **API gratuita da BRAPI**:

https://brapi.dev

A BRAPI fornece dados de mercado da **B3 (Bolsa de Valores do Brasil)**
como:

-   preço atual das ações
-   variação diária
-   volume negociado
-   informações do ativo

⚠️ Observação:

O plano gratuito da BRAPI possui algumas limitações, como por exemplo:

-   limite de requisições
-   consulta de **apenas um ativo por requisição**

Por isso o sistema consulta cada ativo individualmente.

------------------------------------------------------------------------

# ⚙️ Como funciona

1.  As **ações e FIIs monitorados já estão previamente definidos** no
    arquivo `application.yml`.
2.  O **email que receberá os alertas também é configurado no arquivo de
    propriedades**.
3.  A aplicação executa um **scheduler automático** que consulta os
    preços periodicamente.
4.  Para cada ativo monitorado:
    -   o sistema consulta o preço atual na BRAPI
    -   compara com o preço alvo configurado
5.  Se o preço atual for **menor ou igual ao preço alvo**, um email é
    enviado.

Fluxo do sistema:

Scheduler\
↓\
Consulta BRAPI\
↓\
Obtém preço atual do ativo\
↓\
Compara com preço alvo configurado\
↓\
Se atingir → envia email

------------------------------------------------------------------------

# 📂 Estrutura do projeto

src/main/java/com/example/stockalert

client\
└─ StockPriceClient.java

scheduler\
└─ StockScheduler.java

service\
└─ EmailService.java

dto\
├─ BrapiResponse.java\
└─ BrapiStock.java

properties\
├─ StockProperties.java\
├─ BrapiProperties.java\
└─ EmailAlertProperties.java

------------------------------------------------------------------------

# 🔧 Configuração

## 1️⃣ Ativos monitorados (ações e FIIs)

Os ativos monitorados são definidos previamente no arquivo:

``` yaml
application.yml
```

Exemplo:

``` yaml
stocks:
  targets:
    PETR4: 40
    VALE3: 60
    ITUB4: 28
    BBSE3: 30
    VULC3: 18
```

Cada ativo possui um **preço alvo**.\
Quando o preço atual for **menor ou igual ao valor definido**, o alerta
será disparado.

------------------------------------------------------------------------

## 2️⃣ Token da BRAPI

``` yaml
brapi:
  token: SEU_TOKEN_AQUI
```

------------------------------------------------------------------------

## 3️⃣ Email que receberá os alertas

O email de destino também é definido no `application.yml`:

``` yaml
alerts:
  email: seuemail@gmail.com
```

Esse email será utilizado para receber os alertas sempre que um ativo
atingir o preço desejado.

------------------------------------------------------------------------

# ⏱️ Scheduler

A consulta aos preços é feita automaticamente por um scheduler.

Exemplo:

``` java
@Scheduled(fixedDelay = 900000)
```

Isso significa:

Consulta a cada **15 minutos**.

------------------------------------------------------------------------

# 📧 Exemplo de email recebido

Assunto:

📈 Ações que atingiram o preço alvo

Conteúdo:

As seguintes ações atingiram o preço desejado:

PETR4 → R\$ 39.80\
VALE3 → R\$ 58.50

------------------------------------------------------------------------

# ▶️ Executando o projeto

``` bash
mvn spring-boot:run
```

ou

``` bash
mvn clean package
java -jar target/stock-alert.jar
```

------------------------------------------------------------------------

# 👨‍💻 Autor

Projeto desenvolvido com **Spring Boot + WebFlux + integração
com APIs externas** para automação de monitoramento do mercado financeiro.
