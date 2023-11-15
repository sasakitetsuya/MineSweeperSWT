package main;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

public class SweepLogic {
	
	public void sweep(int x, int y ,int xMax , int yMax 
			,List<List<Button>> buttonList
			,boolean[][] bombFlgArr) {
		int startX;
		int endX;
		int startY;
		int endY;
		int bombCnt = 0;
		
		buttonList.get(y).get(x).setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		if (x == 0) {
			startX = 0;
		} else {
			startX = x - 1;
		}

		if (x == xMax - 1) {
			endX = x;
		} else {
			endX = x + 1;
		}

		if (y == 0) {
			startY = 0;
		} else {
			startY = y - 1;
		}

		if (y == yMax - 1) {
			endY = y;
		} else {
			endY = y + 1;
		}

		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j++) {
				if (bombFlgArr[j][i]) {
					bombCnt++;
				}
			}
		}

		if (bombCnt > 0) {
			buttonList.get(y).get(x).setText(Integer.toString(bombCnt));
		} else {
			for (int i = startX; i <= endX; i++) {
				for (int j = startY; j <= endY; j++) {
					if ( ( i != x || j != y ) &&
							buttonList.get(j).get(i).getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_WHITE))) {
						SweepLogic sweepLogic = new SweepLogic();
						sweepLogic.sweep(i, j, xMax, yMax
								, buttonList, bombFlgArr);
					}
				}
			}
		}
	}
}
