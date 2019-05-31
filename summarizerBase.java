package numberrangesummarizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.StringBuilder;

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
		 StringBuilder mainStr = new StringBuilder("");
		 Object[] intObjArr =  inColle.toArray();
		 Arrays.sort(intObjArr, 0, intObjArr.length); //sort the array first.
		 
		 StringBuilder tray = new StringBuilder(""); //the "tray" is to facilitate the range in string form.
		 for(int i = 0; i < intObjArr.length; i++)
		 {
			 //check if the two elements are sequential, then make the range. 
			 if(i!=0 && (int)intObjArr[i]-(int)intObjArr[i-1]==1)
			 {
				 if(tray.toString().equals(""))
				 {
					 tray.append(intObjArr[i-1].toString()).append("-").append(intObjArr[i].toString());
				 }
				 else if(!tray.toString().equals(""))
				 {
					 //updates the maximum range.
					 tray.replace(tray.indexOf("-")+1,tray.length(),intObjArr[i].toString());
				 }
				 //caters for end-of-array.
				 if(i == intObjArr.length-1)
				 {
					 mainStr.append(tray);
				 }
			 }
			 //check if the two elements are not sequential, then drop it to the main string.
			 else if(i!=0 && (int)intObjArr[i]-(int)intObjArr[i-1]!=1)
			 {
				 //drop the range string (tray) into the main string. 
				 if(!tray.toString().equals("") && (int)intObjArr[i]-(int)intObjArr[i-1]!=0) //cater for duplicates
				 {
					 mainStr.append(tray).append(", ");
					 tray.setLength(0);
				 }
				 else if(tray.toString().equals("") && (int)intObjArr[i]-(int)intObjArr[i-1]!=0) //cater for duplicates
				 {
					 mainStr.append(intObjArr[i-1].toString()).append(", ");
				 }
				 //caters for end-of-array.
				 if(i == intObjArr.length-1)
				 {
					 if(tray.toString().equals(""))
					 {
						 mainStr.append(intObjArr[i].toString());
					 }
					 else
					 {
						 mainStr.append(tray); //cater for long duplicates.
					 }
				 }
			 }
		 }
		 
		 //Arrays.stream(intObjArr).forEach(x->System.out.print(x+" ")); System.out.println();
		 //System.out.println(mainStr);
		 
		 return mainStr.toString();
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
		 //Should return 1-50 (distribution squished in 1-50)
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
		 //Should return 50 very individual numbers.
		 rngArr = new int[50];
		 for(int i = 0; i < rngArr.length; i++)
		 {
			 rngArr[i] = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
		 }
		 rngStr = Arrays.toString(rngArr);
		 
		 
		 obj.setRanges(obj.collect(rngStr));
		 outStr2 = obj.summarizeCollection(obj.getRanges());
		 System.out.println(outStr2);
		 
		 //Balanced 100 random integers between 1 and 100.
		 //Should return near balance of ranges and single numbers.
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
