/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

import java.io.Serializable;

/**
 *
 * @author 20202PF.CC0011
 */
public class Ordem implements Serializable{
    // essa classe terá atributos para guardar nome do atributo que sera usado na ordenação, operador que vai utilizar na consulta, ...
    private String atributo; // nome do atributo da classe que vamos usar (ex.: da classe cidade o atributo aqui será nome)
    private String label; // oq sera exibido para o usuario
    private String operador; // operador: mior, igual, like, ...

    public Ordem(String atributo, String label, String operador) {
        this.atributo = atributo;
        this.label = label;
        this.operador = operador;
    }

    @Override
    public String toString() { // retorna sempre o valor do label, exibe isso na tela para o usuário 
        return label; 
    }
    
    
    
    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
    
    
}
