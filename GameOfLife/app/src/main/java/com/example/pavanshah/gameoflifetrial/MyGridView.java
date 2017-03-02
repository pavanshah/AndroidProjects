package com.example.pavanshah.gameoflifetrial;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyGridView extends View {

    private int Columns = 12, Rows = 12;
    private int cellWidth, cellHeight;
    private Paint yellowPaint = new Paint();
    private Paint cellColor = new Paint();
    private boolean[][] currentGeneration, nextGeneration;

    public MyGridView(Context context) {
        this(context, null);
        initGrid();
        setWillNotDraw(false);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        yellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yellowPaint.setColor(Color.BLACK);
        yellowPaint.setStrokeWidth(5);
        cellColor.setStyle(Paint.Style.FILL_AND_STROKE);
        cellColor.setColor(Color.RED);
    }

    public void generateNext()
    {
        int totalNeighbours;
        nextGeneration = new boolean[Columns][Rows];

        Log.d("PAVAN", "Generate Next");

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                totalNeighbours = findNeighbours(i, j);

                if (currentGeneration[i][j] != false) {
                    if ((totalNeighbours >= 2) && (totalNeighbours <= 3)) {
                        nextGeneration[i][j] = true;
                    }
                } else {
                    if (totalNeighbours == 3) {
                        nextGeneration[i][j] = true;
                    }
                }
            }
        }

        currentGeneration = nextGeneration;

        invalidate();
    }

    private int findNeighbours(int i, int j)
    {
        Log.d("PAVAN", "Calculate neighbours");
        int total = (currentGeneration[i][j] != false) ? -1 : 0;
        for (int h = -1; h <= +1; h++) {
            for (int w = -1; w <= +1; w++) {
                if (currentGeneration[(12 + (i + h)) % 12][(12 + (j + w))
                        % 12] != false) {
                    total++;
                }
            }
        }
        return total;
    }

    public void resetGrid()
    {
        Log.d("PAVAN", "Reset Grid");

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("This action will reset the game!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < Columns; i++) {
                            for (int j = 0; j < Rows; j++) {
                                currentGeneration[i][j] = false;
                            }
                        }

                        invalidate();

                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void initGrid() {

        cellWidth = getWidth() / Columns;
        cellHeight = getHeight() / Rows;

        currentGeneration = new boolean[Columns][Rows];

        Log.d("PAVAN", "Calculate dimentions");

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initGrid();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        Log.d("PAVAN", "On Draw");

        int width = getWidth();
        int height = getHeight();

        if (Columns == 0 || Rows == 0) {
            return;
        }

        float circX = cellWidth/2;
        float circY = cellHeight/2;

        for (int i = 0; i < Columns; i++) {
            for (int j = 0; j < Rows; j++) {
                if (currentGeneration[i][j]) {

                    canvas.drawCircle(i * cellWidth+circX, j * cellHeight+circY, circX, cellColor);
                }
            }
        }

        for (int i = 1; i < Columns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, yellowPaint);
        }

        for (int i = 1; i < Rows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, yellowPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int c = (int)(event.getX() / cellWidth);
            int r = (int)(event.getY() / cellHeight);

            currentGeneration[c][r] = !currentGeneration[c][r];
            invalidate();
        }

        return true;
    }
}
