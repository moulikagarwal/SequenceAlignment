
public class ScoreModel {
	private int score;
	private int query_start_pos;
	private int db_start_pos;
	private int result_query_start_pos;
	private int result_db_start_pos;
	
	ScoreModel(int score,int query_start_pos,int db_start_pos){
		this.score=score;
		this.query_start_pos=query_start_pos;
		this.db_start_pos=db_start_pos;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getQuery_start_pos() {
		return query_start_pos;
	}
	public void setQuery_start_pos(int query_start_pos) {
		this.query_start_pos = query_start_pos;
	}
	public int getDb_start_pos() {
		return db_start_pos;
	}
	public void setDb_start_pos(int db_start_pos) {
		this.db_start_pos = db_start_pos;
	}
	public int getResult_query_start_pos() {
		return result_query_start_pos;
	}
	public void setResult_query_start_pos(int result_query_start_pos) {
		this.result_query_start_pos = result_query_start_pos;
	}
	public int getResult_db_start_pos() {
		return result_db_start_pos;
	}
	public void setResult_db_start_pos(int result_db_start_pos) {
		this.result_db_start_pos = result_db_start_pos;
	}
	
	
}
