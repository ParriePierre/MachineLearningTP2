
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
public class PerceptronTest {
	private SparseDoubleVector W;
	public int maxIter = 200;
	public double epsilon = 0.0001;

	public PerceptronTest(Dictionnaire dico) {
		W = new SparseDoubleVector();
	}

	public SparseDoubleVector getW() {
		return W;
	}

	//Test - predit - compare - compte % erreur
	public SparseDoubleVector learn(ArrayList<HashMap> c) {
		for(int i = 0; i<maxIter;i++)
		{
			c.forEach(h -> {
				SparseDoubleVector x = (SparseDoubleVector) h.get("vector");
				double y;
				if(h.get("label").equals("C"))
					y=1;
				else
					y= -1;
				
				SparseDoubleVector r = x.prod(y);
				if(W.prodScal(r) <= 0)
				{
					r = r.prod(epsilon);
					W = W.plus(r);
				}
			});	
		}		
		return W;
	}

	public boolean test(Corpus c) {
		return false;
	}
}
