/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import br.edu.ifsul.modelo.Cidade;
import javax.ejb.Stateful;

/**
 *
 * @author 20202PF.CC0011
 */
@Stateful
public class CidadeDAO<TIPO> extends DAOGenerico<Cidade> implements Serializable{
    
     // tenho que dizer que esse cara é um interpraze java bean (entao o stateful
    
    public CidadeDAO(){
        super();
        classePersistente = Cidade.class;   // tenho que dizer que esse cara é um interpraze java bean (entao o 
        // lista de ordenações do dao
        listaOrdem.add(new Ordem("id", "ID","=")); // elemento 0
        listaOrdem.add(new Ordem("nome", "Nome", "like")); // elemento 1
        listaOrdem.add(new Ordem("estado.nome", "Estado", "like")); // elemento 2
        //definição da ordem atual
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    // quando usar na controlador atenho que dizer o tipo dele
    
    
}
