import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class gameeasy extends Application {
    private int initialSceneWidth = 900;
    private int initialSceneHeight = 950;

    private MapAndChars mapAndChars;
    private AnimationTimer timer;
    private long startTime = 0;
    private long trophyGetTime = 0; // トロフィー取得時間を記録
    private Stage primaryStage; // start() で Stage を保持

    @Override
    public void start(Stage st) throws Exception {
        primaryStage = st; // Stage を保持

        // Start scene
        Group startRoot = new Group();
        Scene startScene = new Scene(startRoot, initialSceneWidth, initialSceneHeight, Color.rgb(120, 200, 0));
        Image backgroundImage = new Image("./image/background.png"); // 適切なパスに置き換えてください
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(initialSceneWidth); // 画像の幅をシーンの幅に合わせる
        backgroundView.setFitHeight(initialSceneHeight); // 画像の高さをシーンの高さに合わせる
        startRoot.getChildren().add(backgroundView); // 背景画像をstartRootに追加	
        VBox startVBox = new VBox(50);
        startVBox.setTranslateX(initialSceneWidth / 2 - 50);
        startVBox.setTranslateY(initialSceneHeight / 2 - 25);

        Text titleText = new Text("きみはねずみだ！ねこからにげろ！");
        titleText.setFont(Font.font(40)); // フォントサイズを指定
        titleText.setFill(Color.YELLOW); // 色を指定
        titleText.setX(initialSceneWidth / 2 - 210); // X座標を指定
        titleText.setY(initialSceneHeight / 2 - 100); // Y座標を指定
        startRoot.getChildren().add(titleText); // startRootに追加

	 Text titleText1 = new Text("一番上のチーズを取ったらクリアー");
        titleText1.setFont(Font.font(40)); // フォントサイズを指定
        titleText1.setFill(Color.YELLOW); // 色を指定
        titleText1.setX(initialSceneWidth / 2 - 210); // X座標を指定
        titleText1.setY(initialSceneHeight / 2 - 190); // Y座標を指定
        startRoot.getChildren().add(titleText1); // startRootに追加

	Text titleText2 = new Text("ねこのもり");
        titleText2.setFont(Font.font(60)); // フォントサイズを指定
        titleText2.setFill(Color.RED); // 色を指定
        titleText2.setX(initialSceneWidth / 2 - 120); // X座標を指定
        titleText2.setY(initialSceneHeight / 2 - 300); // Y座標を指定
        startRoot.getChildren().add(titleText2); // startRootに追加

	Text titleText3 = new Text("注意：ねことおなじ道にいくとおっかけてくるよ！！");
        titleText3.setFont(Font.font(35)); // フォントサイズを指定
        titleText3.setFill(Color.RED); // 色を指定
        titleText3.setX(10); // X座標を指定
        titleText3.setY(600); // Y座標を指定
        startRoot.getChildren().add(titleText3); // startRootに追加


        Button startButton = new Button("ゲームスタート");

        startButton.setOnAction(e -> {
            startGame(); // startGame(st) を startGame() に変更
        });

        startVBox.getChildren().add(startButton);
        startRoot.getChildren().add(startVBox);

        st.setTitle("Tile-based Game");
        st.setScene(startScene);
        st.show();
    }

    private void startGame() { // startGame(st) を startGame() に変更
        Group root = new Group();
        Random rnd = new Random();
        Scene scene = new Scene(root, initialSceneWidth, initialSceneHeight, Color.rgb(rnd.nextInt(256),rnd.nextInt(256) ,rnd.nextInt(256)));

       	Image backgroundImage1 = new Image("./image/back1.jpg"); // 適切なパスに置き換えてください
	  ImageView backgroundView1 = new ImageView(backgroundImage1);
	  backgroundView1.setFitWidth(initialSceneWidth); 
	 backgroundView1.setFitHeight(initialSceneHeight); 
	 root.getChildren().add(backgroundView1);
	
        primaryStage.setScene(scene); // primaryStage を使用する

        scene.setOnKeyPressed(this::keyPressed);

        mapAndChars = new MapAndChars(root, initialSceneWidth, initialSceneHeight);

        // タイマーの設定
        timer = new AnimationTimer() {
            @Override
            public void handle(long t) {
                if (startTime == 0) {
                    startTime = t;
                }
                if (!mapAndChars.isGameOver() && !mapAndChars.hasTrophy()) {
                    mapAndChars.drawScreen(60 - (int) ((t - startTime) / 1000000000));
                } else if (mapAndChars.hasTrophy()) {
                    if (trophyGetTime == 0) {
                        trophyGetTime = t;
                    }
                    mapAndChars.drawScreen(0);
                    if (t - trophyGetTime >= 12000000000L) { // 12秒経過
                        timer.stop();
                        showClearScene(); // showClearScene(st) を showClearScene() に変更
                    }
                } else { // ゲームオーバー時
                    timer.stop(); // タイマーを停止
                    showGameOverScene(); // ゲームオーバーシーンを表示
                }

            }
        };

        timer.start();
    }

    private void showClearScene() { // showClearScene(st) を showClearScene() に変更
        Group clearRoot = new Group();
        Scene clearScene = new Scene(clearRoot, initialSceneWidth, initialSceneHeight, Color.rgb(0, 0, 0));
	// 背景画像を追加
        Image backgroundImage = new Image("./image/clear.jpg"); // 適切なパスに置き換えてください
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(initialSceneWidth); 
        backgroundView.setFitHeight(initialSceneHeight); 
        clearRoot.getChildren().add(backgroundView); 

        Text clearText = new Text("クリアー");
        clearText.setFont(Font.font(40));
        clearText.setFill(Color.YELLOW);
        clearText.setX(initialSceneWidth / 2 - 50);
        clearText.setY(initialSceneHeight / 2);

        // ボタンを追加
        Button startButton = new Button("ゲームスタート");
        startButton.setTranslateX(initialSceneWidth / 2 - 50);
        startButton.setTranslateY(initialSceneHeight / 2 + 50);
        startButton.setOnAction(e -> {
            startTime = 0;
            trophyGetTime = 0;
            startGame();
        });


        // ボタンを追加
        Button backtostartTitle = new Button("タイトルへ");
        backtostartTitle.setTranslateX(initialSceneWidth / 2 - 50);
        backtostartTitle.setTranslateY(initialSceneHeight / 2 + 70);
        backtostartTitle.setOnAction(e -> {
            startTime = 0;
            trophyGetTime = 0;
            showStartScene();
        });

        clearRoot.getChildren().addAll(clearText, startButton, backtostartTitle);
        primaryStage.setScene(clearScene); // primaryStage を使用する
    }

    private void showGameOverScene() {
        Group Root = new Group();
        Scene gameoverScene = new Scene(Root, initialSceneWidth, initialSceneHeight, Color.rgb(0, 0, 0));
        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font(40));
        gameOverText.setFill(Color.RED);
        gameOverText.setX(initialSceneWidth / 2 - 100);
        gameOverText.setY(initialSceneHeight / 2);


        AnimationTimer gameOverTimer = new AnimationTimer() {
            long gameOverStartTime = 0;

            @Override
            public void handle(long now) {
                if (gameOverStartTime == 0) {
                    gameOverStartTime = now;
                }

                // 彦星のサイズを小さくするアニメーション
                mapAndChars.drawBoy();
            }
        };
        gameOverTimer.start();

        Button backtostartTitle = new Button("タイトルへ");
        backtostartTitle.setTranslateX(initialSceneWidth / 2 - 50);
        backtostartTitle.setTranslateY(initialSceneHeight / 2 + 70);
        backtostartTitle.setOnAction(e -> {
            startTime = 0;
            trophyGetTime = 0;
            gameOverTimer.stop();
            showStartScene();
        });

        Root.getChildren().addAll(gameOverText, backtostartTitle);
        primaryStage.setScene(gameoverScene);

    }

    private void showStartScene() {
        try {
            start(primaryStage); // start() を再呼び出し
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {
        launch(a);
    }

    public void keyPressed(KeyEvent e) {
        KeyCode key = e.getCode();
        int dir = -1;
        switch (key) {
            case LEFT:
                dir = 2;
                break;
            case RIGHT:
                dir = 0;
                break;
            case UP:
                dir = 1;
                break;
            case DOWN:
                dir = 3;
                break;
        }
        if (dir >= 0 && !mapAndChars.hasTrophy())
            mapAndChars.boyMove(dir);
    }
}

class MapAndChars {
    // 二次元マップの準備
    private char[][] map;
    private int MX = 18, MY = 20;
    private String[] initialMap = {
            "T               G ",
            "BLBBBBLBBBBLBBLBBB",
            " L    L     G     ",
            "BLBBLBBBBBBLBBBBBB",
            " L          L     ",
            "BLBLBBBBBBBBLBBBBB",
            " L L    G     L   ",
            "BLBLBBLBLBBLBBBBBB",
            " L L         L    ",
            "BLBBLBLBBLBLBBLBBB",
            " L    L   G   L   ",
            "BLBBBLBLLLBLBBBLBB",
            " L      L  L  G   ",
            "BLBLBBLBLBBLLBBBBB",
            " L L   L    L     ",
            "BBBBLBBLBLBLBBBLBB",
            "         L        ",
            "BLBLBLBLBBBBBBBLBB",
            "          M       ",
            "BBBBBBBBBBBBBBBBBB",
    };

    // 彦星
    private int boyX, boyY;
    ImageView boyView;
    private double boySize = 39;
    private Line erase1, erase2;

    // 織姫 (複数)
    private List<Girl> girls = new ArrayList<>();
    private Image girlImage01, girlImage02;

    // トロフィー
    private Trophy trophy;


    // トロフィを持っているかどうか
    private boolean hasTrophy = false;

    private boolean gameOver = false;
    private Text gameOverText;
    private Text timeText;

    public boolean hasTrophy() {
        return hasTrophy;
    }

    MapAndChars(Group root, int initialSceneWidth, int initialSceneHeight) {
        // マップの周囲を見えない壁で囲む
        map = new char[MY + 2][MX + 2];
        for (int x = 0; x <= MX + 1; x++) {
            map[0][x] = 'B';
            map[MY + 1][x] = 'B';
        }
        for (int y = 0; y <= MY + 1; y++) {
            map[y][0] = 'B';
            map[y][MX + 1] = 'B';
        }
        // マップデータの読み込み
        for (int y = 1; y <= MY; y++) {
            for (int x = 1; x <= MX; x++) {
                map[y][x] = initialMap[y - 1].charAt(x - 1);
            }
        }



        timeText = new Text(55, 50, "Time: 30");
        timeText.setFont(new Font("TimeRoman", 18));
        timeText.setFill(Color.ORANGE);
        root.getChildren().add(timeText);

        erase1 = new Line(0, 0, 0, 0);
        erase2 = new Line(0, 0, 0, 0);
        erase1.setStroke(Color.CYAN);
        erase2.setStroke(Color.CYAN);
        root.getChildren().add(erase1);
        root.getChildren().add(erase2);

        drawInitialMapAndChars(root);
    }

    public void drawInitialMapAndChars(Group root) {
        Image image;
        for (int y = 1; y <= MY; y++) {
            for (int x = 1; x <= MX; x++) {
                int xx = 40 * x + 20, yy = 40 * y + 20;
                switch (map[y][x]) {
                    case 'L':
                        image = new Image("./image/ladder.png"); // はしごの画像
                        ImageView ladderView = new ImageView(image);
                        ladderView.setFitWidth(40);
                        ladderView.setFitHeight(40);
                        ladderView.setX(xx);
                        ladderView.setY(yy);
                        root.getChildren().add(ladderView);
                        break;
                    case 'B':
                        image = new Image("./image/block1.jpg"); // ブロックの画像
                        ImageView blockView = new ImageView(image);
                        blockView.setFitWidth(40);
                        blockView.setFitHeight(40);
                        blockView.setX(xx);
                        blockView.setY(yy);
                        root.getChildren().add(blockView);
                        break;
                    case 'T':
                        trophy = new Trophy(x, y); // Trophyクラスのインスタンスを生成
                        root.getChildren().add(trophy.getCanvas()); // CanvasをGroupに追加
                        break;
                    case 'G':
                        girlImage01 = new Image("./image/paku1.png"); // 
                        girlImage02 = new Image("./image/paku2.png"); // 
                        ImageView girlView = new ImageView(girlImage01);
                        girlView.setFitWidth(40);
                        girlView.setFitHeight(40);
                        girls.add(new Girl(x, y, girlView));
                        root.getChildren().add(girlView);
                        break;
                    case 'M':
                        image = new Image("./image/nezumi.png"); // 
                        boyView = new ImageView(image);
                        boyView.setFitWidth(50);
                        boyView.setFitHeight(50);
                        boyX = x;
                        boyY = y;
                        root.getChildren().add(boyView);
                        break;
                }
            }
        }
    }

    // 描画処理をまとめたメソッド
    public void drawScreen(int t) {
        // rotateTrophy();
        drawTrophy();
        drawBoy();
        moveGirls();
        drawGirls();
        displayTime(t);
    }

    public void drawBoy() {

        if (gameOver) boySize -= 0.25;
        switch ((int) boySize) {
            case 39:
                if (!hasTrophy) {
                    boyView.setX(40 * boyX + 31);
                    boyView.setY(40 * boyY + 20);
                }
                boyView.toFront();
                break;
            case 0:
                boyView.setImage(null);
            case -1:
            case -2:
            case -3:
            case -4:
            case -5:
                int xx = 40 * boyX + 31 + 10, yy = 40 * boyY + 20 + 20;
                erase1.setStartX(xx + boySize);
                erase1.setStartY(yy + boySize);
                erase1.setEndX(xx - boySize);
                erase1.setEndY(yy - boySize);
                erase2.setStartX(xx - boySize);
                erase2.setStartY(yy + boySize);
                erase2.setEndX(xx + boySize);
                erase2.setEndY(yy - boySize);
                break;
            default:
                boyView.setFitHeight(boySize);
                boyView.setFitWidth(boySize / 2);
                boyView.setX(40 * boyX + 31 + (10 - boySize / 4));
                boyView.setY(40 * boyY + 20 + (20 - boySize / 2));
        }

    }

    public void boyMove(int dir) {
        if (!hasTrophy) {
            int dx = 0, dy = 0;
            switch (dir) {
                case 0:
                    dx = 1;
                    break;
                case 1:
                    dy = -1;
                    break;
                case 2:
                    dx = -1;
                    break;
                case 3:
                    dy = 1;
                    break;
            }
            if (dx == 0 && dy == 0) return;
            if (map[boyY + dy][boyX + dx] == 'B') return;
            boyX += dx;
            boyY += dy;
            if (map[boyY][boyX] == ' ' && map[boyY + 1][boyX] == ' ') boyMove(3);
            if (map[boyY][boyX] == 'T') getItem();
        }
    }

    //  public void rotateTrophy() {
    //    trophy.rotate();
    // }

    public void drawTrophy() {
        if (hasTrophy) {
            trophy.clear(); // トロフィーを非表示にする
        } else {
            trophy.draw(); // トロフィーを描画する
        }
    }

    public void drawGirls() {
        for (Girl girl: girls) {
            girl.draw();
        }
    }

    public void moveGirls() {
        for (Girl girl : girls) {
            if (!hasTrophy) {
                girl.move(map, boyX, boyY);
            } else {
                girl.dance();
            }
        }
    }


    public void displayTime(int t) {
        if (gameOver) {
            timeText.setFill(Color.RED);
            timeText.setText("Game Over");
        } else if (hasTrophy) {
            timeText.setFill(Color.YELLOW);
            timeText.setText("Clear!");
        } else {
            timeText.setFill(Color.ORANGE);
            timeText.setText("Time: " + t);
            if (t == 0) gameOver = true;
        }
    }

    public void getItem() {
        hasTrophy = true;
        boyView.setX(40 * boyX + 31);
        boyView.setY(40 * boyY + 20);
    }

    public boolean isGameOver() {
        for (Girl girl : girls) {
            if (boyX == (int) girl.getX() && boyY == (int) girl.getY()) {
                gameOver = true;
                return true; // ゲームオーバーを検知したらすぐに true を返す
            }

        }
        return false;
    }
}

// 織姫クラス
class Girl {
    private double x, y;
    private ImageView view;
    private int moveCounter = 0;
    private int direction = 0; // 0: right, 1: up, 2: left, 3: down
    private int speed = 12; //数字を小さくすると速くなる
    private int danceCounter = 0;
    private double targetX, targetY; // 目標座標
    private double moveX, moveY; // 1フレームあたりの移動量

    public Girl(double x, double y, ImageView view) {
        this.x = x;
        this.y = y;
        this.view = view;
        this.targetX = x;
        this.targetY = y;
        draw();
    }

    public void draw() {
        view.setX(40 * x + 31);
        view.setY(40 * y + 20);
        view.toFront();
    }

    public void move(char[][] map, int boyX, int boyY) {
        moveCounter++;
        if (moveCounter % speed == 0) {
            Random rand = new Random();
            int dx = 0, dy = 0;
            boolean moved = false;

            if (boyY == (int) y) {
                if (boyX > x && map[(int) y][(int) x + 1] != 'B') {
                    targetX = x + 1;
                    moved = true;
                } else if (boyX < x && map[(int) y][(int) x - 1] != 'B') {
                    targetX = x - 1;
                    moved = true;
                }
            }
            if (!moved) {
                for (int i = 0; i < 4; i++) {
                    direction = rand.nextInt(4);
                    dx = 0;
                    dy = 0;

                    switch (direction) {
                        case 0: // right
                            dx = 1;
                            break;
                        case 1: // up
                            dy = -1;
                            break;
                        case 2: // left
                            dx = -1;
                            break;
                        case 3: // down
                            dy = 1;
                            break;
                    }

                    if (map[(int) y + dy][(int) x + dx] != 'B') {
                        targetX = x + dx;
                        targetY = y + dy;
                        moved = true;
                        break;
                    }
                }
                if (!moved) {
                    switch (direction) {
                        case 0:
                            direction = 2;
                            break;
                        case 1:
                            direction = 3;
                            break;
                        case 2:
                            direction = 0;
                            break;
                        case 3:
                            direction = 1;
                            break;
                    }

                }
            }

            // 目標座標への移動量を計算
            moveX = (targetX - x) / speed;
            moveY = (targetY - y) / speed;
        }

        x += moveX;
        y += moveY;

        if (map[(int) y][(int) x] == ' ' && map[(int) y + 1][(int) x] == ' ') {
            targetY = y + 1;
            moveY = (targetY - y) / speed;
        }
    }

    public void dance() {
        danceCounter = (danceCounter + 1) % 120;
        switch (danceCounter) {
            case 0:
                view.setImage(new Image("./image/paku1.png")); //
                break;
            case 18:
                view.setImage(new Image("./image/paku2.png")); // 
                break;
            case 60:
                view.setImage(new Image("./image/paku1.png")); // 
                break;
            case 66:
                view.setImage(new Image("./image/paku2.png")); // 
                break;
            case 72:
                view.setImage(new Image("./image/paku1.png")); // 
                break;
            case 78:
                view.setImage(new Image("./image/paku2.png")); // 
                break;
            case 84:
                view.setImage(new Image("./image/paku1.png")); // 
                break;
            case 102:
                view.setImage(new Image("./image/paku2.png")); // 
                break;
            case 110:
                view.setImage(new Image("./image/paku1.png")); // 
                break;
            case 119:
                view.setImage(new Image("./image/paku2.png")); // 
                break;

        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

abstract class Tile {
    protected Group parts;

    Tile(int x, int y) {
        parts = new Group();
        parts.setTranslateX(x);
        parts.setTranslateY(y);
        construct();
    }

    public Group getParts() {
        return parts;
    }

    abstract public void construct();
}

class Ladder extends Tile {

    Ladder(int x, int y) {
        super(x, y);
    }

    @Override
    public void construct() { // 抽象メソッドの実装（継承したクラスで実装しなければならない）
        Color c = Color.CYAN;

        Rectangle r1 = new Rectangle(4, 0, 4, 40);
        r1.setFill(c);
        Rectangle r2 = new Rectangle(32, 0, 4, 40);
        r2.setFill(c);
        Rectangle r3 = new Rectangle(8, 8, 24, 4);
        r3.setFill(c);
        Rectangle r4 = new Rectangle(8, 28, 24, 4);
        r4.setFill(c);
        parts.getChildren().add(r1);
        parts.getChildren().add(r2);
        parts.getChildren().add(r3);
        parts.getChildren().add(r4);
    }
}


class Block extends Tile {

    Block(int x, int y) {
        super(x, y);
    }

    @Override

    public void construct() { // 抽象メソッドの実装（継承したクラスで実装しなければならない）
        Color c = Color.GREEN;

        Rectangle r1 = new Rectangle(0, 0, 26, 10);
        r1.setFill(c);
        Rectangle r2 = new Rectangle(32, 0, 8, 10);
        r2.setFill(c);
        Rectangle r3 = new Rectangle(0, 15, 10, 10);
        r3.setFill(c);
        Rectangle r4 = new Rectangle(16, 15, 24, 10);
        r4.setFill(c);
        Rectangle r5 = new Rectangle(0, 30, 18, 10);
        r5.setFill(c);
        Rectangle r6 = new Rectangle(24, 30, 16, 10);
        r6.setFill(c);
        parts.getChildren().add(r1);
        parts.getChildren().add(r2);
        parts.getChildren().add(r3);
        parts.getChildren().add(r4);
        parts.getChildren().add(r5);
        parts.getChildren().add(r6);
    }
}

class Trophy {
    private Image trophyImage; // トロフィー画像
    private Canvas canvas;
    private GraphicsContext gc;

    Trophy(int initX, int initY) {
        trophyImage = new Image("./image/tro.png"); // トロフィー画像を読み
        canvas = new Canvas(40, 40);
        gc = canvas.getGraphicsContext2D();
        canvas.setTranslateX(initX * 40 + 20);
        canvas.setTranslateY(initY * 40 + 20);
    }

    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(trophyImage, 0, 0, 40, 40); // トロフィー画像を描画
    }

    public void clear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // rotateメソッドは削除

    public Canvas getCanvas() {
        return canvas;
    }
}
