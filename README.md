# Chat em Linha de Comando com Servidor Multicliente

Exercicio extra de SD.

## Funcionamento

### Servidor

1. Compile e execute o código do servidor `ChatTCPServer.java`.
2. O servidor aguardará as conexões dos clientes.
3. Cada vez que um cliente se conecta, o servidor inicia uma nova instância de `ClientHandler` para lidar com a comunicação com esse cliente.
4. A classe `ClientHandler` gerencia a leitura das mensagens recebidas do cliente e a transmissão dessas mensagens para todos os clientes conectados.

### Cliente

1. Compile e execute o código do cliente `ChatTCPClient.java`.
2. O cliente se conectará ao servidor.
3. Duas threads são iniciadas:
    - `ReadThread`: Lê as mensagens enviadas pelo servidor e as exibe na tela.
    - `WriteThread`: Lê as mensagens do usuário através do teclado e as envia para o servidor.
4. As mensagens enviadas pelo cliente são recebidas pelo servidor e, em seguida, retransmitidas para todos os clientes conectados.
5. Para testar com outro cliente execute o `ChatTCPClient2.java`

## Como Usar

1. Execute o servidor primeiro para que ele esteja pronto para aceitar conexões.
2. Execute quantos clientes desejar em diferentes terminais ou máquinas.
3. O cliente pode digitar mensagens na linha de comando e pressionar Enter para enviá-las.
4. Todas as mensagens enviadas por um cliente serão exibidas para todos os outros clientes conectados.

## Considerações

Este é um exemplo simples de um sistema de chat em linha de comando usando sockets em Java.
