package tools;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class BasicTools {
	public static ArrayList<Integer> String2IntTab(String s) {
		ArrayList<Integer> tab = new ArrayList<Integer>();

		StringTokenizer st = new StringTokenizer(s, ", ");
		while (st.hasMoreTokens()) {
			tab.add(Integer.parseInt(st.nextToken()));
		}

		return tab;
	}
}
