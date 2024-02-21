# Bank-api
Aplicação para consulta de saldos e transferências entre contas.


## Dependências e tecnologias utilizadas
| Dependency | Version      |
|------------|--------------|
| Java       | 21           |
| SpringBoot | 3.2.2        |
| Maven      | \>= 3.6.0    |
| Docker     | \>= 19.03.12 |
| MySQL      | 8.0          |


## Build
O projeto utiliza o Docker e Docker Compose. Para gerar a imagem basta utilizar o comando:
```shell
sh generate-image.sh 
```
O comando irá gerar a imagem e executar os testes automatizados.

É possível compilar o projeto utilizando o comando Maven diretamente:
```shell
mvn clean package
```
Ou ainda rodar somente os testes:
```shell
mvn clean test
```


## Execução
Para executar o projeto e suas dependências basta executar o comando abaixo para subir os containers criados:
```shell
docker compose up
```


## Decisões Técnicas
Foi utilizada a linguagem de programação Java na versão 21 juntamente com o gerenciador de dependências Maven, devido a maior familiaridade e experiência de uso.
Foi utilizado o framework Spring Boot.

Foi utilizado o JUnit 5 para a implementação dos testes automatizados devido a experiência prévia e seu vasto uso nos projetos Java.




