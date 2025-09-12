# O problema do ônibus

## Contexto
Este problema foi originalmente inspirado no ônibus do Senado do Wellesley College,
proposto por Downey (2008) e adaptado para este trabalho. A situação descrita é a
seguinte: os alunos da Feevale chegam ao ponto de ônibus e aguardam pelo BUS
FEEVALE. Quando o ônibus chega, todos os passageiros que já estão esperando
invocam a ENTRADA no ônibus, mas qualquer aluno que chegar durante o embarque
deverá aguardar pelo próximo ônibus.
Os alunos devem ser criados de forma aleatória e frequentar as aulas, cuja duração é
controlada pela universidade, de forma que todas as disciplinas concorrentes terminem
simultaneamente. Essa duração deve ser aleatória, variando entre 2 e 10 minutos. Ao
término das aulas, os alunos devem se dirigir ao ponto de ônibus. Importante que as
disciplinas iniciem e finalizem no mesmo horário.
Os ônibus, por sua vez, devem ser gerados com uma frequência aleatória entre 2 e 3
minutos. Cada ônibus tem uma capacidade máxima de 50 pessoas; caso haja mais de
50 passageiros aguardando, o excedente precisará esperar pelo próximo ônibus. Após
o embarque de todos os passageiros que estavam no ponto, o ônibus pode invocar a
PARTIDA DO ÔNIBUS. Caso o ônibus chegue e não haja passageiros no ponto, ele
deve partir imediatamente.
Escreva um código de sincronização que atenda a todas essas restrições, utilizando
obrigatoriamente threads para representar os alunos e os ônibus.
Referencias
Downey, A. (2008). The little book of semaphores (Vol. 2, No. 2). Green Tea Press.

## O Trabalho
O problema do ônibus, é um problema clássicos no campo da programação concorrente.
Servindo para comparar vários formalismos e provenientes de programas concorrentes.
São problemas suficientemente simples para ser tratado, ainda que bastante desafiante.
Linguagens de desenvolvimento
O trabalho poderá ser desenvolvido nas seguintes linguagens:
• C
• C ++
• Java
• C#
O que deve ser entregue junto com o trabalho:
1. Apresentação desse ser gravada e postada no Yotube;
2. Redação de relatório técnico;
a. Documentação (tais como, fluxos, diagramas de classes, etc);
b. Descrição e apresentação dos algoritmos;
3. Código Fonte, o relatório deve conter uma seção com as explicações de como
executar o projeto/fonte;
4. O Relatório deve conter um exemplo de aplicação dos problemas estudados.
