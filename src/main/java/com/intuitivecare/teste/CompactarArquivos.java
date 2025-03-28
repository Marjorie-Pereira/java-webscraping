package com.intuitivecare.teste;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompactarArquivos {
    List<String> arquivosNaPasta = new ArrayList<>();

    public void compactarPasta(File pasta, String nomeArquivoZip) {
        try {
            popularLista(pasta);

            FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String caminhoArquivo : arquivosNaPasta) {
                ZipEntry ze = new ZipEntry(caminhoArquivo
                .substring(pasta
                .getAbsolutePath()
                .length()+1, caminhoArquivo
                .length()));
                zos.putNextEntry(ze);

                FileInputStream fis = new FileInputStream(caminhoArquivo);
                byte[] buffer = new byte[1024];
                int len;
                while((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void popularLista(File pasta) throws IOException {
        File[] arquivos = pasta.listFiles();
        for(File arquivo : arquivos) {
            if(arquivo.isFile()) {
                arquivosNaPasta.add(arquivo.getAbsolutePath());
            } else popularLista(arquivo);
        }
    }
}
