# Trabalho 01
	
Desenvolver  um  robô  que  segue  uma  linha  preta  no  piso e circule obstaculos,  conforme  cenários/circuitos  apresentados pelo professor em sala de aula. 
Implementar utilizando a técnica polling.

Tempo de realização do percurso:                    00:01:29

# Trabalho 02

Desenvolver um robô capaz de percorrer por uma matriz 7 x 7 com obstaculos. Existem posições com cores que devem ser lembradas. Após realizar o mapeamento de todo o cenário, o robô deve retornar a origem.
Assim que retornar a sua posição inicial, deve ser utilizado o algorítmo de dijkstra para visitar as posições marcadas com cores.

Tempo de mapeamento do cenário:                     00:04:19
Tempo de caminhamento entre posições marcadas:      00:02:41

# Trabalho 03

Desenvolver um robô que seja capaz de obter informações sobre a sua localização, pontos de referência da meta e dos obstáculos, a partir de imagens capturadas por uma câmera posicionada sobre o ambiente.
O algorítmo de processamento de imagem utiliza a biblioteca Java Advanced Imaging. A aplicação deve:
    * Mapear e navegar pelo ambiente a partir de uma imagem de entrada 
    * Utilizar processamento de imagens para descobrir robô, obstáculos e objetivo. A partir da interpretação da imagem, você deve representar os elementos encontrados através de uma matriz. Ou seja, você precisa indicar a localização do robô, obstáculos e qual será o caminho que o robô fará para atingir o objetivo. 
    * Utilizar o algoritmo wavefront para realizar o planejamento e navegação do robô
