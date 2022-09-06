/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 20202PF.CC0011
 */
public class DAOGenerico<TIPO> implements Serializable {
    
    
    // tenho que dizer qual o tipo ele vai esperar para ser armazenado
    
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;  
    @PersistenceContext(unitName = "PW2022-2-WebPU") // nome da unidade de opersistencia que vai procurar  -> esse nome vem do arquivo persistence.xml
    protected EntityManager em;   // acessam diretamente os atributos sem precisar dos get e set (os filhos consegyem acessar, entao fica visivel para ios filhos)
    protected Class classePersistente;

    public DAOGenerico(){
        
    }
    
    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();  // conforme a calsse q7ue eu estiver usando (pessoa, cidade, peddido, ..) ele monta a consulta com base na classe que for usada no momentop
        return em.createQuery(jpql).getResultList();
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() {
        String jpql = "from " + classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }
    
    
    // ---------------------------------------------------------
    
    // todos os metodos lançam exceção para a proxima camada que for usar esse metodo par ela fazer o tratamentp
    public void persist(TIPO obj)throws Exception{
        em.persist(obj); // nao preciso mais do commit pois agora temos o contexto que fica a cargo do container (o conexto jdbc que criamos antes)
    }
    
    public void merge(TIPO obj)throws Exception{
        em.merge(obj); // nao preciso mais do commit pois agora temos o contexto que fica a cargo do container (o conexto jdbc que criamos antes)
    }
    
    public TIPO getObjectByID(Object id) throws Exception{  // esse TIPO pega o tipo que for da classe usada no momentp
        return (TIPO) em.find(classePersistente, id);
    }
  
    public void remover(TIPO obj) throws Exception{
        obj = em.merge(obj) ; // tenho que fazer um merge no objeto para poder remover depois
        em.remove(obj);
    }
    
    // ---------------------------------------------------------
    

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }
    
    
    
}
