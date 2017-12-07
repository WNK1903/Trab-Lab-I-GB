package br.com.unisinos.lab1.TrabGB;

/** Alunos : Willian Winck e Gustavo Koch      Trabalho GBLab 1 Turma:53  2016/2 */

public class Agencia{
    
    private Poupanca[] poupanca;
    private int contadorContas;
    
    public Agencia(int qtdMax){
        poupanca = new Poupanca[qtdMax];
        contadorContas = 0;
        
    }
    
    private int abreConta(){
        Teclado t = new Teclado();
        int tipoConta = 0;
        int numeroConta = 0;
        if(contadorContas == poupanca.length){
            return -1;
        }
        else{
            tipoConta = t.leInt("\fDigite o tipo de conta que deseja abrir:\n1 - Poupança simples.\n2 - Poupança saúde.");
            if(tipoConta == 1){
                numeroConta = t.leInt("Digite o numero da conta:");
                poupanca[contadorContas] = new Poupanca(numeroConta,t.leString("Digite seu nome:"));
                contadorContas = contadorContas + 1;
            }
            
            else {
                numeroConta = t.leInt("Digite o número da conta:");
                poupanca[contadorContas]= new PoupancaSaude(numeroConta,t.leString("Digite seu nome:"));
                contadorContas = contadorContas + 1;
                
                String sn = null;
                do{
                sn = t.leString("Deseja inserir dependentes? S/N?");
                if(sn.compareToIgnoreCase("S") == 0 ){
                    ((PoupancaSaude)poupanca[contadorContas-1]).insereDependente( new Dependente(t.leString("Digite o nome:"),
                     t.leChar("Digite o parentesco:\n'c' para cônjuge.\n'f' para filho(a).\n'p' para progenitor(pais, avós).\n'o' para outro.")));
                }
               }   while(sn.compareToIgnoreCase("S") == 0 && ((PoupancaSaude)poupanca[contadorContas-1]).contaDependentes()<5);
            }
        }
        return numeroConta;
    }
       
    private int buscaConta(int conta){
        for(int i = 0; poupanca[i] != null; i++){
            if(poupanca[i].getNumero() == conta){
                return i;
            }
        }
        return -1;
    }
    
    public void menuDeTransacoes(){
        Teclado t = new Teclado();
        int opcao = 0;
        do{
            System.out.println("\f 1 - Abre conta.\n2 - Deposita.\n3 - Retira.\n4 - Retira para sa�de.\n5 - Amortiza financiamento.\n"+
            "6 - Emite extrato da conta.\n7 - Credita rendimentos.\n8 - Insere um dependente.\n9 - Retira um dependente.\n10 - Encerra.");
            do{
                opcao = t.leInt("Escolha a op��o:");
                if(opcao<1 || opcao>10){
                    System.out.println("Op��o invalida. Digite novamente!");
                }
            }while(opcao<1 || opcao>10);
            if(opcao == 1){
                int x = abreConta();
                if(x != -1){
                    System.out.println("Conta aberta de n�mero:"+ x);
                }
                else{
                    System.out.println("N�o pode abrir novas contas nessa ag�ncia.");
                }
            }
            else if(opcao == 2){
                int x = buscaConta(t.leInt("\fDigite o n�mero da conta para o deposito:"));
                if(x != -1){
                    poupanca[x].deposita(t.leDouble("Digite o valor que deseja depositar:"));
                }
                else{
                    System.out.println("Conta inexistente.");
                }
            }
            else if(opcao == 3){
                int numeroConta = buscaConta(t.leInt("Digite o n�mero da conta para a retirada:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente.");
                }
                else{
                    boolean x = poupanca[numeroConta].retira(t.leDouble("Digite o valor que deseja sacar."));
                    if(x == false){
                        System.out.println("Saldo insuficiente!");
                    }
                }
            }
            else if(opcao == 4){
                int numeroConta = buscaConta(t.leInt("\fDigite o n�mero da conta:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente!");
                }
                else if(poupanca[numeroConta] instanceof PoupancaSaude == false){
                    System.out.println("Conta n�o � do tipo Poupan�a Sa�de.");
                }
                else{
                    double x = ((PoupancaSaude)poupanca[numeroConta]).retiraSaude(t.leDouble("Digite o valor que deseja sacar:"));
                    if(x != 0){
                        System.out.println("Valor financiado:R$"+x);
                    }
                }
            }
            else if(opcao == 5){
                int numeroConta = buscaConta(t.leInt("\fDigite o n�mero da conta:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente!");
                }
                else if(poupanca[numeroConta] instanceof PoupancaSaude == false){
                    System.out.println("Tipo de conta n�o aceita essa opera��o!");
                }
                else{
                    double x = ((PoupancaSaude)poupanca[numeroConta]).amortizaFinanciamento(t.leDouble("Digite o valor que deseja amortizar:"));
                    if(x != 0){
                        System.out.println("Ganhou desconto-dep�sito de R$"+x);
                   }
               }
            }
            else if(opcao == 6){
                int numeroConta = buscaConta(t.leInt("\fDigite o n�mero da conta:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente!");
                }
                else{
                    System.out.println(poupanca[numeroConta].toString());
                }
            }
            else if(opcao == 7){
                double taxa = t.leDouble("Digite a taxa de rendimento:");
                double totalCreditado = 0;
                for(int i = 0; poupanca[i] != null; i++){
                    totalCreditado = totalCreditado + poupanca[i].creditaRendimento(taxa);
                }
                System.out.println("R$"+totalCreditado+"   Este valor � a soma de todos os rendimentos creditados.");
            }
                
            
            else if(opcao == 8){
                int numeroConta = buscaConta(t.leInt("Digite o n�mero da conta:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente!");
                }
                else if(poupanca[numeroConta] instanceof PoupancaSaude == false){
                    System.out.println("Conta n�o � do tipo poupan�a sa�de!");
                }
                else{
                    boolean x = ((PoupancaSaude)poupanca[numeroConta]).insereDependente(new Dependente(t.leString("Digite o nome:"),t.leChar("Digite o parentesco:\n'c' para cônjuge.\n'f' para filho(a).\n'p' para progenitor(pais, avós).\n'o' para outro.")));
                    if(x == false){
                        System.out.println("Esta conta n�o admite mais dependentes!");
                    }
                }
            }
            else if(opcao == 9){
                int numeroConta = buscaConta(t.leInt("Digite o número da conta:"));
                if(numeroConta == -1){
                    System.out.println("Conta inexistente!");
                }
                else if(poupanca[numeroConta] instanceof PoupancaSaude == false){
                    System.out.println("Conta n�o � do tipo poupan�a sa�de!");
                }
                else{
                    int aux;
                    String nome = t.leString("Digite o nome do dependente que deseja retirar:");
                    aux = ((PoupancaSaude)poupanca[numeroConta]).buscaDependente(nome);
                    
                    if(aux != 99){
                        ((PoupancaSaude)poupanca[numeroConta]).retiraDependente(nome);
                        System.out.println("Dependente retirado com sucesso!");
                        
                    }
                    else{
                        System.out.println("N�o existe dependente com este nome nesta conta.");
                    }
                }
            }
            if(opcao != 10){
                t.leString("Tecle ENTER para voltar ao menu.");
            }
        }while(opcao != 10);
    }
}


   
            
            

    
    
    
    

        

            


                
                    
                
                    
                
                
                            
    

  
   
            
            
            
        

