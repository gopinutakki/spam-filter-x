import weka.core.Attribute;
import weka.core.FastVector;

/**
 * 
 * Spam Classifier. Assignment 4. 
 * Dr. Olfa Nasraoui
 * CECS 621. University of Louisville
 * 
 * @author Gopi Chand Nutakki
 * @license GPLv3
 * 
 * March 9 2012
 */

public class SpamClassifier{
	
	public static void main(String args[]){
		Attribute emailMessage = new Attribute("emailMessage");
		
		FastVector emailClass = new FastVector(3);
		emailClass.addElement("spam");
		emailClass.addElement("no spam");
		emailClass.addElement("?");
		Attribute eClass = new Attribute("emailClass", emailClass);
		
		FastVector records = new FastVector(40);
		
	}
}