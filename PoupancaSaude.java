package br.com.unisinos.lab1.TrabGB;

/** Alunos : Willian Winck e Gustavo Koch      Trabalho GBLab 1 Turma:53  2016/2 */

public class PoupancaSaude extends Poupanca{
    
    private double saldoVinculado;
    private double saldoFinanciado;
    private Dependente[] dependente;
    
    public PoupancaSaude(int nc, String nome){
        super(nc, nome);
        dependente = new Dependente[5];
    }
  
    public int contaDependentes(){
        int cont = 0;
        for(int i = 0; i<dependente.length; i++){
            if(dependente[i] != null){
                cont = cont + 1;
            }
        }
        return cont;
    }
    
    public void deposita(double valor){
        if(contaDependentes() == 0){
            saldoVinculado += valor* 0.15;
            super.deposita(valor*0.85);
        }
        else if(contaDependentes() == 1 || contaDependentes() == 2){
            saldoVinculado += valor*0.20;
            super.deposita(valor*0.80);
        }
        else if(contaDependentes()== 3 || contaDependentes() == 4){
            saldoVinculado += valor*0.30;
            super.deposita(valor*0.70);
        }
        else{
            saldoVinculado += valor*0.50;
            super.deposita(valor*0.50);
        }
    }
    
    public double creditaRendimento(double taxa){
        double valoresCreditados;
        valoresCreditados = (super.getSaldoLivre()*taxa)+(saldoVinculado*taxa);
        super.creditaRendimento(taxa);
        saldoVinculado = saldoVinculado + saldoVinculado*taxa;
        
        return valoresCreditados;
    }
    
    public boolean insereDependente(Dependente d){
        for(int i = 0; i<dependente.length; i++){
            if(dependente[i] == null){
                dependente[i] = d;
                return true;
        }
       }
       return false;
    }
    
    public int buscaDependente(String nome){
        for(int i = 0; i<dependente.length; i++){
            if(dependente[i]!= null && dependente[i].getNome().compareToIgnoreCase(nome) == 0 ){
                return i;
            }
        }
        return 99;
    }
   
    public Dependente retiraDependente(String nome){
       int salva = buscaDependente(nome); 
       Dependente temporario;
       if(salva != 99){
           if(dependente[salva].getNome().equalsIgnoreCase(nome) == true){
               temporario = dependente[salva];
               dependente[salva] = null;
               return temporario;
            }
        }
       return null;
    }
   
    public double retiraSaude(double despesa){
       double saldoFaltante = 0;
       double valorFinanciado = 0;
       double valorUsarSaldoLivre = 0;
       Teclado t = new Teclado();
       if(despesa > saldoVinculado){
           saldoFaltante = despesa - saldoVinculado;
           saldoVinculado = 0;
           System.out.println("\fSaldo que falta para a despesa:" + saldoFaltante+"R$ \n" +
                                "Saldo livre em conta:" + super.getSaldoLivre() +"R$");
           do{
               valorUsarSaldoLivre = t.leDouble("\nDigite o valor que deseja usar do saldo livre.");
                    if(valorUsarSaldoLivre > super.getSaldoLivre() || valorUsarSaldoLivre > saldoFaltante){
                        System.out.println("Valor muito grande, redigite!");
                    }
                    
            }
           while(valorUsarSaldoLivre > super.getSaldoLivre()|| valorUsarSaldoLivre > saldoFaltante);
           if(valorUsarSaldoLivre < saldoFaltante){
               valorFinanciado = saldoFaltante - valorUsarSaldoLivre;
               if(saldoFinanciado == 0){
                   saldoFinanciado = valorFinanciado * 1.05;
                   valorFinanciado = valorFinanciado* 1.05;
                }
               else if(saldoFinanciado <= 500){
                   saldoFinanciado += valorFinanciado * 1.10;
                   valorFinanciado = valorFinanciado * 1.10;
                }
               else if(saldoFinanciado > 500){
                   saldoFinanciado += valorFinanciado * 1.15;
                   valorFinanciado = valorFinanciado * 1.15;
                }
            }
        }
        else{
            saldoVinculado = saldoVinculado - despesa;
        }
       super.retira(valorUsarSaldoLivre);
       return valorFinanciado;
    }
   
    public double amortizaFinanciamento(double pagar){
       if(pagar > saldoFinanciado){
           return 0;
        }
       else if(pagar <= saldoFinanciado){
           saldoFinanciado = saldoFinanciado - pagar;
           if(saldoFinanciado == 0){
               super.deposita(pagar*0.05);
               return pagar*0.05;
            }
        }
       return 0; 
    }
   
    public void ordenaDependentes(){
       int aux = 0;
       Dependente salva;
       for(int i = 0; i<dependente.length; i++){
           for(int j = 0; j<dependente.length-1; j++){
               if(dependente[j] == null){
                   dependente[j] = dependente[j+1];
                   dependente[j+1] = null;
                }
            }
        }
       for(int i = 0; i<=contaDependentes(); i++){
           for(int j = 0; j<contaDependentes()-1; j++){
               if(dependente[j].getNome().compareToIgnoreCase(dependente[j+1].getNome()) > 0){
                   salva = dependente[j+1];
                   dependente[j+1] = dependente[j];
                   dependente[j] = salva;
                }
            }
        }
    }
    
    public String toString(){
      ordenaDependentes();
      if(contaDependentes() == 0){ 
           return super.toString() + "\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado;
        }
       else if(contaDependentes() == 1){
           return super.toString() +"\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado+ 
           "\nDependentes:" + "\nNome: "+dependente[0].getNome()+"\tParentesco: " + dependente[0].traduzParentesco();
        }
       else if(contaDependentes() == 2){
           return super.toString() +"\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado+ 
           "\nDependentes:" + "\nNome: "+dependente[0].getNome()+"\tParentesco: " + dependente[0].traduzParentesco()+
            "\nNome: "+dependente[1].getNome()+"\tParentesco: " + dependente[1].traduzParentesco();
        }
       else if(contaDependentes() == 3){
           return super.toString() +"\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado+ 
           "\nDependentes:" + "\nNome: "+dependente[0].getNome()+"\tParentesco: " + dependente[0].traduzParentesco()+
            "\nNome: "+dependente[1].getNome()+"\tParentesco: " + dependente[1].traduzParentesco()+
            "\nNome: "+dependente[2].getNome()+"\tParentesco: " + dependente[2].traduzParentesco();
        }
       else if(contaDependentes() == 4){
           return super.toString() +"\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado+ 
           "\nDependentes:" + "\nNome: "+dependente[0].getNome()+"\tParentesco: " + dependente[0].traduzParentesco()+
            "\nNome: "+dependente[1].getNome()+"\tParentesco: " + dependente[1].traduzParentesco()+
            "\nNome: "+dependente[2].getNome()+"\tParentesco: " + dependente[2].traduzParentesco()+
            "\nNome: "+dependente[3].getNome()+"\tParentesco: " + dependente[3].traduzParentesco();
       }
       return super.toString() +"\nSaldo vinculado:R$"+ saldoVinculado + "\nSaldo financiado:R$"+ saldoFinanciado+ 
       "\nDependentes:" + "\nNome: "+dependente[0].getNome()+"\tParentesco: " + dependente[0].traduzParentesco()+
       "\nNome: "+dependente[1].getNome()+"\tParentesco: " + dependente[1].traduzParentesco()+
       "\nNome: "+dependente[2].getNome()+"\tParentesco: " + dependente[2].traduzParentesco()+
       "\nNome: "+dependente[3].getNome()+"\tParentesco: " + dependente[3].traduzParentesco()+
       "\nNome: "+dependente[4].getNome()+"\tParentesco: " + dependente[4].traduzParentesco();
    }
}
         
        
           
                                    
       
    

                   
   
  
              
           
           
               
               
           
              
               
             
           
   
   

  
           

   
   

       
       
       

    
               
           
           
        
        
                                    
           
    
    

           
            
            
    
        

        
    
                
            
            
        
    

        
        
        
        
            
    
        
