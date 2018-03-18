package com.flitey;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeGame extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static final long TASK_UPDATE_PERIOD_MS = 100;
	private static final long TASK_UPDATE_DELAY_MS = TASK_UPDATE_PERIOD_MS;

	private static final int WINDOW_HEIGHT = 400;
	private static final int WINDOW_WIDTH = 400;
	private static final int GRID_BLOCK_SIZE = 10;

	Snake snake;
	Grid grid;
	AnimationTimer animationTimer;
	Timer timer;
	TimerTask task;

	private boolean isGameOver = false;
	private boolean isPaused = false;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Snake");
		Group root = new Group();
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);

		grid = new Grid(WINDOW_WIDTH, WINDOW_HEIGHT, GRID_BLOCK_SIZE);

		snake = new Snake(WINDOW_WIDTH, WINDOW_HEIGHT);
		snake.setBlockSize(GRID_BLOCK_SIZE);
		snake.setHeadLocation(GRID_BLOCK_SIZE, GRID_BLOCK_SIZE);
		snake.setDirection(Direction.RIGHT);

		scene.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.UP) {
				snake.setDirection(Direction.UP);
			} else if (e.getCode() == KeyCode.DOWN) {
				snake.setDirection(Direction.DOWN);
			} else if (e.getCode() == KeyCode.LEFT) {
				snake.setDirection(Direction.LEFT);
			} else if (e.getCode() == KeyCode.RIGHT) {
				snake.setDirection(Direction.RIGHT);
			} else if (e.getCode() == KeyCode.P) {
				if (isPaused) {
					task = createTimerTask();
					timer = new Timer("Timer");
					timer.scheduleAtFixedRate(task, TASK_UPDATE_DELAY_MS, TASK_UPDATE_PERIOD_MS);
					isPaused = false;
				} else {
					timer.cancel();
					isPaused = true;
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				if (isGameOver) {
					animationTimer.stop();
					showEndGameAlert();
				} else {
					drawGrid(gc);
					drawSnake(gc);
					drawFood(gc);
				}
			}
		};
		animationTimer.start();

		task = createTimerTask();
		timer = new Timer("Timer");
		timer.scheduleAtFixedRate(task, TASK_UPDATE_DELAY_MS, TASK_UPDATE_PERIOD_MS);
	}

	@Override
	public void stop() {
		System.out.println("stopping");
		if (timer != null) {
			timer.cancel();
		}
	}

	private TimerTask createTimerTask() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				snake.snakeUpdate();

				if (snake.collidedWithWall()) {
					endGame("collided with wall");
				} else if (snake.collidedWithTail()) {
					endGame("collided with tail");
				}

				boolean foundFood = grid.foundFood(snake);
				if (foundFood) {
					snake.addTailSegment();
					grid.addFood();
					System.out.println("Snake length: " + snake.getTail().size());
				}
			}
		};
		return task;
	}

	private void endGame(String reason) {
		timer.cancel();
		isGameOver = true;
		System.out.println("Game over: " + reason);
	}

	private void showEndGameAlert() {
		Alert alertDialog = new Alert(AlertType.INFORMATION);
		alertDialog.setTitle("Game Over");
		alertDialog.setHeaderText("Game over. Your final snake length was: " + (snake.getTail().size() + 1) + ".");
		alertDialog.setContentText("Press OK to start a new game");

		alertDialog.show();
	}

	private void drawGrid(GraphicsContext gc) {

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		gc.setStroke(Color.LIGHTGRAY);
		gc.setLineWidth(0.5);

		for (int x = 0; x < WINDOW_WIDTH; x += GRID_BLOCK_SIZE) {
			gc.strokeLine(x, 0, x, x + WINDOW_HEIGHT);
		}

		for (int y = 0; y < WINDOW_HEIGHT; y += GRID_BLOCK_SIZE) {
			gc.strokeLine(0, y, y + WINDOW_WIDTH, y);
		}

	}

	private void drawSnake(GraphicsContext gc) {

		gc.setFill(Color.GREEN);
		gc.fillRect(snake.getHeadLocation().getX(), snake.getHeadLocation().getY(), snake.getBlockSize(),
				snake.getBlockSize());
		for (Point tailSegment : snake.getTail()) {
			gc.fillRect(tailSegment.getX(), tailSegment.getY(), snake.getBlockSize(), snake.getBlockSize());
		}
	}

	private void drawFood(GraphicsContext gc) {

		gc.setFill(Color.BLUE);

		gc.fillRect(grid.getFood().getLocation().getX(), grid.getFood().getLocation().getY(), GRID_BLOCK_SIZE,
				GRID_BLOCK_SIZE);

	}
}
