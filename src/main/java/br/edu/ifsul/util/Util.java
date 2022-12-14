/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author 20202PF.CC0011
 */
public class Util {
    
    public static String getMensagemErro(Exception e){
        // primeiro preciso chegar ao erro de mais baixo nivel
        while(e.getCause() != null){
            e = (Exception) e.getCause();
        }
        
        // essa classe é pra fazer tratamento dos erros que ele encontra
        // onde vamos mostrar erro pro usuario, chamamos essa classe pra mostrar os erros
        
        String retorno = e.getMessage();
        
        if(retorno.contains(("violates foreign key"))){
           retorno = "Registro não pode ser excluído por possuir" +
                   " referências em outras partes do sistema!";
        }
        return retorno;
    }
    
    public static void mensagemInformacao(String mensagem){
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                mensagem, "");
        contexto.addMessage(null, msg);
        
    }
    
    public static void mensagemErro(String mensagem){
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensagem, "");
        contexto.addMessage(null, msg);
        
    }
    
}
