package rsa;

import java.util.HashMap;
import java.util.Map;

public class TabelaConversao {

    private final Map<Integer, String> tabela = new HashMap<>();

    // monta a tabela de conversão passada nos slides (pula 20, 30 e 40 pra não ter zero)
    public TabelaConversao() {
        tabela.put(11, "A");
        tabela.put(12, "B");
        tabela.put(13, "C");
        tabela.put(14, "D");
        tabela.put(15, "E");
        tabela.put(16, "F");
        tabela.put(17, "G");
        tabela.put(18, "H");
        tabela.put(19, "I");
        tabela.put(21, "J");
        tabela.put(22, "K");
        tabela.put(23, "L");
        tabela.put(24, "M");
        tabela.put(25, "N");

        tabela.put(26, "O");
        tabela.put(27, "P");
        tabela.put(28, "Q");
        tabela.put(29, "R");
        tabela.put(31, "S");
        tabela.put(32, "T");
        tabela.put(33, "U");
        tabela.put(34, "V");
        tabela.put(35, "W");
        tabela.put(36, "X");
        tabela.put(37, "Y");
        tabela.put(38, "Z");

        tabela.put(39, " ");
        tabela.put(41, ".");
        tabela.put(42, ",");
        tabela.put(43, "!");
        tabela.put(44, "?");
    }

    // junta todos os blocos descriptografados numa string só
    public String concatenarBlocos(long[] blocos) {
        StringBuilder sequencia = new StringBuilder();
        for (long bloco : blocos) {
            sequencia.append(bloco);
        }
        return sequencia.toString();
    }

    // pega a sequência concatenada e vai lendo de 2 em 2 dígitos pra traduzir usando a tabela
    public String converterParaTexto(long[] blocos) {
        String sequencia = concatenarBlocos(blocos);
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i + 1 < sequencia.length(); i += 2) {
            int codigo = Integer.parseInt(sequencia.substring(i, i + 2));
            String simbolo = tabela.get(codigo);
            if (simbolo != null) {
                texto.append(simbolo);
            } else {
                texto.append("[").append(codigo).append("]");
            }
        }

        // caso sobre algum dígito sozinho no final
        if (sequencia.length() % 2 != 0) {
            texto.append("[").append(sequencia.charAt(sequencia.length() - 1)).append("]");
        }

        return texto.toString();
    }
}
