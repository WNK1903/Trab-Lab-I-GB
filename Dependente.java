package br.com.unisinos.lab1.TrabGB;

/** Alunos : Willian Winck e Gustavo Koch      Trabalho GBLab 1 Turma:53  2016/2 */

public class Dependente{
    
    private String nome;
    private char parentesco;
    
    public Dependente(String nome, char p){
        this.nome = nome;
        setParentesco(p);
    }
    
    public void setParentesco(char p){
        if(p != 'c' && p != 'C' && p != 'f' && p != 'F' && p != 'p' && p != 'P')
        parentesco = 'o';
        else
        parentesco = p;
    }
    
    public String traduzParentesco(){
        if(parentesco == 'c' || parentesco == 'C')
        return "Conjuge";
        else if(parentesco == 'f' || parentesco == 'F')
        return "Filho(a)";
        else if(parentesco == 'p' || parentesco == 'P')
        return "Progenitor";
        else
        return "Outro";
    }
    
    public String toString(){
        return "Nome: " + nome + "  "+"Parentesco: " + traduzParentesco();
    }
    
    public String getNome(){
        return nome;
    }
    
    public char getParentesco(){
        return parentesco;
    }
    

}
    