package wk.model;


public class MovieVO {

	private int movieCd;
	private String movieNm;
	private String openDt;
	private String genreAlt;
	private String repNationNm;
	private String director;
	private String screenDt;
	
	public MovieVO() {
		super();
	}

	public MovieVO(int movieCd, String movieNm, String openDt, String genreAlt, String repNationNm, String director,
			String screenDt) {
		super();
		this.movieCd = movieCd;
		this.movieNm = movieNm;
		this.openDt = openDt;
		this.genreAlt = genreAlt;
		this.repNationNm = repNationNm;
		this.director = director;
		this.screenDt = screenDt;
	}

	public int getMovieCd() {
		return movieCd;
	}

	public void setMovieCd(int movieCd) {
		this.movieCd = movieCd;
	}

	public String getMovieNm() {
		return movieNm;
	}

	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getGenreAlt() {
		return genreAlt;
	}

	public void setGenreAlt(String genreAlt) {
		this.genreAlt = genreAlt;
	}

	public String getRepNationNm() {
		return repNationNm;
	}

	public void setRepNationNm(String repNationNm) {
		this.repNationNm = repNationNm;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getScreenDt() {
		return screenDt;
	}

	public void setScreenDt(String screenDt) {
		this.screenDt = screenDt;
	}

	@Override
	public String toString() {
		return movieCd + "\t" + movieNm + "\t\t" + openDt + "\t" + genreAlt
				+ "\t" + repNationNm + "\t" + director + "\t" + screenDt;
	}
	
	
	
}
