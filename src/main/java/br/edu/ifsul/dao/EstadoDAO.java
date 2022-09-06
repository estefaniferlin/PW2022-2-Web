/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

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
    }
    
    // quando usar na controlador atenho que dizer o tipo dele
    
    
}
