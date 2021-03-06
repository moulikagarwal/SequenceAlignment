import java.util.List;

public class DoveTail {
	private  String seq1;
	private  String seq2;
	private  List<Character> alphabets;
	private  int[][] scoreMatrix;
	private  int method;
	private  int penalty;
	private int query_seqId;
	private int db_seqId;
	private ScoreModel scoreModel;
	private String result_query;
	private String result_db;
	public String getSeq1() {
		return seq1;
	}

	public String getResult_query() {
		return result_query;
	}

	public void setResult_query(String result_query) {
		this.result_query = result_query;
	}

	public String getResult_db() {
		return result_db;
	}

	public void setResult_db(String result_db) {
		this.result_db = result_db;
	}

	public void setSeq1(String seq1) {
		this.seq1 = seq1;
	}

	public String getSeq2() {
		return seq2;
	}

	public void setSeq2(String seq2) {
		this.seq2 = seq2;
	}

	public List<Character> getAlphabets() {
		return alphabets;
	}

	public void setAlphabets(List<Character> alphabets) {
		this.alphabets = alphabets;
	}

	public int[][] getScoreMatrix() {
		return scoreMatrix;
	}

	public void setScoreMatrix(int[][] scoreMatrix) {
		this.scoreMatrix = scoreMatrix;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public int getQuery_seqId() {
		return query_seqId;
	}

	public void setQuery_seqId(int query_seqId) {
		this.query_seqId = query_seqId;
	}

	public int getDb_seqId() {
		return db_seqId;
	}

	public void setDb_seqId(int db_seqId) {
		this.db_seqId = db_seqId;
	}

	public ScoreModel getScoreModel() {
		return scoreModel;
	}

	public void setScoreModel(ScoreModel scoreModel) {
		this.scoreModel = scoreModel;
	}

	public  void align(){
		int len1=seq1.length();
		int len2=seq2.length();
		int[][] dp=new int[len1+1][len2+1];
		char[][] dir=new char[len1+1][len2+1];
		
		for(int i=0;i<=len2;i++){
			dp[0][i]=0;
		}
		for(int i=0;i<=len1;i++){
			dp[i][0]=0;
		}
		for(int i=1;i<=len1;i++){
			for(int j=1;j<=len2;j++){
				int diag=dp[i-1][j-1]+getDiagScore(scoreMatrix,seq1,seq2,alphabets,i,j);
				int left=dp[i][j-1]+penalty;
				int up=dp[i-1][j]+penalty;
				
				int max_score=Math.max(Math.max(diag, left),up);
				dp[i][j]=max_score;
				if(max_score==diag){
					dir[i][j]='d';
				}else if(max_score==up){
					dir[i][j]='u';
				}else if(max_score==left){
					dir[i][j]='l';
				}
			}
		}
		getAlignment(dir,dp,seq1,seq2);
	}
	
	private int getDiagScore(int[][] scoreMatrix, String seq1, String seq2, List<Character> alphabets, int i, int j) {
		char row_char=seq1.charAt(i-1);
		char col_char=seq2.charAt(j-1);
		
		int row_index=alphabets.indexOf(row_char);
		int col_index=alphabets.indexOf(col_char);
		return scoreMatrix[row_index][col_index];
	}
	
	public void getAlignment(char[][] dir,int[][] matrix,String seq1,String seq2){
		getMaxRowCol(matrix);
		int i=scoreModel.getQuery_start_pos();
		int j=scoreModel.getDb_start_pos();
		int pos_row=0;
		int pos_col=0;
		
		StringBuilder res_query=new StringBuilder();
		StringBuilder res_db=new StringBuilder();
		
		while(i>0 && j>0){
			if(i>0 && dir[i][j]=='u'){
				res_query.insert(0,'.');
				res_db.insert(0, seq2.charAt(j-1));
				pos_row=i;
				i--;
			}else if((i>0 && j>0) && dir[i][j]=='d'){
				res_query.insert(0,seq1.charAt(i-1));
				res_db.insert(0, seq2.charAt(j-1));
				pos_row=i;
				pos_col=j;
				i--;
				j--;
			}else if(j>0 && dir[i][j]=='l'){
				res_query.insert(0,seq1.charAt(i-1));
				res_db.insert(0,'.');
				pos_col=j;
				j--;
			}
		}
		this.setResult_db(res_db.toString());
		this.setResult_query(res_query.toString());
		scoreModel.setResult_db_start_pos(pos_col);
		scoreModel.setResult_query_start_pos(pos_row);
	}
	public void getMaxRowCol(int[][] matrix){
		int rows=matrix.length;
		int cols=matrix[0].length;
		int max=Integer.MIN_VALUE;
		
		int[] res=new int[3];
		for(int j=0;j<cols;j++){
			if(max<matrix[rows-1][j]){
				max=matrix[rows-1][j];
				res[1]=j;
			}
		}
		res[0]=rows-1;
		for(int i=0;i<rows;i++){
			if(max<matrix[i][cols-1]){
				max=matrix[i][cols-1];
				res[1]=cols-1;
				res[0]=i;
			}
		}
		scoreModel=new ScoreModel(max,res[0],res[1]);
	}
}
