package numberrangesummarizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Q Wu
 * 
 * This script implements the interface file: NumberRangeSummarizer.java
 * 
 * - Collection<Integer> collect(String input): collects the input data.
 * - String summarizeCollection(Collection<Integer> input) : gets the summarized string
 *
 **/
 
 public class summarizerBase implements NumberRangeSummarizer
 {
	 private Collection<Integer> ranges = new ArrayList<Integer>();
	 
	 public void setRanges(Collection<Integer> inRange){ranges = inRange;}
	 public Collection<Integer> getRanges(){return ranges;}
	 
	 /*
	 The collect method collects the string input data
	 of comma delimited list of numbers.
	 */
	 public Collection<Integer> collect(String inStr) throws ClassCastException
	 {
		 ranges.clear();
		 String[] strArr = inStr.replaceAll("[^0-9.,]+","").split(",");
		 for(int i = 0; i < strArr.length; i++)
		 {
			 ranges.add(Integer.parseInt(strArr[i]));
		 }
		 
		 //Arrays.stream(intArr).forEach(x->System.out.print(x+" ")); System.out.println();
		 
		 return ranges;
	 }
	 
	 /*
	 The summarizeCollection method takes the Collection
	 input data and groups the numbers into ranges when they are sequential.
	 */
	 public String summarizeCollection(Collection<Integer> inColle) throws ClassCastException
	 {
		 String mainStr = "";
		 Object[] intObjArr =  inColle.toArray();
		 Arrays.sort(intObjArr, 0, intObjArr.length); //sort the array first.
		 
		 String tray = ""; //the "tray" is to facilitate the range in string form.
		 for(int i = 0; i < intObjArr.length; i++)
		 {
			 //check if the two elements are sequential, then make the range. 
			 if(i!=0 && (int)intObjArr[i]-(int)intObjArr[i-1]==1)
			 {
				 if(tray.equals(""))
				 {
					 tray = intObjArr[i-1].toString()+"-"+intObjArr[i].toString();
				 }
				 else if(!tray.equals(""))
				 {
					 //updates the maximum range.
					 tray = tray.substring(0,tray.indexOf("-")+1)+intObjArr[i].toString();
				 }
				 //caters for end-of-array.
				 if(i == intObjArr.length-1)
				 {
					 mainStr = mainStr + tray;
				 }
			 }
			 //check if the two elements are not sequential, then drop it to the main string.
			 else if(i!=0 && (int)intObjArr[i]-(int)intObjArr[i-1]!=1)
			 {
				 //drop the range string (tray) into the main string. 
				 if(!tray.equals("") && (int)intObjArr[i]-(int)intObjArr[i-1]!=0) //cater for duplicates
				 {
					 mainStr = mainStr + tray + ", ";
					 tray = "";
				 }
				 else if(tray.equals("") && (int)intObjArr[i]-(int)intObjArr[i-1]!=0) //cater for duplicates
				 {
					 mainStr = mainStr + intObjArr[i-1].toString() + ", ";
				 }
				 //caters for end-of-array.
				 if(i == intObjArr.length-1)
				 {
					 if(tray.equals(""))
					 {
						 mainStr = mainStr + intObjArr[i].toString();
					 }
					 else
					 {
						 mainStr = mainStr + tray; //cater for long duplicates.
					 }
				 }
			 }
		 }
		 
		 //Arrays.stream(intObjArr).forEach(x->System.out.print(x+" ")); System.out.println();
		 //System.out.println(mainStr);
		 
		 return mainStr;
	 }
	 /*
	 The main method will focus on the testing. First for possible edge cases,
	 then a few random generated data set. 
	 */
	 public static void main(String[] args)
	 {
		 summarizerBase obj = new summarizerBase();
		 //if the data set is sequential at two ends of the list and/or duplicate values.
		 obj.setRanges(obj.collect("3,1,5,2,4,6,1,21,45,21,62,1,26,42,10,1,7,6,98,21,9,8,98,98"));
		 String outStr1 = obj.summarizeCollection(obj.getRanges());
		 System.out.println(outStr1);
		 
		 //Generate 1000 random integers between 1 and 50.
		 int[] rngArr = new int[1000];
		 for(int i = 0; i < rngArr.length; i++)
		 {
			 rngArr[i] = ThreadLocalRandom.current().nextInt(1, 50 + 1);
		 }
		 String rngStr = Arrays.toString(rngArr);
		 
		 
		 obj.setRanges(obj.collect(rngStr));
		 String outStr2 = obj.summarizeCollection(obj.getRanges());
		 System.out.println(outStr2);
		 
		 //Generate 50 random integers between 1 and 1000.
		 rngArr = new int[50];
		 for(int i = 0; i < rngArr.length; i++)
		 {
			 rngArr[i] = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
		 }
		 rngStr = Arrays.toString(rngArr);
		 
		 
		 obj.setRanges(obj.collect(rngStr));
		 outStr2 = obj.summarizeCollection(obj.getRanges());
		 System.out.println(outStr2);
		 
		 //Balanced 100 random integers between 1 and 100
		 rngArr = new int[100];
		 for(int i = 0; i < rngArr.length; i++)
		 {
			 rngArr[i] = ThreadLocalRandom.current().nextInt(1, 100 + 1);
		 }
		 rngStr = Arrays.toString(rngArr);
		 
		 
		 obj.setRanges(obj.collect(rngStr));
		 outStr2 = obj.summarizeCollection(obj.getRanges());
		 System.out.println(outStr2);
	 }
	 
 }