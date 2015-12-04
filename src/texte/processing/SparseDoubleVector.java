package texte.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SparseDoubleVector implements Vector<Double> {

	public HashMap<Integer, Double> v;

	public SparseDoubleVector() {
		v = new HashMap<Integer, Double>(100);
	}

	public void addOne(int id) {
		Double d = v.get(id);
		if (d == null)
			v.put(id, 1.);
		else {
			v.remove(id);
			v.put(id, d + 1.);
		}

	}

	@Override
	public Double get(int i) {
		Double d = v.get(i);
		if (d == null)
			return 0.;

		return d;
	}

	public void load(BufferedReader in) {
		v = new HashMap<Integer, Double>(); // RAZ
		try {
			while (true) {

				String buf;

				buf = in.readLine();

				if (buf == null)
					break;
				StringTokenizer st = new StringTokenizer(buf);
				v.put(Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
			}

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void load(String filename) {
		try {
			FileReader fr = new FileReader(new File(filename));

			// BufferedInputStream input = new BufferedInputStream(ins);
			BufferedReader in = new BufferedReader(fr);

			load(in);
			in.close();

		} catch (IOException e) {
			System.err.println("Can't open file " + filename + " for reading... Loading aborted");

		} catch (Exception e) {
			System.err.println("Invalid Format : " + filename + "... Loading aborted");

		}
	}

	public void loadOneLine(BufferedReader in) {
		v = new HashMap<Integer, Double>(); // RAZ
		try {
			String buf;
			buf = in.readLine();

			if (buf == null)
				return;

			StringTokenizer st = new StringTokenizer(buf);
			while (st.hasMoreTokens())
				v.put(Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public SparseDoubleVector plus(SparseDoubleVector snd) {
		SparseDoubleVector t = new SparseDoubleVector();

		ArrayList<Integer> tmp = new ArrayList();

		for (Integer i : snd.v.keySet()) {
			t.set(i, snd.get(i) + this.get(i));
			tmp.add(i);
		}

		for (Integer i : this.v.keySet())
			if (!tmp.contains(i))
				t.set(i, snd.get(i) + this.get(i));

		return t;
	}

	public SparseDoubleVector prod(Double val) {
		SparseDoubleVector t = new SparseDoubleVector();
		for (Integer i : v.keySet()) {
			t.set(i, this.get(i) * val);
		}
		return t;
	}

	public double prodScal(SparseDoubleVector snd) {
		double t = 0.;
		if (v.size() > snd.v.size()) {
			// It is better to loop on the smaller keyset:
			for (Integer i : snd.v.keySet()) {
				t += snd.get(i) * this.get(i);
			}
		} else {
			// It is better to loop on the smaller keyset:
			for (Integer i : v.keySet())
				t += snd.get(i) * this.get(i);
		}
		return t;

	}

	@Override
	public double prodScal(Vector<Double> snd) {
		return prodScal((SparseDoubleVector) snd);
	}

	public void save(PrintStream p) {
		for (Integer mot : v.keySet()) {
			p.println(mot + " " + v.get(mot));
		}
	}

	public void save(String filename) {
		try {
			FileOutputStream output = new FileOutputStream(filename);
			PrintStream p = new PrintStream(output);
			save(p);

			output.close();
		} catch (IOException e) {
			System.err.println("Can't open file " + filename + " for writting... Saving aborted");

		}
	}

	public void saveOneLine(PrintStream p) {
		for (Integer mot : v.keySet()) {
			p.print(mot + " " + v.get(mot) + " ");
		}
		p.print("\n");

	}

	@Override
	public void set(int i, Double val) {
		v.put(i, val);
	}

	public int size() {
		return v.size();
	}

}
