package View;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;



public class MazeDisplay extends Canvas {
    private int[][] maze;
    //Solution solution;
    static public int avatar;
    static public String[] avatars = {"images/IcyTower.PNG","images/sonic.jpg", "images/bugs bunny.jpg","images/sponge.png"};
    private int row_index =0;
    private int col_index =0;
    private int Goal_Row;
    private int Goal_Col;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameroad = new SimpleStringProperty();
    public MazeDisplay(){
        widthProperty().addListener(e->draw());
        heightProperty().addListener(e->draw());
    }
    private String getImageFileNameRoad() {
        return imageFileNameroad.get();
    }
    public String imageFileNameroadProperty() {
        return imageFileNameroad.get();
    }

    public void setImageFileNameroad(String imageFileNameroad) {
        this.imageFileNameroad.set(imageFileNameroad);
    }

    public int getPlayerRow() {
        return row_index;
    }

    public int getPlayerCol() {
        return col_index;
    }

    public int setPlayerPosition(int row, int col) {
        this.row_index = row;
        this.col_index = col;
        draw();
        if(row == Goal_Row&& col==Goal_Col){
            return 1;
        }
        return 0;
    }
    public void drawSol(ArrayList<javafx.geometry.Point2D> solution){
        if (solution == null){
            return;}
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int rows = maze.length;
        int cols = maze[0].length;
        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;
        GraphicsContext graphicsContext = getGraphicsContext2D();
        //clear the canvas:
        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
        drawMazeSolution(solution,graphicsContext, cellHeight, cellWidth);
        drawPlayer(graphicsContext, cellHeight, cellWidth);
    }
    public void drawMazeSolution(ArrayList<javafx.geometry.Point2D> solution,GraphicsContext graphicsContext, double cellHeight, double cellWidth){
        //ArrayList<AState> sol = solution.getSolutionPath();
        for (int i=0; i<solution.size(); i++){
            int SolRow = (int)solution.get(i).getX();
            int SolCol = (int)solution.get(i).getY();
            if (SolCol == Goal_Col && SolRow == Goal_Row){
                continue;}
            else {
                double x = SolCol * cellWidth;
                double y = SolRow * cellHeight;
                graphicsContext.setFill(Color.LIGHTGREEN);
                graphicsContext.fillRect(x, y, cellWidth, cellHeight);
            }
        }
    }
    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(int[][] maze , int start_row, int start_col, int end_row, int end_col) {
        this.maze = maze;
        this.row_index=start_row;
        this.col_index=start_col;
        this.Goal_Col= end_col;
        this.Goal_Row=end_row;
        this.avatar = avatar;
        this.avatars = avatars;
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;
            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }
    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.AQUAMARINE);
        Image wallImage = null;
        Image roadImage = null;
        wallImage = new Image("/images/Wall.PNG");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze[i][j] == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null) {
                        graphicsContext.setFill(Color.AQUAMARINE);
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
                else{
                    if (j == Goal_Col&& i== Goal_Row){
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.setFill(Color.LIGHTGREEN);
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else {
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                            graphicsContext.setFill(Color.BLACK);
                            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
        }
    }



    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.RED);
        Image playerImage = null;

        playerImage = new Image(avatars[avatar]);

        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }
}
