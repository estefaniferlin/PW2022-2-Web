/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.dao.EstadoDAO;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.modelo.Estado;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author 20202PF.CC0011
 */

@Named(value = "controleCidade")
@ViewScoped
public class ControleCidade implements Serializable{
    
    @EJB      // esta instanciando de forma automatica esse ejb (controle transacional automatico)
    private CidadeDAO<Cidade> dao;
    private Cidade objeto;
    @EJB
    private EstadoDAO<Estado> daoEstado;
    
    public ControleCidade(){
        
    }
    
    // metodos que respondem ao que o usuário faz:
    
    public String listar(){
        return "/privado/cidade/listar?faces-redirect=true";   // vamos criar esse caminho dentro de web pages
    }
    
    public void novo(){
        objeto = new Cidade();
    }
    
    public void alterar(Object id){  // recebo o id do objeto que quero alterar
        try{
            objeto = dao.getObjectByID(id);
        }catch(Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "
                + Util.getMensagemErro(e));
        }
    }
    
    public void remover(Object id){  // recebo o id do objeto que quero alterar
        try{
            objeto = dao.getObjectByID(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso");
        }catch(Exception e){
            Util.mensagemErro("Erro ao remover objeto: "
                + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try{
            if(objeto.getId() == null){
                dao.persist(objeto);
            }else{
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        }catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " +
                    Util.getMensagemErro(e));
        }
    }
    
    // escopo de visao, como vamos fazer manutenção com ajax, ele faz a instancia quando estamos na mesma tela, quando eu sair da tela ele exclui (poupa recursos)

    public CidadeDAO<Cidade> getDao() {
        return dao;
    }

    public void setDao(CidadeDAO<Cidade> dao) {
        this.dao = dao;
    }

    public Cidade getObjeto() {
        return objeto;
    }

    public void setObjeto(Cidade objeto) {
        this.objeto = objeto;
    }

    public EstadoDAO<Estado> getDaoEstado() {
        return daoEstado;
    }

    public void setDaoEstado(EstadoDAO<Estado> daoEstado) {
        this.daoEstado = daoEstado;
    }
    
}
