# LinkedIndex
Trabalho de estruturas de Dados
API REST Simples com o uso do Vert.x 3 e as dependências do CORE e WEB.

## Run
`Importe o projeto para a IDE de sua preferência execute a classe Run.java`

## Para acessar a página web utilize 

`http://localhost:9000/assets/index.html`

## Executando os Endpoints:

### Listar todos os contatos:

`http://localhost:9000/api/products GET`

### Adicionar contato:

`http://localhost:9000/api/products POST`

Seguindo o seguinte json:

``````
{
    "name": "Pringles",
    "label": "Nestle",
    "type": "Salgadinho",
    "value": "12"
}
