/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmtdswing;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class MatriceStoch {
    int lenghthMax=10;
    private int n;
    private double[][] matrix=new double[lenghthMax][lenghthMax];
    private double[] P0=new double[lenghthMax];
//======================
    //======================
    public MatriceStoch(int n,int m) {
        if(n!=m){
            System.err.println("La matrice n'est pas carrée");
            end();
        }      
        else{
        this.n = n;
        Scanner lire = new Scanner(System.in);
        
        System.out.println("Saisire la matrice de transiton M : \n") ;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.println("M ["+i+"]["+j+"]") ;
                String s=lire.nextLine();
                double a=  Float.parseFloat(s) ;
                this.matrix[i][j]=a;  
            }
            double sommeSurLigne=LaSommeSurLigne(this.matrix[i]);
            if(sommeSurLigne!=1){
                System.err.println("la somme sur la ligne "+i+" != 1");
                break;
            }                
        }
        }
        
        boolean stockastique=verifierStochastique();
        if(stockastique){
            
            saisirP0();
            boolean P0coorecte=verifierP0();
            if(P0coorecte){
                System.out.println("Matrice de transition :");
                afficherMatrice();
                System.out.println("distribution initiale :");
                afficherP(this.P0);
            }
                
        }
    }
    public MatriceStoch(double[][] mat,int taille){
        this.n=taille;
        for(int i=0;i<taille;i++)
            for(int j=0;j<n;j++)
                this.matrix[i][j]=mat[i][j];
        
    }
    public MatriceStoch(){
        this.n=3;
        this.matrix[0][0]=(double) 0.0;this.matrix[0][1]=(double) 0.5;this.matrix[0][2]=(double) 0.5;
        this.matrix[1][0]=(double) 0.0;this.matrix[1][1]=(double) 1.0;this.matrix[1][2]=(double) 0.0;
        this.matrix[2][0]=(double) 0.0;this.matrix[2][1]=(double) 1.0;this.matrix[2][2]=(double) 0.0;
        
        this.P0[0]=1;this.P0[1]=0;this.P0[2]=0;
    }
    public void afficherMatrice(){
        for(int i=0;i<this.n;i++){
            for(int j=0;j<this.n;j++)
                System.out.print(this.matrix[i][j]+"|");
            System.out.println("");
        }        
    }  
    public void afficherMatrice(double[][] m){
        for(int i=0;i<this.n;i++){
            for(int j=0;j<this.n;j++)
                System.out.print(m[i][j]+"|");
            System.out.println("");
        }        
    }
    public void afficherP(double[] p){
        for(int i=0;i<this.n;i++)
            System.out.print(p[i]+"|");
        System.out.println("");
    }
    private boolean verifierStochastique(){
        double sum=0;
        for(int i=0;i<this.n;i++){
            for(int j=0;j<this.n;j++)
                sum=sum+this.matrix[i][j];
            if(sum!=1){
                System.err.println("erreur:la somme est diff à 1 : c pas stochastique");
                return false;
            }             
            else
                sum=0;
        }
        System.out.println("matrcie stochastique :correcte");
        return true;
            
    }
    public void saisirP0(){
        System.out.println("Saisie de P0");
        Scanner lire = new Scanner(System.in);
        for(int i=0;i<this.n;i++){
                System.out.println("valeur à la position ["+i+"]") ;
                String s=lire.nextLine();
                double a=  Float.parseFloat(s) ;
                this.P0[i]=a;  
            }
        System.out.println("P0");
        //afficherP(this.P0);
        //verifierP0();
    }
    private boolean verifierP0(){
        int positionDe1=99;
        for(int i=0;i<this.n;i++)
            if(this.P0[i]==1){
                if(positionDe1!=99){
                    System.err.println("erreur dans P0; il dois exister un seul 1");
                    return false;
                }       
                else
                    positionDe1=i;
            }
            else if(this.P0[i]!=0){
                System.err.println("erreur dans P0; il doit pas exister des valeurs differents de 0 ou 1");
              return false;
            }
       System.out.println("P0 est correcte");
       return true;
    }      
    
    public double[] claculerPordreN(int n){
        double[] p=new double[lenghthMax];
        if(n==0)return P0;
        else
            return produitP_Matrice(claculerPordreN(n-1),matrix);
    }
    private double[] produitP_Matrice(double[] p,double[][] m){
        double sum=0;
        double[] result=new double[lenghthMax];
        for(int i=0;i<n;i++){
            for(int j=0;j<this.n;j++){
                sum=sum+p[j]*m[j][i];
            }
            result[i]=sum;
            sum=0;
        }  
        return result;
    } 
    private double LaSommeSurLigne(double[] p){
        double sum=0;
        for(int i=0;i<this.n;i++)
            sum=sum+p[i];
        return sum;
    }
    private void end(){
        System.out.println("end");
    }
    
    public double[] RégimeStationaire(){
        int i=1;
        double[] PN_1=new double[n];
        double[] PN=new double[n];
        int s=0;
        double epsilon=0.000000000001;
        while(true){
            PN=claculerPordreN(i);
            //afficherP(PN);
            for(int j=0;j<this.n;j++){
                if(Math.abs(PN[j]-PN_1[j])<epsilon)s++;
            }
            if(s==n){
                System.out.println("regime stationnaire");
                return PN;
            }
            else s=0;
            PN_1=PN;
            i++;
            if(i==50)
                return null;
        }
    }
    public void classificationEtats(int etat){
        if(etat<0 || etat>n)
            System.err.println("verifier le numero d'etat");
        else{
            
        }
    }

// Function to multiply two matrices A and B
   private double[][] multiply(double[][] A,    double[][] B, int N) 
{ 
    double[][] C=new double[N][N];
    
    for (int i = 0; i < N; ++i) 
        for (int j = 0; j < N; ++j) 
            for (int k = 0; k < N; ++k) 
                C[i][j] += A[i][k] * B[k][j]; 
    return C; 
} 
// Function to calculate the power of a matrix 
 /*   public double[][] matrix_power(double[][] M, int p, int n) 
{ 
    double[][] A=new double[n][n]; 
    for (int i = 0; i < n; ++i) 
        A[i][i] = 1; 
  
    while (p!=0) { 
        if (p % 2==0) 
            A = multiply(A, M, n); 
        M = multiply(M, M, n); 
        p /= 2; 
    }   
  return A;
}*/

    //
    public double[][] matrix_addition(double[][] M, double[][] M2, int n){
        double[][] sommeMatrix=new double[this.n][this.n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                sommeMatrix[i][j]=M[i][j]+M2[i][j];
            }
        return sommeMatrix;
    }
    
    public boolean irréductible(){
        double[][] sommeMatrix=this.matrix;
        //afficherMatrice(sommeMatrix);
        double[][] pPrec=this.matrix;
        for(int i=1;i<this.n;i++){          
        double[][] p=multiply(pPrec,this.matrix,this.n) ;
        sommeMatrix=matrix_addition(sommeMatrix,p,this.n);
          //  System.out.println("");
        //afficherMatrice(sommeMatrix);
        pPrec=p;
        }
        boolean matriceIreeductible=YaTilZero(sommeMatrix, this.n);
        if(matriceIreeductible==true) return false;
        else return true;
        
    }
    
    private boolean YaTilZero(double[][] m,int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                if(m[i][j]==0)return true;
        return false;
    }
    
    public boolean aperiodic(){
        double[][] pPrec=this.matrix;
        
        int boucle=0;
        for(int i=0;i<n;i++){
           if(pPrec[i][i]!=0)boucle++;
        }
        if(boucle==n){System.out.println("tout les etats ont un chemin d'ordre 1");
                        return true;}
        else if(boucle!=0 && irréductible()){
            System.out.println("des etats ont un chemin d'ordre 1 en plus c irréductible");
            return true;
        }
        
        else {
            //System.out.println("aucun chemin est d'ordre 1");
            
            double[][] ordres=new double[this.n][this.n];
            for(int i=0;i<this.n;i++)
                for(int j=0;j<this.n;j++)
                    ordres[i][j]=0;
            
           // afficherMatrice(ordres);
            
            for(int i=2;i<=n;i++){
                //System.out.println("chemin  d'ordre "+i);
                double[][]p=multiply(pPrec,this.matrix,this.n);
                pPrec=p;
                
                for(int k=0;k<n;k++){
                       if(p[k][k]!=0)ordres[i-1][k]=1;                      
                }
                
               // System.out.println("");
                      // afficherMatrice(ordres);
            }
            
            boolean irre=irréductible();
        for(int i=0;i<n;i++){
            List<Integer> chemins=new ArrayList<>();
            for(int j=0;j<this.n;j++)
                if(ordres[j][i]==1)chemins.add(j);
            if(chemins.size()!=0){
            int ancPGCD= chemins.get(0);
            for(int k=1;k<chemins.size()-1;k++){
              int pgcd=pgcd(ancPGCD,chemins.get(k));
              ancPGCD= pgcd;       
            }
            if(ancPGCD==1&&irre)return true;
            System.out.println("etat "+i+" periode="+(ancPGCD+1));
            }
            else{
              System.out.println("etat "+i+" periode infini");  
            }
        }
        }   
        
        return false;
    }
    //
    
public  int pgcd(int a, int b) {
    
   int r,q=0;
    
    for(;;) {
        r=a%b;
        q = (a-r)/b;
        if (r==0) break;
        a=b;
        b=r;
    }
    
    return b;
}
// Function to calculate the probability of  
// reaching F at time T after starting from S 
/*    public double findProbability(int F,int S, int T) 
{ 
    // Storing M^T in MT 
//    double[][] MT = matrix_power(this.matrix, T, this.n); 
     Returning the answer 
    return MT[F - 1][S - 1]; 
} 
*/

    
}
