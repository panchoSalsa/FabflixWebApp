class Pair{
	public int star_id;
	public int movie_id;
	
	public Pair(int star_id, int movie_id){
		this.star_id = star_id;
		this.movie_id = movie_id;
	}
	public boolean equals(Object obj) {
		Pair pair = (Pair) obj;
		return (star_id == pair.star_id) && (movie_id == pair.movie_id);
	}  

	public int hashCode() {
	        int hash = (new Integer(this.star_id)).hashCode() + (new Integer(this.movie_id)).hashCode();
		return hash;
	}
}