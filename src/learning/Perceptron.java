
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
public class Perceptron {
	private SparseDoubleVector W;
	public int maxIter = 200;
	public double epsilon = 0.0001;

	public Perceptron(Dictionnaire dico) {

		W = new SparseDoubleVector();
	}

	public SparseDoubleVector getW() {
		return W;
	}

	public SparseDoubleVector learn(ArrayList<HashMap> c) {
		return W;
	}

	public boolean test(Corpus c) {
		return false;
	}
}
