/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import br.edu.ifsul.modelo.Estado;
import javax.ejb.Stateful;

/**
 *
 * @author 20202PF.CC0011
 */
@Stateful
public class EstadoDAO<TIPO> extends DAOGenerico<Estado> implements Serializable{
    
     // tenho que dizer que esse cara é um interpraze java bean (entao o stateful
    
    public EstadoDAO(){
        super();
        classePersistente = Estado.class;   // tenho que dizer que esse cara é um interpraze java bean (entao o 
        //definir as ordem possiveis e ordenaçlão, atriburos e operadores que serao utilizados
        listaOrdem.add(new Ordem("id", "ID", "=")); // ordenação para o id //sequenciamente: atributo, label do atributo e operador
        listaOrdem.add(new Ordem("nome", "Nome", "like"));// ordenação para o nome do estado
    
        //definir a ordem inicial
        ordemAtual = listaOrdem.get(1); // onde 0 é id e 1 é o nome, ou seja, iremos iniciar ordenando pelo nome ?

        //inicializar o conversor das ordens
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
        
    }
    
    // quando usar na controlador atenho que dizer o tipo dele
    
    
}
