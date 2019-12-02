# LinkedIndex
Trabalho de estruturas de Dados
API REST Simples com o uso do Vert.x 3 e as dependências do CORE e WEB.
Desenvolvido com Java 8 e Maven.

### Estrutura
```

├── src/
│   ├── main/
│      ├── java/
│          ├── model/
│              └── Product.java
│          ├── data/
│              └── DataProducts.java
│          ├── indexation/
│              └── DirInvertedIndex.java
│          ├── main/
│              └── Run.java
│          ├── server/
│              └── ServerVerticle.java
│   ├── resources/
        ├── assets/
            └── index.html
```

## Run
`Importe o projeto para a IDE de sua preferência execute a classe Run.java`

## Para acessar a página web utilize 

`http://localhost:9000`

## Executando os Endpoints:

### Listar todos os Produtos:

`http://localhost:9000/api/products GET`

### Adicionar Produto:

`http://localhost:9000/api/products POST`

Seguindo o seguinte json:


`{
    "name": "Pringles",
    "label": "Nestle",
    "type": "Salgadinho",
    "value": "12"
}`

### Buscar Produto por Codigo:

`http://localhost:9000/api/id POST`

### Buscar Produto por Marca:

`http://localhost:9000/api/label POST`

### Buscar Produto por Tipo:

`http://localhost:9000/api/typel POST`

### Buscar Produto por Tipo e Marca:

`http://localhost:9000/api/typelabel POST`
