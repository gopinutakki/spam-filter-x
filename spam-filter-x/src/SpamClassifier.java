import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * 
 * Spam Classifier. Assignment 4. Dr. Olfa Nasraoui CECS 621. University of
 * Louisville
 * 
 * @author Gopi Chand Nutakki
 * @license GPLv3
 * 
 *          March 9 2012
 */

public class SpamClassifier {

	static Attribute emailMessage;
	static FastVector emailClass;
	static Attribute eClass;
	static FastVector records;
	static Instances trainingSet;

	public static void main(String args[]) throws Exception {
		String dataset = "d1";
		FastVector trainingSet, testingSet;
		SpamClassifier classifier = new SpamClassifier();

		trainingSet = classifier.createTrainingSet(dataset);
		testingSet = classifier.createTestingSet(dataset);
	}

	private FastVector createTestingSet(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private FastVector createTrainingSet(String dataset) throws Exception {

		emailMessage = new Attribute("emailMessage", (FastVector) null);

		emailClass = new FastVector(3);
		emailClass.addElement("spam");
		emailClass.addElement("no spam");
		emailClass.addElement("?");
		eClass = new Attribute("emailClass", emailClass);

		records = new FastVector(2);
		records.addElement(eClass);
		records.addElement(emailMessage);

		trainingSet = new Instances("SpamClsfyTraining", records, 40);
		trainingSet.setClassIndex(0);

		this.readDataset(dataset);
		this.NaiveBayesClassifier();
		return records;
	}

	private void NaiveBayesClassifier() throws Exception {

		StringToWordVector stringToVector = new StringToWordVector();
		stringToVector.setInputFormat(trainingSet);
		Instances filteredData = Filter
				.useFilter(trainingSet, stringToVector);

		Classifier cModel = (Classifier) new NaiveBayes();
		cModel.buildClassifier(filteredData);

		Evaluation eTest = new Evaluation(filteredData);
		eTest.evaluateModel(cModel, filteredData);

		System.out.println(eTest.toSummaryString());
	}

	private void readDataset(String dataset) throws IOException {

		ArrayList<String> fileNames = new ArrayList<String>();
		ArrayList<String> dataFiles = this.listFiles(dataset, fileNames);
		String data = "";
		for (int looper = 0; looper < dataFiles.size(); looper++) {
			data = this.readFileAsString(dataFiles.get(looper));
			Instance rec = new Instance(2);
			if (dataFiles.get(looper).contains("nospam")) {
				rec.setValue((Attribute) records.elementAt(0), "no spam");
			} else
				rec.setValue((Attribute) records.elementAt(0), "spam");
			rec.setValue((Attribute) records.elementAt(1), data);
			trainingSet.add(rec);
		}
	}

	private String readFileAsString(String filePath) throws IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
				}
		}
		return new String(buffer);
	}

	private ArrayList<String> listFiles(String path, ArrayList<String> fileNames) {
		File dir = new File(path);
		File[] files = dir.listFiles();

		for (int loop = 0; loop < files.length; loop++) {
			if (files[loop].isDirectory()) {
				listFiles(files[loop].getAbsolutePath(), fileNames);
			} else
				fileNames.add(files[loop].getAbsolutePath());
		}
		Collections.shuffle(fileNames);
		return fileNames;
	}
}