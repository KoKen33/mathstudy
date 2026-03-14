package officialInternalAssessment;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// add navigation to all sources of the program
//add functionalities for fractions and percentage questions
// re-factor the program to also make use of google fire-base
public class GUI extends JFrame implements ActionListener{
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private Timer timer;
	JLabel label;
	JTextField text;
	
	//General Pages
	private static final String LOGIN = "Login Portal";
	private static final String CREATION = "User Creation";
	private static final String STUDENTDASH = "Student Dashboard";
	private static final String LEARNMATH = "Learn Math";
	private static final String PRACTICEMATH = "Practice Math";
	private static final String PRACTICEREPORT = "Practice Report";
	private static final String TEACHERDASH = "Teacher Dashboard";
	private static final String STUDENTPROFILE = "Student Profile";
	
	//for login info
	private JTextField usernameFieldLogin;
	private JPasswordField pFieldLogin;
	private JLabel errorLabelLogin;
	
	// for resetting login
	private void resetUserLogin() {
		usernameFieldLogin.setText("");
	    pFieldLogin.setText("");
	    errorLabelLogin.setText("");
	}
	
	// for user creation
	private JTextField usernameFieldCreation;
	private JPasswordField pFieldCreation;
	private JPasswordField cpFieldCreation;
	private JCheckBox studentBox;
	private JCheckBox teacherBox;
	private JLabel errorLabelCreation;

	// Reset method for user creation
	private void resetUserCreationForm() {
	    usernameFieldCreation.setText("");
	    pFieldCreation.setText("");
	    cpFieldCreation.setText("");
	    studentBox.setSelected(false);
	    teacherBox.setSelected(false);
	    errorLabelCreation.setText("");
	}
	
	private User currentUser = null;              // <-- track logged-in user
	private JPanel practiceReportPanel = null;    // <-- keep reference to report panel
	
	public void stopPracticeTimer() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
	// Add this method inside GUI class
	public void startPracticeTimer() {
	    // If you declared 'timer' as a class-level variable
	    if (timer != null && !timer.isRunning()) {
	        timer.start();
	    }
	}
	
	public GUI() {
		super("Math Study");
		
		// Get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create card panel
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		//Create general pages
		cardPanel.add(createLoginPortal(), LOGIN);
		cardPanel.add(createUserCreation(), CREATION);
		cardPanel.add(createStudentDashboard(), STUDENTDASH);
		cardPanel.add(createLearnMath(), LEARNMATH);
		cardPanel.add(createPracticeMath(), PRACTICEMATH);
		cardPanel.add(createTeacherDashboard(), TEACHERDASH);
		cardPanel.add(new JPanel(), STUDENTPROFILE); // cannot call student profile straight away because current user has not been established
		practiceReportPanel = createPracticeReport();
		cardPanel.add(practiceReportPanel, PRACTICEREPORT);

		
		//Create topic pages

		add(cardPanel);
		setVisible(true);
		cardLayout.show(cardPanel, LOGIN);
//		use cardLayout.show to jump directly to a page but the default should always be to LOGIN
//		cardLayout.show(cardPanel, LEARNMATH);
//		cardLayout.show(cardPanel, PRACTICEMATH);


	}
	// login portal done
	private JPanel createLoginPortal() {
	    // Outer panel with GridBagLayout to center everything
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	
	    // Inner panel with BoxLayout for vertical stacking
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
	    // Title
	    JLabel title = new JLabel("Login Site", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 36));
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(title);
	    panel.add(Box.createVerticalStrut(10));
	
	    // Username row
	    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
	    JLabel userLabel = new JLabel("Username:");
	    usernameFieldLogin = new JTextField(15);
	    userPanel.add(userLabel);
	    userPanel.add(usernameFieldLogin);
	    panel.add(userPanel);
	    panel.add(Box.createVerticalStrut(15));
	
	    // Password row
	    JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
	    JLabel passLabel = new JLabel("Password:");
	    pFieldLogin = new JPasswordField(15);
	    passPanel.add(passLabel);
	    passPanel.add(pFieldLogin);
	    panel.add(passPanel);
	    panel.add(Box.createVerticalStrut(15));
	
	    // Login button
	    JButton loginButton = new JButton("    Login    ");
	    loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(loginButton);
	    panel.add(Box.createVerticalStrut(5));
	    
	    // Create User Button
	    JButton createUserButton = new JButton();
	    createUserButton.setText("<html><u> Create new user? </u></html>");
	    createUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(createUserButton);
	    createUserButton.setBorderPainted(false);
	    
	    //Dimension controls
	    Dimension fieldSize = new Dimension(450, 35);
	    usernameFieldLogin.setSize(fieldSize);
	    pFieldLogin.setSize(fieldSize);
//	    usernameField.setMaximumSize(fieldSize);
//	    passwordField.setMaximumSize(fieldSize);

	    Dimension buttonSize = new Dimension(100, 35);
	    loginButton.setSize(buttonSize);
	    createUserButton.setSize(buttonSize);

	
	    // Error label
	    errorLabelLogin = new JLabel("", SwingConstants.CENTER);
	    errorLabelLogin.setForeground(Color.RED);
	    errorLabelLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(errorLabelLogin);
	
	    // Center the inner panel inside the outer panel
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    outerPanel.add(panel, gbc);
	
	    // ActionListener for login button
	    loginButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String userText = usernameFieldLogin.getText().trim();
	            String passText = new String(pFieldLogin.getPassword()).trim();

	            if (userText.isEmpty()) {
	                errorLabelLogin.setText("Error: No username was filled.");
	                return;
	            } else if (passText.isEmpty()) {
	                errorLabelLogin.setText("Error: No password was filled.");
	                return;
	            }

	            User loggedInUser = Records.checkLogin(userText, passText);
	            if (loggedInUser == null) {
	                errorLabelLogin.setText("Error: Invalid username or password.");
	                return;
	            }

	            // Successful login -> store the user
	            currentUser = loggedInUser;         // <-- IMPORTANT
	            errorLabelLogin.setText("");

	            if ("student".equalsIgnoreCase(currentUser.getRole())) {
	                cardLayout.show(cardPanel, STUDENTDASH);
	            } else if ("teacher".equalsIgnoreCase(currentUser.getRole())) {
	                cardLayout.show(cardPanel, TEACHERDASH);
	            }
	        }
	    });

	    
	    createUserButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		resetUserCreationForm();
	    		cardLayout.show(cardPanel, CREATION);
	    	}
	    });
	
	    return outerPanel;
	}
	// user creation done
	private JPanel createUserCreation() {
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();

	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints formGbc = new GridBagConstraints();
	    formGbc.insets = new Insets(8, 8, 8, 8);
	    formGbc.fill = GridBagConstraints.HORIZONTAL;

	    JLabel title = new JLabel("User Creation", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 36));
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;
	    panel.add(title, gbc);

	    JButton returnButton = new JButton("Return to Login");
	    gbc.gridy++;
	    panel.add(returnButton, gbc);

	    // Username
	    JLabel userLabel = new JLabel("Username:");
	    usernameFieldCreation = new JTextField(15);
	    formGbc.gridx = 0; formGbc.gridy = 2;
	    panel.add(userLabel, formGbc);
	    formGbc.gridx = 1;
	    panel.add(usernameFieldCreation, formGbc);

	    // Password
	    JLabel passwordLabel = new JLabel("Password:");
	    pFieldCreation = new JPasswordField(15);
	    formGbc.gridx = 0; formGbc.gridy = 3;
	    panel.add(passwordLabel, formGbc);
	    formGbc.gridx = 1;
	    panel.add(pFieldCreation, formGbc);

	    // Confirm password
	    JLabel cpasswordLabel = new JLabel("Confirm Password:");
	    cpFieldCreation = new JPasswordField(15);
	    formGbc.gridx = 0; formGbc.gridy = 4;
	    panel.add(cpasswordLabel, formGbc);
	    formGbc.gridx = 1;
	    panel.add(cpFieldCreation, formGbc);

	    // Role
	    studentBox = new JCheckBox("Student");
	    teacherBox = new JCheckBox("Teacher");
	    JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
	    rolePanel.add(studentBox);
	    rolePanel.add(new JLabel("or"));
	    rolePanel.add(teacherBox);
	    formGbc.gridx = 0; formGbc.gridy = 5; formGbc.gridwidth = 2;
	    panel.add(rolePanel, formGbc);

	    // Create button
	    JButton createUserButton = new JButton("Create new user");
	    formGbc.gridy = 6; formGbc.gridx = 0; formGbc.gridwidth = 2;
	    panel.add(createUserButton, formGbc);

	    // Error label
	    errorLabelCreation = new JLabel("", SwingConstants.CENTER);
	    errorLabelCreation.setForeground(Color.RED);
	    formGbc.gridy = 7;
	    panel.add(errorLabelCreation, formGbc);

	    gbc = new GridBagConstraints();
	    gbc.gridx = 0; gbc.gridy = 0;
	    outerPanel.add(panel, gbc);

	    // Events
	    returnButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		resetUserLogin();
	    		cardLayout.show(cardPanel, LOGIN);
	    	}
	    });

	    createUserButton.addActionListener(e -> {
	        String userText = usernameFieldCreation.getText().trim();
	        String passText = new String(pFieldCreation.getPassword()).trim();
	        String cpassText = new String(cpFieldCreation.getPassword()).trim();

	        if (userText.isEmpty()) {
	            errorLabelCreation.setText("Error: No username was filled.");
	        } else if (passText.isEmpty()) {
	            errorLabelCreation.setText("Error: No password was filled.");
	        } else if (cpassText.isEmpty()) {
	            errorLabelCreation.setText("Error: Password has not been confirmed.");
	        } else if (!cpassText.equals(passText)) {
	            errorLabelCreation.setText("Error: Password confirmation does not match password");
	        } else if (!studentBox.isSelected() && !teacherBox.isSelected()) {
	            errorLabelCreation.setText("Error: User has not selected role as teacher or student");
	        } else if (studentBox.isSelected() && teacherBox.isSelected()) {
	            errorLabelCreation.setText("Error: User cannot select both student and teacher");
	        } else {
	            if (studentBox.isSelected()) {
	                Records.addUser(new Student(userText, passText));
	            } else if (teacherBox.isSelected()) {
	                Records.addUser(new Teacher(userText, passText));
	            }
	            resetUserCreationForm(); // reset fields after submission
	            cardLayout.show(cardPanel, LOGIN);
	        }
	    });

	    return outerPanel;
	}
	// student dashboard mostly done
	// add a few QoL features like welcome messages
	// student dash board ongoing (fix it so that it uses a database if possible)
	// need to add student profile
	private JPanel createStudentDashboard() {
	    // Outer panel with GridBagLayout to center everything
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	
	    // Inner panel with BoxLayout for vertical stacking
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
	    // Title
	    JLabel title = new JLabel("Student Dashboard", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 36));
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(title);
	    panel.add(Box.createVerticalStrut(10));
	    
	    // View Student Profile button
	    JButton viewProfile = new JButton("👤 View Student Profile");
	    viewProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(viewProfile);
	    panel.add(Box.createVerticalStrut(10));
	
	    try {
	    // Activity Panel
        JPanel activityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // Learn Math button
        URL learnUrl = new URL("https://img.icons8.com/color/96/book.png");
        JButton learnMath = new JButton("Learn Math", new ImageIcon(learnUrl));
        learnMath.setHorizontalTextPosition(SwingConstants.CENTER);
        learnMath.setVerticalTextPosition(SwingConstants.BOTTOM);
        activityPanel.add(learnMath);

        // Practice Math button
        URL practiceUrl = new URL("https://img.icons8.com/color/96/pencil.png");
        JButton practiceMath = new JButton("Practice Math", new ImageIcon(practiceUrl));
        practiceMath.setHorizontalTextPosition(SwingConstants.CENTER);
        practiceMath.setVerticalTextPosition(SwingConstants.BOTTOM);
        activityPanel.add(practiceMath);
        panel.add(activityPanel);
        panel.add(Box.createVerticalStrut(15));

        // Button sizes
        Dimension buttonSize1 = new Dimension(400, 50);
        viewProfile.setPreferredSize(buttonSize1);
        Dimension buttonSize2 = new Dimension(300, 300);
        learnMath.setPreferredSize(buttonSize2);
        practiceMath.setPreferredSize(buttonSize2);
        
        learnMath.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		cardLayout.show(cardPanel, LEARNMATH);
	    	}
	    });
        
        practiceMath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, PRACTICEMATH);
                
                // When entering Practice Math, start timer
                SwingUtilities.invokeLater(() -> {
                    // Call a public method in GUI to start the timer
                    startPracticeTimer();
                });
            }
        });

        
        viewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// logic for establishing the currentUser for the student profile
                if (currentUser != null) {
                    // Remove old profile panel (if it exists)
                    for (Component comp : cardPanel.getComponents()) {
                        if (STUDENTPROFILE.equals(cardPanel.getLayout().toString())) {
                            cardPanel.remove(comp);
                            break;
                        }
                    }
                    // Refreshing logic if students wants to re-check their current history of performances
                    cardPanel.add(createViewStudentProfile(currentUser), STUDENTPROFILE); //Add a fresh profile panel for the logged-in student
                    cardLayout.show(cardPanel, STUDENTPROFILE); //Switching logic
                    cardPanel.revalidate();
                    cardPanel.repaint();
                }
            }
        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    outerPanel.add(panel, gbc);
	    
	    // Logout Button
	    JButton logoutButton = new JButton("Logout");
	    logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(Box.createVerticalStrut(15));
	    panel.add(logoutButton);

	    logoutButton.addActionListener(e -> {
	        currentUser = null;
	        resetUserLogin();
	        cardLayout.show(cardPanel, LOGIN);
	    });

	    
	    return outerPanel;
	}
	// learn math page ongoing
	// maybe add more color/visuals to the GUI
	// create a default topic through topic
	private JPanel createLearnMath() {
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    outerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5); // spacing
	
	    // ===== TOP BAR =====
	    JPanel topBar = new JPanel(new GridBagLayout());
	    GridBagConstraints topGbc = new GridBagConstraints();
	    topGbc.insets = new Insets(5, 5, 5, 5);
	
	    JButton homeButton = new JButton("Home");
	    JLabel title = new JLabel("Learn Math");
	    title.setFont(new Font("Arial", Font.BOLD, 36));
	    JComboBox<String> topicDropdown = new JComboBox<>(new String[]{
	        "Algebra", "Areas & Perimeters" //, "Fractions", "Percentages"
	    });
	
	    // Home button on left
	    topGbc.gridx = 0;
	    topGbc.gridy = 0;
	    topGbc.anchor = GridBagConstraints.WEST;
	    topBar.add(homeButton, topGbc);
	
	    // Title centered
	    topGbc.gridx = 1;
	    topGbc.weightx = 1.0;
	    topGbc.anchor = GridBagConstraints.CENTER;
	    topBar.add(title, topGbc);
	
	    // Dropdown on right
	    topGbc.gridx = 2;
	    topGbc.weightx = 0;
	    topGbc.anchor = GridBagConstraints.EAST;
	    topBar.add(topicDropdown, topGbc);
	
	    // Add top bar to outerPanel
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 3;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    outerPanel.add(topBar, gbc);
	
	    // ===== LEFT: IMAGE =====
	    JPanel imagePanel = new JPanel();
	    imagePanel.setPreferredSize(new Dimension(200, 300));
	    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
	
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 2;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.weightx = 0.2;
	    gbc.weighty = 1.0;
	    outerPanel.add(imagePanel, gbc);
	
	    // ===== CENTER: EXPLANATION =====
	    JPanel explanationPanel = new JPanel(new BorderLayout());
	    explanationPanel.setBorder(BorderFactory.createTitledBorder("Explanation"));
	    JTextArea explanationText = new JTextArea();
	    explanationText.setLineWrap(true);
	    explanationText.setWrapStyleWord(true);
	    explanationPanel.add(new JScrollPane(explanationText), BorderLayout.CENTER);
	
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 2;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.weightx = 0.6;
	    gbc.weighty = 1.0;
	    outerPanel.add(explanationPanel, gbc);
	
	    // ===== RIGHT: EXAMPLES + PRACTICE BUTTON =====
	    JPanel rightPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints rightGbc = new GridBagConstraints();
	
	    JPanel examplesPanel = new JPanel(new BorderLayout());
	    examplesPanel.setBorder(BorderFactory.createTitledBorder("Examples"));
	    JTextArea examplesText = new JTextArea();
	    examplesText.setLineWrap(true);
	    examplesText.setWrapStyleWord(true);
	    examplesPanel.add(new JScrollPane(examplesText), BorderLayout.CENTER);
	
	    rightGbc.gridx = 0;
	    rightGbc.gridy = 0;
	    rightGbc.weightx = 1.0;
	    rightGbc.weighty = 1.0;
	    rightGbc.fill = GridBagConstraints.BOTH;
	    rightPanel.add(examplesPanel, rightGbc);
	
	    JButton practiceButton = new JButton("Practice");
	    practiceButton.setPreferredSize(new Dimension(120, 40));
	    rightGbc.gridx = 0;
	    rightGbc.gridy = 1;
	    rightGbc.weighty = 0;
	    rightGbc.anchor = GridBagConstraints.CENTER;
	    rightPanel.add(practiceButton, rightGbc);
	
	    gbc.gridx = 2;
	    gbc.gridy = 1;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 2;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.weightx = 0.2;
	    gbc.weighty = 1.0;
	    outerPanel.add(rightPanel, gbc);
	
	    // ===== Logic =====
	    homeButton.addActionListener(e -> cardLayout.show(cardPanel, STUDENTDASH));
	    practiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, PRACTICEMATH);
                
                // When entering Practice Math, start timer
                SwingUtilities.invokeLater(() -> {
                    // Call a public method in GUI to start the timer
                    startPracticeTimer();
                });
            }
        });
	
	    topicDropdown.addActionListener(e -> {
	        String selected = (String) topicDropdown.getSelectedItem();
	        Topics.Topic t = Topics.getTopic(selected);
	        if (t != null) {
	            explanationText.setText(t.getExplanation());
	            examplesText.setText(t.getExamples());
	        }
	    });
	
	    // Default selection
	    topicDropdown.setSelectedIndex(0);
	
	    return outerPanel;
	}
	// practice math ongoing
	// maybe add more color/visuals to the GUI
	// functionalities need to be added
	private JPanel createPracticeMath() {
	    // Outer panel with GridBagLayout to center everything
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	
	    // Inner panel with BoxLayout for vertical stacking
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
	    // ===== TOP BAR =====
	    JPanel topBar = new JPanel();
	    topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
	
	    JButton homeButton = new JButton("Home");
	
	    JLabel title = new JLabel("Practice Math");
	    title.setFont(new Font("Arial", Font.BOLD, 32));
	    title.setHorizontalAlignment(SwingConstants.CENTER);
	
	    JComboBox<String> topicDropdown = new JComboBox<>(Topics.topicNames());
	
	    topBar.add(homeButton);
	    topBar.add(Box.createHorizontalStrut(20));
	    topBar.add(title);
	    topBar.add(Box.createHorizontalGlue());
	    topBar.add(topicDropdown);
	
	    panel.add(topBar);
	    panel.add(Box.createVerticalStrut(20));
	
	    // ===== CENTER CONTENT =====
	    // Row: Question + Time
	    JPanel qTimeRow = new JPanel(new BorderLayout());
	    JLabel questionLabel = new JLabel("Question 0 of 10");
	    JLabel timeLabel = new JLabel("Time: 0s");
	    qTimeRow.add(questionLabel, BorderLayout.WEST);
	    qTimeRow.add(timeLabel, BorderLayout.EAST);
	    panel.add(qTimeRow);
	    panel.add(Box.createVerticalStrut(10));
	
	    // Row: Display question
	    JTextField questionField = new JTextField();
	    questionField.setFont(new Font("Arial", Font.PLAIN, 24));
	    questionField.setHorizontalAlignment(JTextField.CENTER);
	    questionField.setEditable(false);
	    questionField.setPreferredSize(new Dimension(600, 50));
	    panel.add(questionField);
	    panel.add(Box.createVerticalStrut(15));
	
	    // Row: Input + Answer button + Next Question Button
	    JPanel inputRow = new JPanel();
	    inputRow.setLayout(new BoxLayout(inputRow, BoxLayout.X_AXIS));
	    JTextField answerField = new JTextField();
	    answerField.setMaximumSize(new Dimension(250, 40));
	    JButton answerButton = new JButton("Answer");
	    answerButton.setMaximumSize(new Dimension(100, 40));
	    JButton nextQButton = new JButton("Next Question");
	    nextQButton.setMaximumSize(new Dimension(150, 40));
	
	    inputRow.add(answerField);
	    inputRow.add(Box.createHorizontalStrut(10));
	    inputRow.add(answerButton);
	    inputRow.add(nextQButton);
	    panel.add(inputRow);
	    panel.add(Box.createVerticalStrut(15));
	
	    // Row: Result
	    JLabel resultLabel = new JLabel("Result", SwingConstants.CENTER);
	    resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(resultLabel);
	
	    // Add inner panel to outerPanel (centered)
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    outerPanel.add(panel, gbc);
	
	    // ===== Logic =====
	    final int[] score = {0};
	    final int[] questionsAsked = {0};
	    final String[] currentAnswer = {""};
	    final boolean[] answered = {false}; // track if student already answered
	
	    // Timer logic
	    final int[] elapsedSeconds = {0};
	    timer = new Timer(1000, e -> {
	        elapsedSeconds[0]++;
	        timeLabel.setText("Time: " + elapsedSeconds[0] + "s");
	    });

		// Apply a DocumentFilter to block letters and decimals
	    ((javax.swing.text.AbstractDocument) answerField.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
	        @Override
	        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) 
	                throws javax.swing.text.BadLocationException {
	            if (string.matches("\\d+")) { // Only allow digits
	                super.insertString(fb, offset, string, attr);
	            }
	        }
	
	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) 
	                throws javax.swing.text.BadLocationException {
	            if (text.matches("\\d+")) { // Only allow digits
	                super.replace(fb, offset, length, text, attrs);
	            }
	        }
	    });
	
	    Runnable newQuestion = () -> {
	        String selectedTopic = (String) topicDropdown.getSelectedItem();
	        String q = Questions.generateQuestion(selectedTopic);
	        questionField.setText(q);
	        currentAnswer[0] = Questions.getAnswer(q);
	        
	        // FIX: Use empty string and ensure focus
	        answerField.setText(""); 
	        answerField.requestFocusInWindow(); 
	        
	        resultLabel.setText("Result");
	
	        // enable answering again
	        answerField.setEnabled(true);
	        answerButton.setEnabled(true);
	        answered[0] = false;
	
	        // update question count
	        questionLabel.setText("Question " + (questionsAsked[0] + 1) + " of 10");
	    };
	
	    // Generate first question
	    newQuestion.run();
	
	    homeButton.addActionListener(e -> {
	        stopPracticeTimer(); // stop timer before leaving
	        cardLayout.show(cardPanel, STUDENTDASH); // then go back to dashboard
	    });

	    
	    // When topic is changed, generate a new question
	    topicDropdown.addActionListener(e -> {
	        // reset counters when switching topics
	        questionsAsked[0] = 0;
	        score[0] = 0;
	        elapsedSeconds[0] = 0;
	        newQuestion.run();
	    });

	
	    answerButton.addActionListener(e -> {
	        if (answered[0]) return; // prevent multiple answers
	
	        String userAnswer = answerField.getText().trim();
	        if (userAnswer.equals(currentAnswer[0])) {
	            resultLabel.setText("Correct!");
	            score[0]++;
	        } else {
	            resultLabel.setText("Incorrect. The Correct answer is " + currentAnswer[0]);
	        }
	
	        questionsAsked[0]++;
	        answered[0] = true; // mark as answered
	
	        // disable input until nextQButton
	        answerField.setEnabled(false);
	        answerButton.setEnabled(false);
	
	        if (questionsAsked[0] >= 10) {
	            timer.stop();

	            // Save to report panel
	            JPanel reportPanel = practiceReportPanel;
	            JLabel scoreLabel = (JLabel) reportPanel.getClientProperty("scoreLabel");
	            JLabel timeTakenLabel = (JLabel) reportPanel.getClientProperty("timeLabel");
	            JTextArea feedbackArea = (JTextArea) reportPanel.getClientProperty("feedbackArea");

	            scoreLabel.setText(score[0] + "/10");
	            timeTakenLabel.setText(elapsedSeconds[0] + "s");

	            java.util.List<String> mistakes = new ArrayList<>();
	            feedbackArea.setText(Feedback.generateFeedback(score[0], 10, mistakes));

	            // Store attempt in student profile
	            if (currentUser != null && "student".equals(currentUser.getRole())) {
	                String topic = (String) topicDropdown.getSelectedItem();
	                Records.recordAttempt(currentUser, topic, score[0], elapsedSeconds[0]);
	            }

	            // Show report card
	            cardLayout.show(cardPanel, PRACTICEREPORT);

	            // Reset
	            score[0] = 0;
	            questionsAsked[0] = 0;
	            elapsedSeconds[0] = 0;
	            timer.start();
	            newQuestion.run();
	        }
	    });
	
	    nextQButton.addActionListener(e -> {
	        if (!answered[0]) {
	            // student skipped without answering
	            resultLabel.setText("Skipped. Correct answer was: " + currentAnswer[0]);
	            questionsAsked[0]++;
	        }
	
	        if (questionsAsked[0] >= 10) {
	            timer.stop();

	            // Save to report panel
	            JPanel reportPanel = practiceReportPanel;
	            JLabel scoreLabel = (JLabel) reportPanel.getClientProperty("scoreLabel");
	            JLabel timeTakenLabel = (JLabel) reportPanel.getClientProperty("timeLabel");
	            JTextArea feedbackArea = (JTextArea) reportPanel.getClientProperty("feedbackArea");

	            scoreLabel.setText(score[0] + "/10");
	            timeTakenLabel.setText(elapsedSeconds[0] + "s");

	            java.util.List<String> mistakes = new ArrayList<>();
	            feedbackArea.setText(Feedback.generateFeedback(score[0], 10, mistakes));

	            // Store attempt in student profile
	            if (currentUser != null && "student".equals(currentUser.getRole())) {
	                String topic = (String) topicDropdown.getSelectedItem();
	                Records.recordAttempt(currentUser, topic, score[0], elapsedSeconds[0]);
	            }

	            // Show report card
	            cardLayout.show(cardPanel, PRACTICEREPORT);

	            // Reset
	            score[0] = 0;
	            questionsAsked[0] = 0;
	            elapsedSeconds[0] = 0;
	            timer.start();
	            newQuestion.run();
	        }


	
	        newQuestion.run();
	    });
	
	    return outerPanel;
	}
	//	practice report ongoing
	// works well but logic needs to better intuitively understood by me the developer
	// make it so results can be stored in the student profile and records which can later be viewed by teacher
	// find ways to make feedback more powerful
	private JPanel createPracticeReport() {
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    // ===== TOP BAR =====
	    JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JButton homeButton = new JButton("Home");
	    JLabel title = new JLabel("Practice Report", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 32));
	    topBar.add(homeButton);

	    panel.add(topBar);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(title);
	    panel.add(Box.createVerticalStrut(20));

	    // ===== SCORE + TIME ROW =====
	    JPanel statsRow = new JPanel(new GridBagLayout());
	    GridBagConstraints statsGbc = new GridBagConstraints();
	    statsGbc.insets = new Insets(10, 20, 10, 20);
	    statsGbc.fill = GridBagConstraints.BOTH;

	    JLabel scoreLabel = new JLabel("Score: 0/10", SwingConstants.CENTER);
	    scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
	    scoreLabel.setBorder(BorderFactory.createTitledBorder("Total Score"));

	    JLabel timeLabel = new JLabel("0s", SwingConstants.CENTER);
	    timeLabel.setFont(new Font("Arial", Font.BOLD, 28));
	    timeLabel.setBorder(BorderFactory.createTitledBorder("Time Taken"));

	    statsGbc.gridx = 0; statsGbc.gridy = 0; statsGbc.weightx = 0.5;
	    statsRow.add(scoreLabel, statsGbc);

	    statsGbc.gridx = 1; statsGbc.gridy = 0; statsGbc.weightx = 0.5;
	    statsRow.add(timeLabel, statsGbc);

	    panel.add(statsRow);
	    panel.add(Box.createVerticalStrut(20));

	    // ===== FEEDBACK SECTION =====
	    JPanel feedbackPanel = new JPanel(new BorderLayout());
	    feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedbacks"));
	    JTextArea feedbackArea = new JTextArea("Feedback will appear here...");
	    feedbackArea.setLineWrap(true);
	    feedbackArea.setWrapStyleWord(true);
	    feedbackArea.setEditable(false);
	    feedbackPanel.add(new JScrollPane(feedbackArea), BorderLayout.CENTER);
	    panel.add(feedbackPanel);
	    panel.add(Box.createVerticalStrut(20));

	    // ===== RETRY BUTTON =====
	    JButton retryButton = new JButton("Retry");
	    retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(retryButton);

	    // Add everything to outerPanel
	    gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1; gbc.weighty = 1;
	    outerPanel.add(panel, gbc);

	    // ===== Logic =====
	    homeButton.addActionListener(e -> cardLayout.show(cardPanel, STUDENTDASH));
	    retryButton.addActionListener(e -> cardLayout.show(cardPanel, PRACTICEMATH));

	    // Store components for later update
	    outerPanel.putClientProperty("scoreLabel", scoreLabel);
	    outerPanel.putClientProperty("timeLabel", timeLabel);
	    outerPanel.putClientProperty("feedbackArea", feedbackArea);

	    return outerPanel;
	}
	// student profile view ongoing
	// add more aggregated values to help with more insight into student performance (average scores, fastest time completion, how many practices done so far, etc)
	private JPanel createViewStudentProfile(User student) {
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    outerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10); 
	    gbc.fill = GridBagConstraints.BOTH; 
	    gbc.weightx = 1; 
	
	    // ===== Title =====
	    JLabel title = new JLabel("Practice Report - " + student.getUsername(), SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 28));
	
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.weighty = 0;
	    outerPanel.add(title, gbc);
	
	    // ===== Table Model =====
	    Student s = (Student) currentUser;
	    String[] columns = {"Topic", "Score", "Time Taken (s)"};
	    java.util.List<Student.PracticeAttempt> history = s.getHistory();

	    Object[][] data = new Object[history.size()][3];
	    for (int i = 0; i < history.size(); i++) {
	        Student.PracticeAttempt attempt = history.get(i);
	        data[i][0] = attempt.topic;
	        data[i][1] = attempt.score + "/10";
	        data[i][2] = attempt.timeTaken;
	    }

	
	    JTable table = new JTable(data, columns);
	    table.setEnabled(false);
	    table.setRowHeight(25);
	    JScrollPane scrollPane = new JScrollPane(table);
	
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.weighty = 1;
	    outerPanel.add(scrollPane, gbc);
	
	    // ===== Aggregate Stats =====
	    double avgScore = 0;
	    int fastestTime = Integer.MAX_VALUE;
	    int practices = history.size();
	
	    if (!history.isEmpty()) {
	        int totalScore = 0;
	        for (Student.PracticeAttempt attempt : history) {
	            totalScore += attempt.score;
	            if (attempt.timeTaken < fastestTime) {
	                fastestTime = attempt.timeTaken;
	            }
	        }
	        avgScore = (double) totalScore / practices;
	    }
	
	    String statsText = history.isEmpty()
	            ? "No practice attempts yet."
	            : String.format("Average Score: %.2f | Fastest Time: %ds | Total Practices: %d",
	                            avgScore, fastestTime == Integer.MAX_VALUE ? 0 : fastestTime, practices);
	
	    JLabel statsLabel = new JLabel(statsText, SwingConstants.CENTER);
	    statsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
	
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.weighty = 0;
	    outerPanel.add(statsLabel, gbc);
	
	    // ===== Back Button =====
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(e -> cardLayout.show(cardPanel, STUDENTDASH));
	
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(backButton);
	
	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    gbc.weighty = 0;
	    outerPanel.add(buttonPanel, gbc);
	
	    return outerPanel;
	}	
	// teacher dashboard ongoing
	// later aesthetics can be improved
	private JPanel createTeacherDashboard() {
	    JPanel outerPanel = new JPanel(new GridBagLayout());
	    outerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.fill = GridBagConstraints.BOTH;
	
	    // ===== Title =====
	    JLabel title = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 32));
	
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;   // span across two columns
	    gbc.weightx = 1;
	    gbc.weighty = 0;
	    outerPanel.add(title, gbc);
	
	    // ===== LEFT PANEL: Controls =====
	    JPanel leftPanel = new JPanel();
	    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	
	    // Collect topics
	    Set<String> topics = new HashSet<>();
	    for (Student s : Records.students) {
	        for (Student.PracticeAttempt pa : s.getHistory()) {
	            topics.add(pa.topic);
	        }
	    }
	    JComboBox<String> topicDropdown = new JComboBox<>(topics.toArray(new String[0]));
	    topicDropdown.setMaximumSize(new Dimension(200, 30));
	
	    leftPanel.add(new JLabel("Select Topic:"));
	    leftPanel.add(topicDropdown);
	    leftPanel.add(Box.createVerticalStrut(20));
	
	    JButton highestBtn = new JButton("Find highest average score");
	    JButton lowestBtn = new JButton("Find lowest average score");
	    leftPanel.add(highestBtn);
	    leftPanel.add(Box.createVerticalStrut(10));
	    leftPanel.add(lowestBtn);
	    leftPanel.add(Box.createVerticalStrut(20));
	
	    JTextArea displayBox = new JTextArea(3, 20);
	    displayBox.setEditable(false);
	    displayBox.setLineWrap(true);
	    displayBox.setWrapStyleWord(true);
	    JScrollPane displayScroll = new JScrollPane(displayBox);
	    leftPanel.add(displayScroll);
	
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridwidth = 1;
	    gbc.weightx = 0.3; // left side smaller
	    gbc.weighty = 1;
	    outerPanel.add(leftPanel, gbc);
	
	    // ===== RIGHT PANEL: Table =====
	    String[] columns = {"Name", "Recent Score", "Average Score", "Recent Time (s)"};
	    DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
	    JTable table = new JTable(tableModel);
	    table.setRowHeight(25);
	    JScrollPane scrollPane = new JScrollPane(table);
	
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.gridwidth = 1;
	    gbc.weightx = 0.7; // table gets more space
	    gbc.weighty = 1;
	    outerPanel.add(scrollPane, gbc);
	    
	    JButton logoutButton = new JButton("Logout");

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridwidth = 2;
	    gbc.weightx = 1;
	    gbc.weighty = 0;
	    outerPanel.add(logoutButton, gbc);

	
	    // ===== Populate table function =====
	    Runnable refreshTable = () -> {
	        String selectedTopic = (String) topicDropdown.getSelectedItem();
	        tableModel.setRowCount(0);
	
	        for (Student s : Records.students) {
	            java.util.List<Student.PracticeAttempt> attempts = s.getHistory().stream()
	                    .filter(a -> selectedTopic == null || a.topic.equals(selectedTopic))
	                    .toList();
	
	            if (!attempts.isEmpty()) {
	                Student.PracticeAttempt recent = attempts.get(attempts.size() - 1);
	                double avg = attempts.stream().mapToInt(a -> a.score).average().orElse(0);
	
	                tableModel.addRow(new Object[]{
	                        s.getUsername(),
	                        recent.score + "/10",
	                        String.format("%.2f/10", avg),
	                        recent.timeTaken
	                });
	            }
	        }
	    };
	
	    if (topicDropdown.getItemCount() > 0) {
	        topicDropdown.setSelectedIndex(0);
	        refreshTable.run();
	    }
	
	    topicDropdown.addActionListener(e -> refreshTable.run());
	
	    // ===== Buttons Logic =====
	    highestBtn.addActionListener(e -> {
	        String selectedTopic = (String) topicDropdown.getSelectedItem();
	        User best = null;
	        double bestAvg = -1;
	
	        for (Student s : Records.students) {
	            double avg = s.getHistory().stream()
	                    .filter(a -> a.topic.equals(selectedTopic))
	                    .mapToInt(a -> a.score)
	                    .average().orElse(-1);
	
	            if (avg > bestAvg) {
	                bestAvg = avg;
	                best = s;
	            }
	        }
	
	        if (best != null) {
	            displayBox.setText("Highest average for " + selectedTopic + ": "
	                    + best.getUsername() + " (" + String.format("%.2f", bestAvg) + "/10)");
	        } else {
	            displayBox.setText("No data available for this topic.");
	        }
	    });
	
	    lowestBtn.addActionListener(e -> {
	        String selectedTopic = (String) topicDropdown.getSelectedItem();
	        User worst = null;
	        double worstAvg = Double.MAX_VALUE;
	
	        for (Student s : Records.students) {
	            double avg = s.getHistory().stream()
	                    .filter(a -> a.topic.equals(selectedTopic))
	                    .mapToInt(a -> a.score)
	                    .average().orElse(-1);
	
	            if (avg >= 0 && avg < worstAvg) {
	                worstAvg = avg;
	                worst = s;
	            }
	        }
	
	        if (worst != null) {
	            displayBox.setText("Lowest average for " + selectedTopic + ": "
	                    + worst.getUsername() + " (" + String.format("%.2f", worstAvg) + "/10)");
	        } else {
	            displayBox.setText("No data available for this topic.");
	        }
	    });
	    
	    // Action: clear current user and return to LOGIN
	    logoutButton.addActionListener(e -> {
	        currentUser = null;
	        resetUserLogin();
	        cardLayout.show(cardPanel, LOGIN);
	    });
	
	    return outerPanel;
	}

	public static void main(String[] args) {
		Records.loadData();
		new GUI();	
	}

		@Override
		public void actionPerformed(ActionEvent e) {
				text.setText(label.getText());
		}
}
