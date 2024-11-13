package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Set;

public class ArrowTower extends Towers implements Observer {

    private static final int FRAME_WIDTH = 192;  // Largeur d'une frame
    private static final int FRAME_HEIGHT = 190; // Hauteur d'une frame
    private static final int COLUMNS = 6;        // Colonnes dans le sprite sheet
    private static final int ROWS = 2;           // Lignes dans le sprite sheet

    private boolean isShooting = false;
    private Timeline animation;
    private int currentFrame = 0;

    // Set statique pour suivre les positions occupées
    private static Set<String> occupiedPositions = new HashSet<>();

    // Méthode pour vérifier si une position est occupée
    public static boolean isPositionOccupied(double x, double y) {
        String positionKey = x + "," + y;
        return occupiedPositions.contains(positionKey);
    }

    // Méthode pour marquer une position comme occupée
    public static void markPositionAsOccupied(double x, double y) {
        String positionKey = x + "," + y;
        occupiedPositions.add(positionKey);
    }

    // Méthode pour créer une nouvelle tour à une position donnée
    public static ArrowTower createArrowTower(double x, double y) {
        if (isPositionOccupied(x, y)) {
            System.out.println("Une tour existe déjà à cet endroit, impossible d'en placer une autre.");
            return null;  // Retourne null si la position est déjà occupée
        }

        // Marque la position comme occupée
        markPositionAsOccupied(x, y);

        // Créer et retourner l'instance de ArrowTower
        return new ArrowTower(x, y);
    }

    // Constructeur
    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer_Blue.png");  // Chemin de l'image du sprite

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
        // Calculer la colonne et la ligne de la frame courante
        int col = currentFrame % COLUMNS;
        int row = currentFrame / COLUMNS;

        // Mettre à jour le viewport pour afficher la frame actuelle
        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        // Passer à la frame suivante
        currentFrame = (currentFrame + 1) % (COLUMNS * ROWS);
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
