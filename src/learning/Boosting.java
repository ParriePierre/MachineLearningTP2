/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package learning;

import java.util.ArrayList;
import java.util.HashMap;

import texte.*;
import texte.dictionnaire.*;
import texte.processing.*;

/**
 *
 * @author slash
 */
public class Boosting {
	public int PLOT_VERBOSE = 0;
	private Dictionnaire dico;

	//tableau des poids des classifieurs
	private ArrayList<SparseDoubleVector> TabW = new ArrayList<SparseDoubleVector>();
	 //PDDD
	private SparseDoubleVector D = new SparseDoubleVector();
	//Erreur de chaque exemple (utilisé pour calculer alpha)
	private SparseDoubleVector error = new SparseDoubleVector();
	//Alphas calculé par chaque perceptron
	private SparseDoubleVector TabAlpha = new SparseDoubleVector();

	public Double EXTRACT_RATE = 0.05;
	//Nbr de perceptron
	public int PECPTRON_COUNT = 200;
	public Double EPSILON = 0.0001;
	//Nbr d'exemples testé par un perceptron
	public int MAX_ITER = 200;

	public Boosting(Dictionnaire dico) {
		this.dico = dico;
		System.out.println("Boosting is starting...");

	}

	public void learn(Corpus c, Corpus cTest) {
		//boucle nbr percpetron
		//Extraire sous-corpus de 5% en fct des plus mal appris
    	//Après learn, erreur sur ensemble du corpus
		
		int i;
		int j;
		int indicator;
		HashMap tmp;
		//5% retenu du corpus
		ArrayList<HashMap> l = new ArrayList<>();
		Perceptron p = new Perceptron(dico);
		
		//Initialisation du PDDD a 1/c.size
		for(i = 0; i<c.size(); i++)
			D.set(i,1/(double)c.size());
		
		for(i = 0; i<PECPTRON_COUNT; i++)
		{
			j=0;
			//Tant que 5% du corpus n'a pas été extrait
			while((double)l.size()<((double)c.size())*EXTRACT_RATE) {
				//création d'un indicateur aléatoire
				indicator = (int) Math.random();
				//Comparaison de l'indicateur à un élément de la PDDD
				if(D.get(j)<indicator)
				{
					//Ajout de l'élément correspondant au ss-ensemble
					c.go(j);
					tmp = new HashMap();
					tmp.put("vector", c.getCurrentTextNum());
	                tmp.put("label",c.getCategory());
					l.add(tmp);
					tmp=null;
				}
			}
			SparseDoubleVector w = p.learn(l);
		}
	}

	/*public void print(Corpus c, PlotFrame f) {

	}*/

	public void print_density() {

	}

	public void printErrors(Corpus c) {

	}

}
