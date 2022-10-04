/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Estado;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 20202PF.CC0011
 */

@Named(value = "converterEstado")
@RequestScoped
public class ConverterEstado implements Serializable, Converter{ 
    // isso tudo aqui é para fazermos a manutenção na classe cidade
    @PersistenceContext(unitName = "PW2022-2-WebPU")
    protected EntityManager em;

    // converte da tela para o objeto - recebe string converte para objeto
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {  // recebe o contexto do faces, o componente associado a esse conversor e o valor que vem da tela
       // testo primeiro se deu algum problema na tela
       if(string == null ||string.equals("Selecione um registro")){ // se o valor que vier ser esse selecione um registro, quer dizer que ele nao selecionou nada, pois esse sera o primeiro texto que aparece la, se ele n foi modificado é pq ta vindo nulo
           return null;
       }
       return em.find(Estado.class, Integer.parseInt(string)); // mas se foi preenchido, ele retorna // preciso converter a string que vem da tela para inteiro
    }

    // converte do objeto para a tela - recebe objeto e converte para string
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) { // recebe contrexto, componente que será renderizado e o objeto que será convertido numa string 
        if(t == null){
            return null;
        }
        Estado obj = (Estado) t; // faço um casting do t que veio por parametro
        return obj.getId().toString(); // retorno ele como string pois será mostrado na tela
    }
    
}
