package pkg;


import java.sql.Timestamp;

public class Eredmeny {
	private String name;
	private Integer len;
	private Integer time;
	private Timestamp date;
	
	public Eredmeny(String name, Integer len, Integer time, Timestamp date) {
		this.name = name;
		this.len = len;
		this.time = time;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public Integer getLen() {
		return len;
	}

	public Integer getTime() {
		return time;
	}

	public Timestamp getDate() {
		return date;
	}

	@Override
	public String toString() {
		return name + "   " + len + "   " + time + "sec   " + date.toString().substring(0, 19);
	}
	
}
