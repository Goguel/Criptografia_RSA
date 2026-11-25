package rsa;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // mensagens prontas do trabalho
    private static final String MENSAGEM_PADRAO =
            "2660-3343-3790-2198-1465-2886-904-2083-41-2660-3365-2663-"
            + "974-3984-904-1740-287-2886-3365-3639-3211-1740-287-3639-1980";
    private static final long D_PADRAO = 3097;
    private static final long N_PADRAO = 4187;

    private static final String MENSAGEM_CHAVE_PUBLICA =
            "4961-3072-9263-324-11807-4221-551-13714-1351-7742-13335-1748-"
            + "5453-1183-4591-6815-1748-4221-1501-131-263-3435-3382-13501-"
            + "7616-6966-393-13055-6309-1183-916-6904-11807-3371-3749";
    private static final long E_PUBLICA = 5347;
    private static final long N_PUBLICA = 14279;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Descriptografia RSA ===");
        System.out.println("Qual mensagem você quer descriptografar?");
        System.out.println("  1) Mensagem 1 - padrão (chave privada D = 3097, n = 4187)");
        System.out.println("  2) Mensagem 2 - quebrar pela chave pública (E = 5347, n = 14279)");
        System.out.println("  3) Digitar outra mensagem");
        System.out.print("Escolha [Enter = 1]: ");

        String opcao = scanner.nextLine().trim();

        String mensagemCifrada;
        long d;
        long n;

        if (opcao.equals("2")) {
            mensagemCifrada = MENSAGEM_CHAVE_PUBLICA;
            n = N_PUBLICA;
            d = DescriptografadorRSA.descobrirChavePrivada(E_PUBLICA, n);
            System.out.println("\nChave privada descoberta a partir de E = " + E_PUBLICA + ": D = " + d);
        } else if (opcao.equals("3")) {
            System.out.print("Digite a mensagem cifrada (números separados por hífen): ");
            mensagemCifrada = scanner.nextLine().trim();

            System.out.print("Digite o valor de n: ");
            n = Long.parseLong(scanner.nextLine().trim());

            System.out.print("Você tem a chave privada (D) ou pública (E)? [D/E]: ");
            String tipoChave = scanner.nextLine().trim().toUpperCase();

            if (tipoChave.equals("E")) {
                System.out.print("Digite o valor de E: ");
                long e = Long.parseLong(scanner.nextLine().trim());
                d = DescriptografadorRSA.descobrirChavePrivada(e, n);
                System.out.println("\nChave privada descoberta: D = " + d);
            } else {
                System.out.print("Digite o valor de D: ");
                d = Long.parseLong(scanner.nextLine().trim());
            }
        } else {
            // se apertar Enter ou escolher 1, usa a primeira mensagem como padrão
            mensagemCifrada = MENSAGEM_PADRAO;
            d = D_PADRAO;
            n = N_PADRAO;
        }

        DescriptografadorRSA rsa = new DescriptografadorRSA(d, n);
        long[] codigosDescriptografados = rsa.descriptografarMensagem(mensagemCifrada);

        TabelaConversao tabela = new TabelaConversao();
        String sequenciaConcatenada = tabela.concatenarBlocos(codigosDescriptografados);
        String mensagemDecodificada = tabela.converterParaTexto(codigosDescriptografados);

        System.out.println();
        System.out.println("Chave privada usada: D = " + d + ", n = " + n);
        System.out.println("Total de blocos: " + codigosDescriptografados.length);
        System.out.println();

        System.out.println("Blocos numéricos descriptografados:");
        System.out.println(Arrays.toString(codigosDescriptografados));
        System.out.println();

        System.out.println("Sequência concatenada:");
        System.out.println(sequenciaConcatenada);
        System.out.println();

        System.out.println("Mensagem decodificada:");
        System.out.println(mensagemDecodificada);

        scanner.close();
    }
}
