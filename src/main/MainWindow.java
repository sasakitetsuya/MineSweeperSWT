package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow {

	protected Shell shell;
	private Button resetButton;
	private Label bombLabel;
	private Label timerLabel;
	private MessageBox msgGameOver;
	private MessageBox msgGameClear;
	private List<List<Button>> buttonList;
	private boolean[][] bombFlgArr;
	private int bombNum = 10;
	private int bombFlagCnt = 0;
	private int xMax = 9;
	private int yMax = 9;
	private MenuItem joukyuMenuItem;
	private MenuItem chukyuMenuItem;
	private MenuItem shokyuMenuItem;
	private LocalDateTime startTime;
	private boolean startFlg = false;
	private boolean gameOverFlg = false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		createCells();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(40 + 25 * xMax, 110 + 25 * yMax);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem superMenuItem = new MenuItem(menu, SWT.NONE);
		superMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bombNum = 500;
				xMax = 70;
				yMax = 35;
				clearCells();
			}
		});
		superMenuItem.setText("超人");

		joukyuMenuItem = new MenuItem(menu, SWT.NONE);
		joukyuMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bombNum = 99;
				xMax = 30;
				yMax = 16;
				clearCells();
			}
		});
		joukyuMenuItem.setText("上級");

		chukyuMenuItem = new MenuItem(menu, SWT.NONE);
		chukyuMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bombNum = 40;
				xMax = 16;
				yMax = 16;
				clearCells();
			}
		});
		chukyuMenuItem.setText("中級");

		shokyuMenuItem = new MenuItem(menu, SWT.NONE);
		shokyuMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bombNum = 10;
				xMax = 9;
				yMax = 9;
				clearCells();
			}
		});
		shokyuMenuItem.setText("初級");

		resetButton = new Button(shell, SWT.NONE);
		resetButton.setFont(SWTResourceManager.getFont("Yu Gothic UI", 15, SWT.NORMAL));
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearCells();
			}
		});
		resetButton.setBounds(106, 0, 30, 31);
		resetButton.setText("☺");

		msgGameOver = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		msgGameOver.setText("ゲームオーバー");
		msgGameOver.setMessage("爆発しましたΣ(ﾟДﾟ)");

		msgGameClear = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.CANCEL);
		msgGameClear.setText("クリア");
		
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Yu Gothic UI", 15, SWT.NORMAL));
		lblNewLabel.setBounds(10, 0, 30, 31);
		lblNewLabel.setText("⏰");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Yu Gothic UI", 15, SWT.NORMAL));
		lblNewLabel_1.setBounds(146, 0, 30, 31);
		lblNewLabel_1.setText("🚩");
		
		timerLabel = new Label(shell, SWT.NONE);
		timerLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		timerLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		timerLabel.setAlignment(SWT.RIGHT);
		timerLabel.setFont(SWTResourceManager.getFont("Yu Gothic UI", 15, SWT.NORMAL));
		timerLabel.setBounds(46, 0, 47, 31);
		timerLabel.setText("0");
		
		bombLabel = new Label(shell, SWT.NONE);
		bombLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		bombLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		bombLabel.setAlignment(SWT.RIGHT);
		bombLabel.setFont(SWTResourceManager.getFont("Yu Gothic UI", 15, SWT.NORMAL));
		bombLabel.setBounds(171, 1, 47, 30);
		bombLabel.setText("10");

	}

	private void createCells() {
		int bombCnt = 0;
		bombFlgArr = new boolean[yMax][xMax];
		buttonList = new ArrayList<>();

		while (bombCnt < bombNum) {
			Random rand = new Random();
			int tmpX = rand.nextInt(xMax);
			int tmpY = rand.nextInt(yMax);
			if (!bombFlgArr[tmpY][tmpX]) {
				bombFlgArr[tmpY][tmpX] = true;
				bombCnt++;
			}
		}

		for (int i = 0; i < yMax; i++) {
			List<Button> tmpButtonList = new ArrayList<>();
			for (int j = 0; j < xMax; j++) {
				Button btnNewButton = new Button(shell, SWT.NONE);
				Integer integerX = j;
				Integer integerY = i;
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						if(gameOverFlg) {
							return;
						}
						
						if (e.button == 3 && btnNewButton.getBackground().equals(
								SWTResourceManager.getColor(SWT.COLOR_WHITE))) {
							if (btnNewButton.getText().equals("")) {
								btnNewButton.setText("🚩");
								bombFlagCnt++;
								bombLabel.setText(Integer.toString(bombNum - bombFlagCnt));
							} else if (btnNewButton.getText().equals("🚩")) {
								btnNewButton.setText("");
								bombFlagCnt--;
								bombLabel.setText(Integer.toString(bombNum - bombFlagCnt));
							}
						} else {
							if (bombFlgArr[integerY][integerX]) {
								btnNewButton.setText("💣");
								resetButton.setText("😵");
								gameOverFlg = true;
								msgGameOver.open();
							} else {
								sweep(integerX, integerY);
							}
						}
					}
				});
				btnNewButton.setFont(SWTResourceManager.getFont(
						"Yu Gothic UI", 15, SWT.NORMAL));
				btnNewButton.setBounds(25 * j + 10, 25 * i + 30, 27, 27);
				btnNewButton.setBackground(SWTResourceManager.getColor(
						SWT.COLOR_WHITE));

				tmpButtonList.add(btnNewButton);

			}
			buttonList.add(tmpButtonList);
		}

	}

	private void clearCells() {
		shell.setSize(40 + 25 * xMax, 110 + 25 * yMax);
		for (List<Button> list : buttonList) {
			for (Button button : list) {
				button.dispose();
			}
		}
		createCells();
		resetButton.setText("☺");
		bombLabel.setText(Integer.toString(bombNum));
		gameOverFlg = false;
		bombFlagCnt = 0;
	}

	void sweep(int x, int y) {
		if (!startFlg) {
			startTime = LocalDateTime.now();
			startFlg = true;
		}

		SweepLogic sweepLogic = new SweepLogic();
		sweepLogic.sweep(x, y, xMax, yMax, buttonList, bombFlgArr);

		int sweepCnt = 0;
		for (List<Button> list : buttonList) {
			for (Button button : list) {
				if (button.getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_GRAY))) {
					sweepCnt++;
				}
			}
		}
		if (sweepCnt == (xMax * yMax) - bombNum) {
			startFlg = false;
			LocalDateTime endTime = LocalDateTime.now();
			Duration clearDuration = Duration.between(startTime, endTime);
			msgGameClear.setMessage("クリアしました(∩´∀｀)∩\nタイム："
					+ clearDuration.toSecondsPart()
					+ "." + clearDuration.toMillisPart()
					+ "秒");
			msgGameClear.open();
			
		}
	}
}
