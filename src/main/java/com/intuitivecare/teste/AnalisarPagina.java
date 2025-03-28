package com.intuitivecare.teste;

import java.io.File;
import java.io.IOException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AnalisarPagina {
    WebClient client = new WebClient();
    HtmlPage page;
    public AnalisarPagina(String url) {
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        try {
            this.page = client.getPage(url);
        } catch(FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }
    }

    public AnalisarPagina() {
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        try {
            this.page = client.getPage("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos");
        } catch(FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }
    }

    public String buscarUrl() {
        return page.getUrl().toString();
    }

    public List<HtmlElement> buscarElementos(String... xpathElementos) {
        List<HtmlElement> elementosEncontrados = new ArrayList<>();
        for(String xpath : xpathElementos) {
            HtmlElement elementoEncontrado = page.getFirstByXPath(xpath);
            elementosEncontrados.add(elementoEncontrado);
        }

        return elementosEncontrados;
    }

    public List<HtmlAnchor> buscarElementos() {

        List<HtmlAnchor> anexos = new ArrayList<>();
        String xpathAnexoI = "/html/body/div[2]/div[1]/main/div[2]/div/div/div/div/div[2]/div/ol/li[1]/a[1]";
        String xpathAnexoII = "/html/body/div[2]/div[1]/main/div[2]/div/div/div/div/div[2]/div/ol/li[2]/a";
        HtmlAnchor anexoI = page.getFirstByXPath(xpathAnexoI);
        HtmlAnchor anexoII = page.getFirstByXPath(xpathAnexoII);

        anexos.add(anexoI);
        anexos.add(anexoII);

        return anexos;
    }

    public void fazerDownloads() {
        HtmlAnchor anexoI = buscarElementos().get(0);
        HtmlAnchor anexoII = buscarElementos().get(1);
        String destinoAnexoI = "C:\\development\\testeIntuitiveCare\\teste\\arquivosGov\\anexos\\anexoI.pdf";
        String destinoAnexoII = "C:\\development\\testeIntuitiveCare\\teste\\arquivosGov\\anexos\\anexoII.pdf";

        try {
            String enderecoAnexoI = anexoI.click().getUrl().toString();
            String enderecoAnexoII = anexoII.click().getUrl().toString();

            FileUtils.copyURLToFile(new URL(enderecoAnexoI), new File(destinoAnexoI), 10000, 10000);
            FileUtils.copyURLToFile(new URL(enderecoAnexoII), new File(destinoAnexoII), 10000, 10000);

            System.out.println("Arquivos baixados em: " + destinoAnexoI);
        } catch (IOException e) {
            System.out.println("Um erro ocorreu: ");
            e.printStackTrace();
        }
    } 

    public void fazerDownloads(List<HtmlElement> anchorTags, List<String> destinoDownloads) {

        if(anchorTags.get(0).getClass().toString() != "HtmlAnchor") {
            System.out.println("O elementos precisam ser do tipo anchor tag (<a></a>)!");
            return;
        }; 

        for(HtmlElement anchorTag : anchorTags) {
            
            int anchorTagIndex = anchorTags.indexOf(anchorTag);
            try {
                String endereco = anchorTag.click().getUrl().toString();  
                String destino = destinoDownloads.get(anchorTagIndex);
                // String nomeArquivo = destinoDonwload.split(regex)  
                String nomeArquivo = anchorTag.getTextContent();
                FileUtils.copyURLToFile(new URL(endereco), new File(destino), 10000, 10000);
            
                System.out.println("Arquivo " + nomeArquivo + " baixado em: " + destino);
            } catch (IOException e) {
                System.out.println("Um erro ocorreu: ");
                e.printStackTrace();
            }
        }

    } 

}
