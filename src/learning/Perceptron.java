
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package learning;

import java.util.ArrayList;
import java.util.HashMap;
import texte.Corpus;
import texte.dictionnaire.Dictionnaire;
import texte.processing.SparseDoubleVector;

/**
 *
 * @author slash
 */
public class Perceptron {
    private SparseDoubleVector W;
    public int maxIter = 200;
    public double epsilon = 0.0001;
    
    public Perceptron(Dictionnaire dico){

        W = new SparseDoubleVector();
    }

    public SparseDoubleVector getW(){
        return W;
    }

    public SparseDoubleVector learn(ArrayList<HashMap> c){
       System.out.println("Learning...");

       for(int m=0; m < this.maxIter; m++){

           for(HashMap t : c){
               Double Y = Double.parseDouble(t.get("label").equals("C")?"-1":"1");

               SparseDoubleVector X = (SparseDoubleVector)t.get("vector");
               SparseDoubleVector res = X.prod(Y);

               if(this.W.prodScal(res) <= 0){
                     res = res.prod(epsilon);
                     this.W = this.W.plus(res);
                 }
           }
           epsilon *= 0.99;
        }
        return this.W;
    }
    
    //Test du perceptron sur l'ensemble du corpus. Permet de déterminer si le perceptron est fort ou faible
    /*public boolean test(Corpus c){
    }*/
}
