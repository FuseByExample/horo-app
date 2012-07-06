package net.jakubkorab.horo.model;

import org.joda.time.DateTime;

public class Horoscope {
	private Long id;
	private DateTime predictsFor;
	private StarSign starSign;
	private String entry;
    private Feed feed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getPredictsFor() {
		return predictsFor;
	}

	public void setPredictsFor(DateTime predictsFor) {
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

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

}