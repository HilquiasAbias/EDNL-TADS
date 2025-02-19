package shortestpath;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        String nomeArquivo = "/home/matheus/dev/tads/EDNL-TADS/src/shortestpath/labirinto.dat"; // colocar valor absoluto do arquivo

        try (InputStream is = new FileInputStream(nomeArquivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            List<int[]> linhas = new ArrayList<>();
            String linha;

            while ((linha = br.readLine()) != null) {
                int[] linhaInt = new int[linha.length()]; // Criar array de inteiros para essa linha
                
                for (int i = 0; i < linha.length(); i++) {
                    char caractere = linha.charAt(i);

                    if (Character.isDigit(caractere)) { // Apenas números são convertidos
                        linhaInt[i] = Character.getNumericValue(caractere);
                    } else {
                        System.err.println("Caractere inválido na posição [" + linhas.size() + "][" + i + "]: " + caractere);
                        linhaInt[i] = -1; // Define um valor padrão para caracteres inválidos
                    }
                }

                linhas.add(linhaInt);
            }

            // Converter a lista para matriz
            int[][] matriz = linhas.toArray(new int[0][]);

            MazeSolver ms = new MazeSolver(matriz);

            ms.test();

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}


