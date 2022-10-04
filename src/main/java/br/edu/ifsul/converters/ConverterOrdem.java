/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.converters;

import br.edu.ifsul.dao.Ordem;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 20202PF.CC0011
 */
@FacesConverter(value = "converterOrdem")
@RequestScoped
public class ConverterOrdem implements Serializable, Converter{

    // lista de possiveis ordenações
    private List<Ordem> listaOrdem;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) { // aqui recebo uma string que vem da tela (que sera o valor do tributo da ordenação que ta criado na classe ordem)
        for(Ordem o : listaOrdem){
            if(o.getAtributo().equals(string)){ // se o atributo for igual ao que veio da tela
                return o;
            }
        }
        return null; // se nao achar retorna nulo
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        if(t == null){
            return null;
        }
        return t.toString(); // ai ele retorna para exibirt na tela o label desse objeto
    }

    public List<Ordem> getListaOrdem() {
        return listaOrdem;
    }

    public void setListaOrdem(List<Ordem> listaOrdem) {
        this.listaOrdem = listaOrdem;
    }
    
}
