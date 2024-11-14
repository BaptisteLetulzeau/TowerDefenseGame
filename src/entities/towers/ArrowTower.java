package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class ArrowTower extends Towers implements Observer {

    private static final int FRAME_WIDTH = 192;  // Largeur d'une frame
    private static final int FRAME_HEIGHT = 190; // Hauteur d'une frame
    private static final int COLUMNS = 8;        // Colonnes dans le sprite sheet
    private static final int ROWS = 7;           // Lignes dans le sprite sheet

    private static ArrowTower uniqueTower = null;  // Tour unique

    private boolean isShooting = false;  // Indicateur pour savoir si la tour tire
    private Timeline animation;  // Animation de la tour
    private Timeline updateTimer;  // Timer pour les mises à jour régulières
    private int currentFrame = 0;  // Frame actuelle de l'animation

    private List<Enemies> observedEnemies = new ArrayList<>(); // Liste d'ennemis observés

    // Force de détection et portée
    private static final double MAX_RANGE = 200;  // Portée maximale de la tour

    // Méthode pour créer une seule et unique instance de la tour
    public static ArrowTower createArrowTower(double x, double y) {
        if (uniqueTower == null) {
            uniqueTower = new ArrowTower(x, y);
            System.out.println("Tour ArrowTower créée à : " + x + ", " + y);
        } else {
            System.out.println("Une seule tour peut être créée. Impossible d'en créer une autre.");
        }
        return uniqueTower;
    }

    // Constructeur privé pour empêcher la création de plusieurs instances
    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer.png");  // Chemin de l'image du sprite
        setX(x);
        setY(y);
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
        startAnimation();
        startUpdateTimer();
    }

    private void startAnimation() {
        // Animation de la tour (mise à jour toutes les 100ms)
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            updateFrame();
            if (isShooting) {
                shoot();
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void startUpdateTimer() {
        // Démarre un timer pour appeler la méthode update() à intervalles réguliers
        updateTimer = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            // Appeler la méthode update pour chaque ennemi observé (si existants)
            for (Enemies enemy : observedEnemies) {
                update(enemy); // Appel à la méthode update() pour chaque ennemi observé
            }
        }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);
        updateTimer.play();
    }

    private void updateFrame() {
        // Calcul de la frame à afficher dans le sprite
        int maxFrames = 6;  // Seulement les 6 premières frames sont valides
        int col = currentFrame % maxFrames;
        int row = currentFrame / maxFrames;
        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        currentFrame = (currentFrame + 1) % maxFrames;
    }

    @Override
    public void update(Enemies enemy) {
        // Vérifie si l'ennemi est dans la portée de la tour et tire en conséquence
        System.out.println("Vérification de l'ennemi à : " + enemy.getX() + ", " + enemy.getY());  // Message de débogage

        if (isInRange(enemy)) {
            isShooting = true;  // Commence à tirer si l'ennemi est dans la portée
            System.out.println("L'ennemi est dans la portée, tir en cours!");
        } else {
            isShooting = false;  // Arrête de tirer si l'ennemi est hors de portée
            System.out.println("L'ennemi est hors de portée.");
        }
    }

    private void shoot() {
        if (isShooting) {
            System.out.println("ArrowTower tire !");
            // Logique pour tirer des flèches ici (ex: tirer une flèche)
        }
    }

    private boolean isInRange(Enemies enemy) {
        // Calcule la distance entre la tour et l'ennemi
        double distance = Math.sqrt(Math.pow(enemy.getX() - getX(), 2) + Math.pow(enemy.getY() - getY(), 2));
        return distance < MAX_RANGE;  // Portée de tir de la tour, à ajuster si nécessaire
    }

    // Méthode pour arrêter l'animation et le timer si nécessaire
    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }

    // Cette méthode permet de vérifier s'il y a déjà une tour
    public static boolean isTowerPlaced() {
        return uniqueTower != null;
    }

    // Méthode pour ajouter cette tour comme observateur d'un ennemi
    public static void addObserverToEnemy(Enemies enemy) {
        if (uniqueTower != null) {
            uniqueTower.addEnemyToObserve(enemy);  // Ajoute l'ennemi à la liste d'observation de la tour
            enemy.addObserver(uniqueTower);  // Ajoute la tour comme observateur
            System.out.println("Tour commence à observer l'ennemi à : " + enemy.getX() + ", " + enemy.getY());
        }
    }

    // Méthode pour retirer cette tour comme observateur d'un ennemi
    public static void removeObserverFromEnemy(Enemies enemy) {
        if (uniqueTower != null) {
            uniqueTower.removeEnemyFromObserve(enemy);  // Retire l'ennemi de la liste d'observation
            enemy.removeObserver(uniqueTower);  // Retire la tour comme observateur
            System.out.println("Tour arrête d'observer l'ennemi.");
        }
    }

    // Ajoute un ennemi à la liste des ennemis observés
    private void addEnemyToObserve(Enemies enemy) {
        if (!observedEnemies.contains(enemy)) {
            observedEnemies.add(enemy);
        }
    }

    // Retire un ennemi de la liste des ennemis observés
    private void removeEnemyFromObserve(Enemies enemy) {
        observedEnemies.remove(enemy);
    }
}
