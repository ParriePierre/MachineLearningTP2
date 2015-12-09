/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package learning;

import ihm.PlotFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import texte.Corpus;
import texte.dictionnaire.Dictionnaire;
import texte.processing.SparseDoubleVector;

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

	PlotFrame density = null;
	PlotFrame resultLearnSet = null;
	PlotFrame resultTestSet = null;

	public Boosting(Dictionnaire dico) {
		this.dico = dico;
		System.out.println("Boosting is starting...");

	}

	public void print(Corpus c, PlotFrame f) {

		if (c == null || f == null)
			return;
		System.out.println("Printing ...");

		f.clean();
		c.reset();
		int i = 0;
		while (c.hasNext()) {
			String categ = c.getCategory();
			Double x, y;

			SparseDoubleVector tmp = c.getCurrentTextNum();
			y = _H(i, tmp);
			x = Double.parseDouble("" + i);
			f.addPoint(x, y, (categ.equals("C") ? 0 : 1));

			i++;
			c.next();
		}

		f.repaint();
	}

	public void printErrors(Corpus c) {
		Random r = new Random();
		Double Csize = 0.0, Cgood = 0.0, Bsize = 0.0, Bgood = 0.0, Batt = 0.0, Catt = 0.0;

		for (int i = 0; i < this.error.size(); i++)
			System.out.println(i + ": " + (error.get(i) * 100) + "%");

		Double errors = 0.0;
		c.reset();
		while (c.hasNext()) {

			Double Y = Double.parseDouble(c.getCategory().equals("C") ? "-1" : "1");

			// Nombre d elements par classe
			if (Y > 0)
				Csize++;
			else
				Bsize++;

			// Nombre d'éléments predis par classe
			if (H(c.getCurrentTextNum()) > 0)
				Catt++;
			else
				Batt++;

			if (Y * H(c.getCurrentTextNum()) <= 0)
				errors++;

			else {
				// Nombre d element bien predit par classe
				if (Y > 0)
					Cgood++;
				else
					Bgood++;
			}

			c.next();
		}
		System.out.println("\n--------------------------------\n");
		System.out.println("Classifieur fort :\nNombre d'erreur :" + errors + "/" + c.size() + "("
				+ (errors * 100 / c.size()) + "%)");
		System.out.println("Rappel : " + (Cgood / Csize) + "," + (Bgood / Bsize) + " Precision:" + (Cgood / Catt) + ","
				+ (Bgood / Batt));
		System.out.println("\n--------------------------------\n");
	}

	public Double H(SparseDoubleVector X) {
		Double res = 0.0;

		for (int i = 0; i < PECPTRON_COUNT; i++) {

			if (TabW.size() <= i)
				return Math.signum(res);

			res += TabAlpha.get(i) * Math.signum(X.prodScal(TabW.get(i)));
		}
		return Math.signum(res);
	}

	public Double _H(int id, SparseDoubleVector X) {
		Double res = 0.0;

		for (int i = 0; i < PECPTRON_COUNT; i++) {
			if (TabAlpha.size() <= i || TabW.size() <= i)
				return res;

			res += TabAlpha.get(i) * Math.signum(X.prodScal(TabW.get(i)));
		}

		return res;
	}

	public void learn(Corpus c, Corpus cTest) {
		Random r = new Random();

		for (int i = 0; i < c.size(); i++)
			D.set(i, 1 / (double) c.size());

		for (int j = 0; j < PECPTRON_COUNT; j++) {
			System.out.println("\n--------------------\nClassifier " + j + "/" + PECPTRON_COUNT);
			System.gc();

			ArrayList c_tmp = extractSegmentCorpus(c, EXTRACT_RATE);
			// Learning light classifier

			Perceptron net = new Perceptron(dico);
			net.epsilon = EPSILON;
			net.maxIter = MAX_ITER;
			TabW.add(net.learn(c_tmp));
			// net.test(c);

			// Calcul de l'erreur totale
			Double E = 0.0;
			c.reset();
			int i = 0;
			while (c.hasNext()) {
				Double Y = Double.parseDouble(c.getCategory().equals("C") ? "-1" : "1");
				if (c.getCurrentTextNum().prod(Y).prodScal(TabW.get(j)) < 0)
					E += D.get(i);
				i++;
				c.next();
			}
			error.set(j, E);

			TabAlpha.set(j, 0.5 * Math.log((1 - E) / E));
			System.out.println("Error " + (error.get(j) * 100) + "% Alpha:" + TabAlpha.get(j));

			// PDDD Update scheme (formule dans le cours)
			c.reset();
			i = 0;
			double exp = 0.0;
			while (c.hasNext()) {
				Double Di = D.get(i);
				Double Y = Double.parseDouble(c.getCategory().equals("C") ? "-1" : "1");

				Double h = Math.signum(c.getCurrentTextNum().prodScal(TabW.get(j)));
				exp = Math.exp(-TabAlpha.get(j) * Y * h);
				Di = Di * Math.exp(exp);

				D.set(i, Di);
				c.next();

				i++;
			}

			// Normalization (formule dans le cours)
			Double sum = 0.0;
			for (i = 0; i < D.size(); i++) {
				sum += D.get(i);
			}

			for (i = 0; i < D.size(); i++)
				D.set(i, D.get(i) / sum);

			System.out.println("[SUCCESS]");

			switch (PLOT_VERBOSE) {
			case 3:
				if (resultTestSet == null)
					resultTestSet = new PlotFrame();

				resultTestSet.setTitle("Strong classifier after #" + j + " perceptron on test set");
				print(cTest, resultTestSet);

			case 2:
				if (resultLearnSet == null)
					resultLearnSet = new PlotFrame();

				resultLearnSet.setTitle("Strong classifier after #" + j + " perceptron on learn set");
				print(c, resultLearnSet);

				if (density == null)
					density = new PlotFrame();
				density.setTitle("Density distribution #" + j);
				print_density();
				printErrors(c);
			case 1:

			}
		}

	}

	public void print_density() {
		if (density == null)
			density = new PlotFrame();
		density.clean();
		for (int i = 0; i < D.size(); i++)
			density.addPoint(i + 0.0, D.get(i), 3);
		density.repaint();
	}

	// MonteCarlo
	public ArrayList extractSegmentCorpus(Corpus c, Double rate) {
		Random r = new Random();
		Double size = Double.parseDouble("" + c.size());
		Double D_tmp[] = new Double[c.size()];
		ArrayList<HashMap> res = new ArrayList(c.size());

		// Generation de la distribution cumulée
		D_tmp[0] = D.get(0);
		for (int i = 1; i < D.size(); i++)
			D_tmp[i] = D_tmp[i - 1] + D.get(i);

		Double i = 0.0;
		System.out.println("Extract " + (rate * 100) + "% of the corpus");
		while (i / size < rate) {

			int id = -1;
			Double r_v = r.nextDouble();
			for (id = 0; id < D_tmp.length; id++)
				if (r_v < D_tmp[id])
					break;

			c.reset();
			int j = 0;
			while (c.hasNext()) {
				if (j++ == id)
					break;
				c.next();
			}

			if (id != -1) {
				HashMap tmp = new HashMap();
				tmp.put("vector", c.getCurrentTextNum());
				tmp.put("label", c.getCategory());
				res.add(tmp);
				i++;
			}
		}

		return res;
	}
}
