package mains;

import texte.Corpus;
import texte.CorpusAddNumericalRepresentation;
import texte.CorpusBuilderFromDEFT05File;
import texte.CorpusSaverInOctaveFormat;
import texte.dictionnaire.Dictionnaire;
import texte.dictionnaire.DictionnaireBuilderFromCorpus;
import texte.dictionnaire.DictionnaireSaverInOctaveFormat;
import texte.preprocessing.StringProcessor;

public class ExempleChargementLearn {
	public static void main(String[] args) {

		String filename = "data/corpus.tache1.learn.utf8";
		boolean learningStage = true;

		// chargement corpus
		CorpusBuilderFromDEFT05File builder = 
			new CorpusBuilderFromDEFT05File(filename, learningStage);
		Corpus c = builder.build();

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
		DictionnaireBuilderFromCorpus dicoBuilder = 
			new DictionnaireBuilderFromCorpus(c, sp);
		Dictionnaire dico = dicoBuilder.build();

		// transformation des documents sous formes numeriques
		CorpusAddNumericalRepresentation builderNum = 
			new CorpusAddNumericalRepresentation(c, dico);
		builderNum.build();


		// sauvegarde au format octave du corpus
		filename = "data/deftInOctaveFormat.dat";
		CorpusSaverInOctaveFormat saver = 
			new CorpusSaverInOctaveFormat(filename, c);
		saver.save();

		// savegarde au format octave du dictionnaire
		filename = "data/dicoInOctaveFormat.dat";
		DictionnaireSaverInOctaveFormat saver2 = 
			new DictionnaireSaverInOctaveFormat(filename, dico);
		saver2.save();
	}
}
