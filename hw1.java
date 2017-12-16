import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class hw1 {
	private static Map<Integer,String> map_query=new HashMap<>();
	private static Map<Integer,String> map_db=new HashMap<>();
	
	public static void main(String[] args){
		initiateAlignmentProcess(args);
	}
	
	private static void initiateAlignmentProcess(String[] args) {
		List<Character> alphabets=generateAlphabets(args[3]);
		int[][] scoringMat=generateScoringMatrix(args[4],alphabets.size());
		generateInputSequence(args[1],1,alphabets,scoringMat,Integer.parseInt(args[6]));
		generateInputSequence(args[2],2,alphabets,scoringMat,Integer.parseInt(args[6]));
		int method=Integer.parseInt(args[0]);
		if(method==2) runLocalAlignment(Integer.parseInt(args[5]),alphabets,scoringMat,Integer.parseInt(args[6]));
		else if(method==1) runGlobalAlignment(Integer.parseInt(args[5]),alphabets,scoringMat,Integer.parseInt(args[6]));
		else runDoveTailAlignment(Integer.parseInt(args[5]),alphabets,scoringMat,Integer.parseInt(args[6]));
	}
	
	private static void runDoveTailAlignment(int top,List<Character> alphabets, int[][] scoringMat,int penalty) {
		List<DoveTail> allDoveAlignObj=new ArrayList<>();
		for(Map.Entry<Integer, String> query:map_query.entrySet()){
			//long start_time=System.nanoTime();
			for(Map.Entry<Integer, String> database:map_db.entrySet()){
				DoveTail doveAlignObj=new DoveTail();
				allDoveAlignObj.add(doveAlignObj);
				doveAlignObj.setSeq1(query.getValue());
				doveAlignObj.setSeq2(database.getValue());
				doveAlignObj.setAlphabets(alphabets);
				doveAlignObj.setScoreMatrix(scoringMat);
				doveAlignObj.setQuery_seqId(query.getKey());
				doveAlignObj.setDb_seqId(database.getKey());
				doveAlignObj.setPenalty(penalty);
				doveAlignObj.align();
			}
			//long end_time=System.nanoTime();
			//System.out.println((end_time-start_time)/1000000 +","+query.getValue().length());
		}
		Collections.sort(allDoveAlignObj,(a,b)->b.getScoreModel().getScore()-a.getScoreModel().getScore());
		for(int i=0;i<top;i++){
			//System.out.println(allDoveAlignObj.get(i).getScoreModel().getScore());
			System.out.println("Score = "+allDoveAlignObj.get(i).getScoreModel().getScore());
			System.out.println(allDoveAlignObj.get(i).getDb_seqId() +" "+allDoveAlignObj.get(i).getScoreModel().getResult_db_start_pos()+" "+allDoveAlignObj.get(i).getResult_db());
			System.out.println(allDoveAlignObj.get(i).getQuery_seqId() +" "+allDoveAlignObj.get(i).getScoreModel().getResult_query_start_pos()+" "+allDoveAlignObj.get(i).getResult_query());
		}
		
	}
	
	private static void runGlobalAlignment(int top,List<Character> alphabets, int[][] scoringMat,int penalty) {
		List<GlobalAlignment> allGlobalAlignObj=new ArrayList<>();
		for(Map.Entry<Integer, String> query:map_query.entrySet()){
			//long start_time=System.nanoTime();
			for(Map.Entry<Integer, String> database:map_db.entrySet()){
				GlobalAlignment globalAlignObj=new GlobalAlignment();
				allGlobalAlignObj.add(globalAlignObj);
				globalAlignObj.setSeq1(query.getValue());
				globalAlignObj.setSeq2(database.getValue());
				globalAlignObj.setAlphabets(alphabets);
				globalAlignObj.setScoreMatrix(scoringMat);
				globalAlignObj.setQuery_seqId(query.getKey());
				globalAlignObj.setDb_seqId(database.getKey());
				globalAlignObj.setPenalty(penalty);
				globalAlignObj.align();
			}
			//long end_time=System.nanoTime();
			//System.out.println((end_time-start_time)/1000000 +","+query.getValue().length());
		}
		Collections.sort(allGlobalAlignObj,(a,b)->b.getScoreModel().getScore()-a.getScoreModel().getScore());
		for(int i=0;i<top;i++){
			//System.out.println(allGlobalAlignObj.get(i).getScoreModel().getScore());
			System.out.println("Score = "+allGlobalAlignObj.get(i).getScoreModel().getScore());
			System.out.println(allGlobalAlignObj.get(i).getDb_seqId() +" "+allGlobalAlignObj.get(i).getScoreModel().getResult_db_start_pos()+" "+allGlobalAlignObj.get(i).getResult_db());
			System.out.println(allGlobalAlignObj.get(i).getQuery_seqId() +" "+allGlobalAlignObj.get(i).getScoreModel().getResult_query_start_pos()+" "+allGlobalAlignObj.get(i).getResult_query());
		}
		
	}
	
	private static void runLocalAlignment(int top,List<Character> alphabets, int[][] scoringMat,int penalty) {
		List<LocalAlignment> allLocalAlignObj=new ArrayList<>();
		for(Map.Entry<Integer, String> query:map_query.entrySet()){
			//long start_time=System.nanoTime();
			for(Map.Entry<Integer, String> database:map_db.entrySet()){
				LocalAlignment localAlignObj=new LocalAlignment();
				allLocalAlignObj.add(localAlignObj);
				localAlignObj.setSeq1(query.getValue());
				localAlignObj.setSeq2(database.getValue());
				localAlignObj.setAlphabets(alphabets);
				localAlignObj.setScoreMatrix(scoringMat);
				localAlignObj.setQuery_seqId(query.getKey());
				localAlignObj.setDb_seqId(database.getKey());
				localAlignObj.setPenalty(penalty);
				localAlignObj.align();
			}
			//long end_time=System.nanoTime();
			//System.out.println((end_time-start_time)/1000000 +","+query.getValue().length());
		}
		Collections.sort(allLocalAlignObj,(a,b)->b.getScoreModel().getScore()-a.getScoreModel().getScore());
		for(int i=0;i<top;i++){
			//System.out.println(allLocalAlignObj.get(i).getScoreModel().getScore());
			System.out.println("Score = "+allLocalAlignObj.get(i).getScoreModel().getScore());
			System.out.println(allLocalAlignObj.get(i).getDb_seqId() +" "+allLocalAlignObj.get(i).getScoreModel().getResult_db_start_pos()+" "+allLocalAlignObj.get(i).getResult_db());
			System.out.println(allLocalAlignObj.get(i).getQuery_seqId() +" "+allLocalAlignObj.get(i).getScoreModel().getResult_query_start_pos()+" "+allLocalAlignObj.get(i).getResult_query());
		}
		
	}
	private static int[][] generateScoringMatrix(String path,int len) {
		int[][] scoringMat=new int[len][len];
		int row=0;
		int col=0;
		Scanner scanner=null;
		try {
			scanner = new Scanner(new File(path + ".txt"));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
			    Scanner nextVal = new Scanner(line.trim());
			    while(nextVal.hasNextInt())
			    {
			    	scoringMat[row][col++]=nextVal.nextInt();
			    }
			    row++;
			    col=0;
			    nextVal.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(scanner!=null) scanner.close();
		}
		return scoringMat;
		
	}
	private static List<Character> generateAlphabets(String path) {
		Scanner scanner=null;
		List<Character> alphabets=new ArrayList<>();
		try {
			scanner = new Scanner(new File(path + ".txt"));
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for(int i = 0; i < line.length(); i++){
					alphabets.add(Character.toLowerCase(line.charAt(i)));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(scanner!=null) scanner.close();
		}
		return alphabets;
	}
	public static void generateInputSequence(String file_name,int index,List<Character> alphabets, int[][] scoringMat, int penalty) {
		BufferedReader buffr = null;
		FileReader filer = null;
		file_name+=".txt";
		try { 
			filer = new FileReader(file_name);
			buffr = new BufferedReader(filer);
			
			StringBuilder read_string_seq = new StringBuilder("");
			String line;
			
			int seqID = 0;
			while ((line = buffr.readLine()) != null) {
				if(line.startsWith(">")) {
					if(!read_string_seq.toString().isEmpty()) {
						if(index==1) map_query.put(seqID,read_string_seq.toString());
						else map_db.put(seqID,read_string_seq.toString());
					}
					seqID = new Scanner(line).useDelimiter("[^\\d]+").nextInt();
					read_string_seq = new StringBuilder("");
				} else {
					read_string_seq.append(line.trim());
				}
			}
			if(!read_string_seq.toString().isEmpty()) {
				if(index==1) map_query.put(seqID,read_string_seq.toString());
				else map_db.put(seqID,read_string_seq.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if (filer!=null) filer.close();
					if (buffr!=null) buffr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
