/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package learning;

import java.util.ArrayList;

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

	private ArrayList<SparseDoubleVector> TabW = new ArrayList<SparseDoubleVector>();
	private SparseDoubleVector D = new SparseDoubleVector();
	private SparseDoubleVector error = new SparseDoubleVector();
	private SparseDoubleVector TabAlpha = new SparseDoubleVector();

	public Double EXTRACT_RATE = 0.05;
	public int PECPTRON_COUNT = 200;
	public Double EPSILON = 0.0001;
	public int MAX_ITER = 200;

	public Boosting(Dictionnaire dico) {
		this.dico = dico;
		System.out.println("Boosting is starting...");

	}

	public void learn(Corpus c, Corpus cTest) {

	}

	/*public void print(Corpus c, PlotFrame f) {

	}*/

	public void print_density() {

	}

	public void printErrors(Corpus c) {

	}

}
