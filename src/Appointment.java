//Dennis Frolov #500776941

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Appointment implements Comparable<Appointment> {
	
	private Calendar date;
	private String description;
	
	public void main(String[] args){
		
		date = new GregorianCalendar(1998, 4, 11, 1, 25);
		System.out.println(this.print());
		
	}
	
	public Appointment(int year, int month, int day, int hour, int minute, String inputdesc){
		
		date = new GregorianCalendar(year, month, day, hour, minute);
		description = inputdesc;
		
	}
	
	public String getDescription(){
		
		return description;
		
	}
	
	public Calendar getDate(){
		
		return date;
		
	}
	
	public void setDescription(String givendesc){
		
		description = givendesc;
		
	}
	
	public void setDate(int year, int month, int day, int hour, int minute){
		
		date = new GregorianCalendar(year, month, day, hour, minute);
		
	}
	
	public String print(){
		
		return String.format("%02d", date.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", date.get(Calendar.MINUTE)) + "	" + description;
		
	}
	
	public boolean occursOn(int year, int month, int day, int hour, int minute){
		
		if(year == date.get(Calendar.YEAR) && month == date.get(Calendar.MONTH) && day == date.get(Calendar.DAY_OF_MONTH) && hour == date.get(Calendar.HOUR_OF_DAY) && minute == date.get(Calendar.MINUTE))
			return true;
		else
			return false;
		
	}
	
	public int compareTo(Appointment app){

		if (this.date.before(app.date))
			return -1;
		else if (this.date.after(app.date))
			return 1;
		return 0;
	}
	
	

}
