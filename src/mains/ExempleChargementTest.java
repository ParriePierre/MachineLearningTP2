package mains;

import texte.*;
import texte.dictionnaire.*;
import texte.preprocessing.*;

public class ExempleChargementTest {
	public static void main(String[] args) {

		String filename = "data/corpus.tache1.learn.utf8";
		String filenameTEST = "data/corpus.tache1.test.utf8";

		// LEARN

		boolean learningStage = true;

		// chargement corpus
		CorpusBuilderFromDEFT05File builder = new CorpusBuilderFromDEFT05File(filename, learningStage);
		Corpus c = builder.build();

		// TEST
		learningStage = false;

		// chargement corpus
		builder = new CorpusBuilderFromDEFT05File(filenameTEST, learningStage);
		Corpus cT = builder.build();

		System.out.println(cT);
		System.out.println(cT.size());

		// exemple d'utilisation:
		c.reset();
		System.out.println(c.getCurrentText());
		System.out.println("cat :" + c.getCategory());
		System.out.println("fullID :" + c.getAttribute("id"));
		c.next();
		System.out.println(c.getCurrentText());
		System.out.println("cat :" + c.getCategory());

		// construction du dictionnaire
		StringProcessor sp = Dictionnaire.standardStringProcessor();
		DictionnaireBuilderFromCorpus dicoBuilder = new DictionnaireBuilderFromCorpus(c, sp);
		Dictionnaire dico = dicoBuilder.build();

		// transformation des documents sous forme numeriques
		CorpusAddNumericalRepresentation builderNum = new CorpusAddNumericalRepresentation(cT, dico);
		builderNum.build();

		// sauvegarde au format octave du corpus
		filename = "data/deftInOctaveFormat.dat";
		CorpusSaverInOctaveFormat saver = new CorpusSaverInOctaveFormat(filename, cT);
		saver.save();

		// savegarde au format octave du dictionnaire
		filename = "data/dicoInOctaveFormat.dat";
		DictionnaireSaverInOctaveFormat saver2 = new DictionnaireSaverInOctaveFormat(filename, dico);
		saver2.save();
	}
}
