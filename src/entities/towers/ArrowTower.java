package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class ArrowTower extends Towers implements Observer {

    private static final int FRAME_WIDTH = 192;  // Largeur d'une frame
    private static final int FRAME_HEIGHT = 190; // Hauteur d'une frame
    private static final int COLUMNS = 8;        // Colonnes dans le sprite sheet
    private static final int ROWS = 7;           // Lignes dans le sprite sheet

    // Variable pour stocker l'unique instance de ArrowTower
    private static ArrowTower uniqueTower = null;

    private boolean isShooting = false;
    private Timeline animation;
    private int currentFrame = 0;

    // Méthode pour créer une seule et unique tour
    public static ArrowTower createArrowTower(double x, double y) {
        if (uniqueTower != null) {
            System.out.println("Une seule tour peut être créée. Impossible d'en créer une autre.");
            return null;  // Empêche toute autre création de tour
        }

        // Créer et stocker l'instance unique de ArrowTower
        uniqueTower = new ArrowTower(x, y);
        return uniqueTower;
    }

    // Constructeur privé pour empêcher des instanciations externes
    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer.png");  // Chemin de l'image du sprite

        setX(x);
        setY(y);

        // Fixer la taille de la frame
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);

        // Initialiser le viewport à la première frame du sprite sheet
        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));

        startAnimation();
    }

    private void startAnimation() {
        // Animation qui met à jour la frame toutes les 100 ms
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            updateFrame();
            if (isShooting) {
                shoot();
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE); // Animation continue
        animation.play();
    }

    private void updateFrame() {
        // Limite le nombre de frames aux 6 premières colonnes (sur 2 lignes)
        int maxFrames = 6;  // Seulement 6 frames dans les 2 premières lignes

        // Calculer la colonne de la frame courante en prenant en compte les 6 premières colonnes
        int col = currentFrame % maxFrames;  // Ici, maxFrames = 6
        int row = currentFrame / maxFrames;  // Sur 2 lignes (0 ou 1)

        // Mettre à jour le viewport pour afficher la frame actuelle
        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        // Passer à la frame suivante, limité à 6 colonnes
        currentFrame = (currentFrame + 1) % maxFrames + (row * maxFrames);
    }
    
    @Override
    public void update(Enemies enemy) {
        if (isInRange(enemy)) {
            isShooting = true;  // Commence à tirer quand l'ennemi est dans la portée
        } else {
            isShooting = false;  // Arrête de tirer quand l'ennemi est hors de portée
        }
    }

    private void shoot() {
        if (isShooting) {
            System.out.println("ArrowTower tire !");
            // Logique pour tirer des flèches ici
        }
    }

    private boolean isInRange(Enemies enemy) {
        double distance = Math.sqrt(Math.pow(enemy.getX() - getX(), 2) + Math.pow(enemy.getY() - getY(), 2));
        return distance < 200;  // Portée de la tour
    }

    // Méthode pour arrêter l'animation si nécessaire
    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }
}
