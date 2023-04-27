# Base image com Node.js
FROM node:14-alpine 

# Cria o diretório da aplicação
WORKDIR /app

# Copia o arquivo de dependências
COPY package*.json ./

# Instala as dependências
RUN npm install

# Copia os arquivos da aplicação
COPY . .

# Compila a aplicação React
RUN npm run build

# Expõe a porta da aplicação
EXPOSE 3000

# Define o comando de inicialização
CMD [ "npm", "start" ]