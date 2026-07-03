# Sistema de Academia - Sistemas Distribuídos

Este projeto é um sistema distribuído para gerenciamento de uma academia. Ele é composto por múltiplos serviços integrados e uma interface web, utilizando tecnologias como Java (Spring Boot) e Python (Flask).

## 🏗️ Arquitetura do Sistema

O sistema é dividido nos seguintes módulos:

* **Serviço 1 (`prj_academia_serv_1`)**: Desenvolvido em Java com Spring Boot.
* **Serviço 2 (`prj_academia_serv_2`)**: Desenvolvido em Java com Spring Boot.
* **Serviço 3 (`prj_academia_serv_3`)**: Desenvolvido em Python com Flask.
* **Serviço Gestor (`prj_academia_servGestor`)**: Desenvolvido em Java com Spring Boot, responsável pela gestão central e orquestração do sistema.
* **Cliente Web (`prj_academia_servWeb`)**: Interface de navegação para o cliente final.

## 🚀 Como Executar o Projeto

Abaixo estão as instruções para configurar e rodar cada um dos serviços na sua máquina local. Recomendamos abrir terminais separados para rodar cada serviço simultaneamente.

### 📋 Pré-requisitos
* **Java** (JDK configurado para os projetos Spring)
* **Maven** ou **Gradle** (para compilar os projetos Java)
* **Python 3+**
* Node.js/npm (caso o Cliente Web necessite)

---

### ☕ Executando os Serviços Java (Spring)
Isso se aplica aos projetos: `prj_academia_serv_1`, `prj_academia_serv_2` e `prj_academia_servGestor`.

1. Navegue até o diretório do serviço desejado através do seu terminal. Exemplo:
   ```bash
   cd prj_academia_servGestor
   ```
2. Execute o projeto (utilizando Maven):
   ```bash
   mvn spring-boot:run
   ```
   *(Caso utilize Gradle, o comando é `./gradlew bootRun`)*

---

### 🐍 Executando o Serviço Python (Flask)
O `prj_academia_serv_3` utiliza Python e Flask. É necessário criar um ambiente virtual (env) para não conflitar com as dependências do sistema.

1. Navegue até o diretório do serviço:
   ```bash
   cd prj_academia_serv_3
   ```
2. Crie o ambiente virtual:
   ```bash
   python -m venv venv
   ```
3. Ative o ambiente virtual:
   * **Windows:**
     ```bash
     venv\Scripts\activate
     ```
   * **Linux/Mac:**
     ```bash
     source venv/bin/activate
     ```
4. Instale as dependências necessárias:
   ```bash
   pip install -r requirements.txt
   ```
5. Inicie a aplicação Flask:
   ```bash
   flask run
   ```
   *(Ou `python main.py` dependendo de como o ponto de entrada está configurado).*

---

### 🌐 Executando o Cliente Web
O `prj_academia_servWeb` é a interface de comunicação visual do projeto.

1. Navegue até o diretório:
   ```bash
   cd prj_academia_servWeb
   ```
2. Dependendo de como o projeto foi estruturado (HTML estático, React, Angular, etc.), você pode precisar instalar as dependências e iniciar o servidor de desenvolvimento:
   ```bash
   npm install
   npm start
   ```
   *(Se for apenas um projeto web simples, basta abrir o arquivo principal `.html` no seu navegador).*
