//Dennis Frolov #500776941

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AppointmentFrame extends JFrame {

	/**/
	private static final long serialVersionUID = 1L;
	private JFrame errorFrame;
	private JLabel dateLabel, dayLabel, monthLabel, yearLabel, actionHour, actionMinute, errorLabel;
	private JTextArea appsShown, descBox;
	private JScrollPane scrollBars;
	private JButton prevDate, nextDate, showDate, createAction, cancelAction;
	private JTextField dayBox, monthBox, yearBox, hourBox, minuteBox;
	private JPanel changePanel, setdatePanel, daysPanel, datePanel, timePanel, createPanel,actionPanel, descPanel, allPanel;
	private Calendar curDate;
	private SimpleDateFormat dateFormat;
	private ArrayList<Appointment> appointments;
	private ActionListener listener;
	
	private static final int FRAMEH = 600, FRAMEW = 850, TEXTR = 20, DMWIDTH = 2, YWIDTH = 4, DATECOLS = 10, TIMECOLS = 7, CREATECOLS = 4;
	
	public AppointmentFrame(){			//Calls upon all the required methods to build the sub JPanels and the JFrame
		
		dateLabel = new JLabel("", SwingConstants.CENTER);
		curDate = Calendar.getInstance();
		dateTitle();
		dateLabel.setBorder(new TitledBorder(new EtchedBorder(), ""));
		add(dateLabel, BorderLayout.NORTH);
		
		appointments = new ArrayList<Appointment>();
		
		appointments.add(new Appointment(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), 12, 0, "Lunch with Michael"));
		appointments.add(new Appointment(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), 13, 30, "Study Calculus"));
		appointments.add(new Appointment(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), 8, 45, "Work out in the Gym"));
		
		appsTable();	
		
		daysPanel = new JPanel();
		makeCalendar();
		add(daysPanel, BorderLayout.EAST);
		
		datePanel = new JPanel();
		constructDate();
		datePanel.setLayout(new BorderLayout());
		datePanel.add(changePanel, BorderLayout.NORTH);
		datePanel.add(setdatePanel, BorderLayout.CENTER);
		datePanel.add(showDate, BorderLayout.SOUTH);
		datePanel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
		
		constructAppointment();
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new BorderLayout());
		actionPanel.add(timePanel, BorderLayout.NORTH);
		actionPanel.add(createPanel, BorderLayout.CENTER);
		actionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Action"));
		
		descBox = new JTextArea(4, 30);		
		descPanel = new JPanel();
		descPanel.add(descBox);
		descPanel.setBorder(new TitledBorder(new EtchedBorder(), "Description"));
		
		allPanel = new JPanel();
		allPanel.setLayout(new BorderLayout());
		allPanel.add(datePanel, BorderLayout.NORTH);
		allPanel.add(actionPanel, BorderLayout.CENTER);
		allPanel.add(descPanel, BorderLayout.SOUTH);
		add(allPanel, BorderLayout.SOUTH);
		
		setSize(FRAMEW, FRAMEH);
		
	}
	
	public void dateTitle(){		//This method uses the SimpleDateFormat to set the dateLabel text to the day required
		
		dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
		dateLabel.setText(dateFormat.format(curDate.getTime()));
		 
	}
	
	public void appsTable(){		//This method goes through the ArrayList appointments if it is not empty, and if an appointment occurs on the current date being shown, appends it to the JTextField and add scroll bars
		
		appsShown = new JTextArea(TEXTR, 1);
		appsShown.setText("");
		Collections.sort(appointments);
		if(!appointments.isEmpty()){
			for(int index = 0; index < appointments.size(); index++){
				if(appointments.get(index).getDate().get(Calendar.YEAR) == curDate.get(Calendar.YEAR) && appointments.get(index).getDate().get(Calendar.MONTH) == curDate.get(Calendar.MONTH) && appointments.get(index).getDate().get(Calendar.DAY_OF_MONTH) == curDate.get(Calendar.DAY_OF_MONTH)){
					appsShown.append(appointments.get(index).print() + "\n");
				}
			}
		}
		appsShown.setEditable(false);
		scrollBars = new JScrollPane(appsShown);
		scrollBars.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollBars.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollBars, BorderLayout.CENTER);
		
	}
	
	public void makeCalendar(){
		
		daysPanel.removeAll();
		GridLayout tempGrid = new GridLayout(7,7);
		tempGrid.setHgap(1);
		tempGrid.setVgap(1);
		daysPanel.setLayout(tempGrid);
		int temp = curDate.get(Calendar.DAY_OF_MONTH);
		curDate.set(Calendar.DAY_OF_MONTH,1);
		int counter = 7;
		daysPanel.add(new JLabel("Sunday"));
		daysPanel.add(new JLabel("Monday"));
		daysPanel.add(new JLabel("Tuesday"));
		daysPanel.add(new JLabel("Wednesday"));
		daysPanel.add(new JLabel("Thursday"));
		daysPanel.add(new JLabel("Friday"));
		daysPanel.add(new JLabel("Saturday"));
		for(int i = 1; i < curDate.get(Calendar.DAY_OF_WEEK); i++, counter++) {
			JLabel tempLabel = new JLabel();
			daysPanel.add(tempLabel);
		}
		curDate.set(Calendar.DAY_OF_MONTH, temp);
		int curMonth = curDate.get(Calendar.MONTH);
		if(curMonth <= 6) {
			if(curMonth == 1) {
				if(curDate.get(Calendar.YEAR) % 4 == 0)
					fillDays(29, counter);
				else
					fillDays(28, counter);
			}
			else if(curMonth % 2 == 0)
				fillDays(31, counter);
			else
				fillDays(30, counter);
		}
		else {
			if(curMonth % 2 == 0)
				fillDays(30, counter);
			else
				fillDays(31, counter);
		}
		
	}
	
	public void fillDays(int days, int counter) {
		
		for(int i = 0; i < days; i++, counter++) {
			makeDayBut(i+1);
		}
		for(; counter < 49; counter++) {
			JLabel tempLabel = new JLabel();
			daysPanel.add(tempLabel);
		}
		
	}
	
	public void makeDayBut(int day) {
		
		class PrevListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				curDate.set(Calendar.DAY_OF_MONTH, day);
				dateTitle();
				appsTable();
				
			}
		}
		listener = new PrevListener();
		JButton curDay = new JButton("" + day);
		curDay.addActionListener(listener);
		daysPanel.add(curDay);
		
	}
	
	public void constructDate(){		//This method constructs the sub JPanel showDate using GridLayout
		
		prevButton();
		nextButton();
		
		changePanel = new JPanel();
		changePanel.setLayout(new GridLayout(1, 2));
		changePanel.add(prevDate);
		changePanel.add(nextDate);
		
		dateSpace();		
		
		class ShowDateListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				showButton();
				
			}
		}
		listener = new ShowDateListener();
		showDate = new JButton("Show");
		showDate.addActionListener(listener);
		
	}
	
	public void prevButton(){		//This method is to give the JButton prevDate commands to run when it is pressed, namely subtract 1 from the date and update the appointments table for said date
		
		class PrevListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				curDate.set(Calendar.MONTH, curDate.get(Calendar.MONTH)-1);
				dateTitle();
				appsTable();
				makeCalendar();
				
			}
		}
		listener = new PrevListener();
		prevDate = new JButton("<");
		prevDate.addActionListener(listener);
		
	}
	
	public void nextButton(){		//This method is to give the JButton nextDate commands to run when it is pressed, namely add 1 to the date and update the appointments table for said date
		
		class NextListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				curDate.set(Calendar.MONTH, curDate.get(Calendar.MONTH)+1);
				dateTitle();
				appsTable();
				makeCalendar();
				
			}
		}
		listener = new NextListener();
		nextDate = new JButton(">");
		nextDate.addActionListener(listener);
		
	}
	
	public void dateSpace(){		//Constructs the GridLayout of the JPanel that is used to hold the Day, Month, and Year JLabels and JTextFields
		
		dayLabel = new JLabel("Day");
		monthLabel = new JLabel("Month");
		yearLabel = new JLabel("Year");
		dayBox = new JTextField(DMWIDTH);
		monthBox = new JTextField(DMWIDTH);
		yearBox = new JTextField(YWIDTH);
		setdatePanel = new JPanel();
		setdatePanel.setLayout(new GridLayout(3, DATECOLS));
		fillSpace(DATECOLS, setdatePanel);
		setdatePanel.add(new JLabel());
		setdatePanel.add(dayLabel);
		setdatePanel.add(dayBox);
		setdatePanel.add(new JLabel());
		setdatePanel.add(monthLabel);
		setdatePanel.add(monthBox);
		setdatePanel.add(new JLabel());
		setdatePanel.add(yearLabel);
		setdatePanel.add(yearBox);
		setdatePanel.add(new JLabel());
		fillSpace(DATECOLS, setdatePanel);
		
	}
	
	public void fillSpace(int amount, JPanel tempPanel){		//Fills in a passed GridLayout JPanel with a passed amount of empty JLabels
		
		for(int index = 0; index < amount; index++){
			tempPanel.add(new JLabel());
		}
		
	}
	
	public Boolean checkInteger(String given){		//Goes through the String and checks if each character is an integer using their ASCII value
		
		int[] tester = new int[given.length()];
		for(int index1 = 0; index1 < given.length(); index1++){
			for(int index2 = 48; index2 < 58; index2++){
				if(given.charAt(index1) == index2)
					tester[index1]++;
			}
		}
		int temp = 0;
		for(int index = 0; index < tester.length; index++){
			if(tester[index] == 1)
			temp++;
		}
		if(temp == tester.length)
			return true;
		return false;
	}
	
	public void errorWindow(String message){		//When called upon, creates a new JFrame that pops up with an error saying that the program requires only Integer variables within the required fields
		
		errorFrame = new JFrame();
		errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorFrame.setTitle("ERROR");
	    errorFrame.setVisible(true);
	    errorLabel = new JLabel(message);
	    errorFrame.add(errorLabel);
	    errorFrame.setSize(255, 150);
	    
	}
	
	public void showButton(){		//When the Show button is pressed, checks if the required fields have only integers within them, if so, then shows the requested date
		
		if(dayBox.getText().equals("") || monthBox.getText().equals("") || yearBox.getText().equals("")){
			errorWindow("Please enter a value into each box.");
			yearBox.setText("");
			monthBox.setText("");
			dayBox.setText("");
		}
		else{
			if(checkInteger(dayBox.getText()) && checkInteger(monthBox.getText()) && checkInteger(yearBox.getText())){
				
				if(Integer.parseInt(dayBox.getText()) > 31) 
					errorWindow("Please enter in a day that is not greater than 31.");
				else {
					int givenDay = Integer.parseInt(dayBox.getText());
					int givenMonth = Integer.parseInt(monthBox.getText())-1;
					int givenYear = Integer.parseInt(yearBox.getText());
					curDate = new GregorianCalendar(givenYear, givenMonth, givenDay);
					dateTitle();
					appsTable();
					yearBox.setText("");
					monthBox.setText("");
					dayBox.setText("");
				}
			}
			else{
				yearBox.setText("");
				monthBox.setText("");
				dayBox.setText("");
				errorWindow("Please enter an Integer value into all 3 boxes.");
			}
		}
	}
	
	public void constructAppointment(){		//Constructs the Action Panel using sub JPanels and GridLayout, putting in The CANCEL and CREATE buttons as well as the fields to input the desired hour and minute of appointment
		
		actionHour = new JLabel("Hour");
		hourBox = new JTextField(2);
		actionMinute = new JLabel("Minute");
		minuteBox = new JTextField(2);
		timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(2, TIMECOLS));
		fillSpace(TIMECOLS, timePanel);
		timePanel.add(new JLabel());
		timePanel.add(actionHour);
		timePanel.add(hourBox);
		timePanel.add(new JLabel());
		timePanel.add(actionMinute);
		timePanel.add(minuteBox);
		timePanel.add(new JLabel());
		class CreateListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				createButton();
				
			}
		}
		listener = new CreateListener();
		createAction = new JButton("Create");
		createAction.addActionListener(listener);
		class CancelListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				cancelButton();
				
			}
		}
		listener = new CancelListener();
		cancelAction = new JButton("Cancel");
		cancelAction.addActionListener(listener);
		createPanel = new JPanel();
		createPanel.setLayout(new GridLayout(2, CREATECOLS));
		fillSpace(CREATECOLS, createPanel);
		createPanel.add(new JPanel());
		createPanel.add(createAction);
		createPanel.add(cancelAction);
		createPanel.add(new JPanel());
	}
	
	public void createButton(){		//When the CREATE button is pressed, checks if the current hour and minute already have an appointment assigned to them, if so, prints CONFLICT!! in the description box, if not, creates a new appointment at that time and adds it to the appointments ArrayList
		
		String errorMessage = "Please enter in an Integer value.";
		if(!hourBox.getText().equals("")){
			if(checkInteger(minuteBox.getText()) || minuteBox.getText().isEmpty()){
				int minute = 0;
				if(!minuteBox.getText().isEmpty())
					minute = Integer.parseInt(minuteBox.getText());
				if(checkInteger(hourBox.getText())){
					boolean checker = false;
					for(int index = 0; index < appointments.size(); index++){
						if(appointments.get(index).occursOn(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourBox.getText()), minute))
						checker = true;
					}
					if(!checker){
						Appointment temp = new Appointment(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourBox.getText()), minute, descBox.getText());
						appointments.add(temp);
						appsTable();
						hourBox.setText("");
						minuteBox.setText("");
						descBox.setText("");
					}
					else{
						hourBox.setText("");
						minuteBox.setText("");
						descBox.setText("CONFLICT!!");
					}
				}
				else{
					errorWindow(errorMessage);
					hourBox.setText("");
					minuteBox.setText("");
				}
			}
			else{
				errorWindow(errorMessage);
				hourBox.setText("");
				minuteBox.setText("");
			}
		}
		else{
			errorWindow(errorMessage);
			minuteBox.setText("");
		}
		
	}
	 
	public void cancelButton(){		//When the CANCEL button is pressed, checks if the hour & minute are the same as any preexisting appointments on the current day, and if so, removes them from the list
		
		String errorMessage = "Please enter in an Integer value.";
		if(!hourBox.getText().equals("")){
			int minute = 0;
			if(!minuteBox.getText().isEmpty())
				minute = Integer.parseInt(minuteBox.getText());
			if(checkInteger(hourBox.getText()) && checkInteger(minute + "")){
				if(!appointments.isEmpty()){
					for(int index = 0; index < appointments.size(); index++){
						if(appointments.get(index).occursOn(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH), Integer.parseInt(hourBox.getText()), minute)){
							appointments.remove(index);
						}
					}
					appsTable();
					hourBox.setText("");
					minuteBox.setText("");
				}
			}
			else{
				errorWindow(errorMessage);
				hourBox.setText("");
				minuteBox.setText("");
			}
		}
		else{
			errorWindow(errorMessage);
			minuteBox.setText("");
		}
	}
	
}