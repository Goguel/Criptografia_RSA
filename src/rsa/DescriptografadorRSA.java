package rsa;

public class DescriptografadorRSA {

    private final long d;
    private final long n;

    // guarda a chave privada (d, n)
    public DescriptografadorRSA(long d, long n) {
        if (d < 0) {
            throw new IllegalArgumentException("O expoente privado d deve ser não negativo.");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("O módulo n deve ser maior que zero.");
        }
        this.d = d;
        this.n = n;
    }

    // descobre a chave privada D a partir da chave pública (e, n) fatorando n
    public static long descobrirChavePrivada(long e, long n) {
        long p = -1;
        long q = -1;

        // acha os dois fatores primos p e q de n
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                p = i;
                q = n / i;
                break;
            }
        }

        if (p == -1) {
            throw new IllegalArgumentException("Não foi possível fatorar n = " + n);
        }

        long phi = (p - 1) * (q - 1);
        return inversoModular(e, phi);
    }

    // calcula o inverso modular de 'a' em relação a 'm' usando Euclides estendido
    public static long inversoModular(long a, long m) {
        long t = 0;
        long novoT = 1;
        long r = m;
        long novoR = a % m;

        while (novoR != 0) {
            long quociente = r / novoR;

            long tempT = t - quociente * novoT;
            t = novoT;
            novoT = tempT;

            long tempR = r - quociente * novoR;
            r = novoR;
            novoR = tempR;
        }

        if (r != 1) {
            throw new IllegalArgumentException("Não existe inverso modular para os valores informados.");
        }

        if (t < 0) {
            t += m;
        }
        return t;
    }

    // faz (base^expoente) % modulo usando exponenciação binária pra não dar overflow
    public static long exponenciacaoModular(long base, long expoente, long modulo) {
        long resultado = 1;
        long baseAtual = base % modulo;
        if (baseAtual < 0) {
            baseAtual += modulo;
        }

        long exp = expoente;
        while (exp > 0) {
            // se o expoente for ímpar, multiplica no resultado já tirando o resto
            if (exp % 2 == 1) {
                resultado = (resultado * baseAtual) % modulo;
            }
            baseAtual = (baseAtual * baseAtual) % modulo;
            exp = exp / 2;
        }

        return resultado;
    }

    // descriptografa um bloco só aplicando c^d % n
    public long descriptografarBloco(long blocoCifrado) {
        return exponenciacaoModular(blocoCifrado, this.d, this.n);
    }

    // quebra a string nos hífens e descriptografa cada número
    public long[] descriptografarMensagem(String mensagemCifrada) {
        if (mensagemCifrada == null || mensagemCifrada.isBlank()) {
            return new long[0];
        }

        String[] partes = mensagemCifrada.trim().split("-");
        long[] codigosDescriptografados = new long[partes.length];

        for (int i = 0; i < partes.length; i++) {
            long blocoCifrado = Long.parseLong(partes[i].trim());
            codigosDescriptografados[i] = descriptografarBloco(blocoCifrado);
        }

        return codigosDescriptografados;
    }

    public long getD() {
        return d;
    }

    public long getN() {
        return n;
    }
}
