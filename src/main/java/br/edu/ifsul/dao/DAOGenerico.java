/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import java.util.ArrayList;
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
    protected String filtro = ""; // se o usuario informa um filtro estará aqui
    protected List<Ordem> listaOrdem = new ArrayList<>(); // para guardar as ordenações possiveis nas instancias dos dao de estado, cidade, ...
    protected Ordem ordemAtual; // para saber que ordem estamos usando - ordenação que o usuário selecionou
    protected ConverterOrdem converterOrdem;
    protected Integer maximoObjetos = 5; // quantridade maxima de objetos que vou mostrar na consulta (5 apenas por padrao)
    protected Integer posicaoAtual = 0; // posicao atual da minha consulta (por exemnplo se tenho 100 registros e coloco que quero mostrar 5 por pagina, tenho que saber que na primeira pagina vou do 1 ao 5, na segunda do 6 ao 10, ...
    protected Integer totalObjetos = 0; // total de objetos da consulta para paginar de forma adeuqada
    
    public DAOGenerico(){
        
    }
    
    public List<TIPO> getListaObjetos() { // vai retornar minha consulta de forma paginada
        String jpql = "from " + classePersistente.getSimpleName();  // conforme a calsse q7ue eu estiver usando (pessoa, cidade, peddido, ..) ele monta a consulta com base na classe que for usada no momentop
        String where = "";
        filtro = filtro.replaceAll("[';-]", ""); // vai procurar esses caracteres que nao queremos dentro das consultas e se existir algum desses, ele será substituído por string vazia
        // vamos começar montar a clausula where quando o usuario colocou algum filtro, para poder procurar por ele
        if(filtro.length() > 0){ // usuario informou alguma coisa
            switch (ordemAtual.getOperador()){
                case "=" :  // se o operador é =
                    if(ordemAtual.getAtributo().equals("id")){ // se for igual ao id vamos tentar fazer uma conversão - fazemos isso para nao dar problema na consulta
                        try{
                            Integer.parseInt(filtro);
                        }catch(Exception e){
                            filtro = "0";
                        }
                    }
                    // se for =
                    where += " where " + ordemAtual.getAtributo() + " = '" + filtro + "' ";  // um exemplo: la de cima ele pega o from de estado, whhere id for igual ao filtro inserido
                    break;
                case "like" :
                    where += " where upper(" + ordemAtual.getAtributo() + ") like '" + filtro.toUpperCase() + "%' "; // passar tudo para maiusculo para evitar problema de buscar os dados no banco
                    break;
            }
        }     
        // a string que montamos a consulta vamos concatenar com o resto da consulta (ela ja tem o from, agora tera o where tambem)
        jpql += where;
        jpql += " order by " + ordemAtual.getAtributo();
        System.out.println("JPQL: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();// montar a parte da paginação, entao primeiro preciso saber o totsal de objetos que possuo na consulta
        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() { 
        String jpql = "from " + classePersistente.getSimpleName() + " order by " + ordemAtual.getAtributo(); // vamos ajustar ele para tambem retornar ordenado
        return em.createQuery(jpql).getResultList();
    }
    
    // agora teremos metodos que vao alterar os valores de posico atual para exibir os valores paginados
    public void primeiro(){ // para a primeira pagina, que tem posicao 0
        posicaoAtual = 0;
    }
    
    public void anterior(){ // recua uma pagina 
        posicaoAtual -= maximoObjetos;
        // pode ocorrer de eu ja estar na primeira pagina e o usuario clicar na opcao de ir para o anterior (que nao existe), entao:
        if(posicaoAtual < 0){  // se o usuario tentou voltar, ele vai deixar a posição como 0, pois é a primeira
            posicaoAtual = 0;
        }
    }
    
    public void proximo(){
        if (posicaoAtual + maximoObjetos < totalObjetos){ // quer dizer que ainda tenho para onde ir
            posicaoAtual += maximoObjetos;
        }
    }
    
    public void ultimo(){
        int resto = totalObjetos % maximoObjetos;  // exemplo: se eu tivesse 97 valores, e dividir em 5 por cada pagina, ia sobrar 2 
        if(resto > 0){
            posicaoAtual = totalObjetos - resto; // ai eu faço 97 - 2 e ai estarei na posicao 95
        }else{ //se for um valor que pode ser dividido exatamente, como 100 por 5
            posicaoAtual = totalObjetos - maximoObjetos; 
        }
    }
    
    //metodo para exibir uma mensagem de navegação (para mostrar que pagina estou, de quantos itens que estao aparecendo)
    public String getMensagemNavegacao(){
        int ate = posicaoAtual + maximoObjetos;
        if(ate > totalObjetos){
            ate = totalObjetos;
        }
        if(totalObjetos > 0){ // se tem algo na consulta
            return "Listando de " + (posicaoAtual + 1) + " até " + ate + " de " + totalObjetos + " registros"; // porque começa com 0, então aqui ele mostra iniciando do 1
        } else {
            return "Nenhum registro encontrado";
        }
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
    
    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Ordem> getListaOrdem() {
        return listaOrdem;
    }

    public void setListaOrdem(List<Ordem> listaOrdem) {
        this.listaOrdem = listaOrdem;
    }

    public Ordem getOrdemAtual() {
        return ordemAtual;
    }

    public void setOrdemAtual(Ordem ordemAtual) {
        this.ordemAtual = ordemAtual;
    }

    public ConverterOrdem getConverterOrdem() {
        return converterOrdem;
    }

    public void setConverterOrdem(ConverterOrdem converterOrdem) {
        this.converterOrdem = converterOrdem;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }   
}
