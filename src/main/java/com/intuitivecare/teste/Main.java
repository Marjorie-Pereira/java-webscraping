package com.intuitivecare.teste;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        
        // 1
        // arrange
        
        AnalisarPagina ap = new AnalisarPagina();
        String pastaAnexosCaminho = "C:\\development\\testeIntuitiveCare\\teste\\arquivosGov\\anexos";
        String pastaCompactadaCaminho = "C:\\development\\testeIntuitiveCare\\teste\\arquivosGov\\anexos.zip";
        File pastaAnexos = new File(pastaAnexosCaminho);
        CompactarArquivos ca = new CompactarArquivos();

        //act
        ap.fazerDownloads();
        ca.compactarPasta(pastaAnexos, pastaCompactadaCaminho);

        //assert

        File anexosZip = new File(pastaCompactadaCaminho);
        System.out.println(pastaAnexos.isDirectory());
        System.out.println(anexosZip.isFile());
    }
}