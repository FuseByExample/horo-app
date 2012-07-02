package net.jakubkorab.horo.model;

public class Horoscope {

	private Long id;
	private Date predictsFor;
	private StarSign starSign;
	private String entry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPredictsFor() {
		return predictsFor;
	}

	public void setPredictsFor(Date predictsFor) {
		this.predictsFor = predictsFor;
	}

	public StarSign getStarSign() {
		return starSign;
	}

	public void setStarSign(StarSign starSign) {
		this.starSign = starSign;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}