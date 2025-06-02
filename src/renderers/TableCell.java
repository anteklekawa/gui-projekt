package renderers;

import controllers.GameController;
import enums.GameField;
import enums.GhostName;
import models.GameModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class TableCell extends JLabel implements TableCellRenderer {
    private GameField gameField;

    private Image pacmanOpenImgRight;
    private Image pacmanMidImgRight;
    private Image pacmanClosedImgRight;

    private Image pacmanOpenImgLeft;
    private Image pacmanMidImgLeft;
    private Image pacmanClosedImgLeft;

    private Image pacmanOpenImgUp;
    private Image pacmanMidImgUp;
    private Image pacmanClosedImgUp;

    private Image pacmanOpenImgDown;
    private Image pacmanMidImgDown;
    private Image pacmanClosedImgDown;

    private Image blinkyImg;
    private Image clydeImg;
    private Image inkyImg;
    private Image pinkyImg;
    private Image blueImg;


    private GameController gameController;

    public TableCell(GameController gameController) {

        this.gameController = gameController;

        setOpaque(true);

        ImageIcon pacmanOpenImgIconRight = new ImageIcon(getClass().getResource("/assets/pacman-right/1.png"));
        ImageIcon pacmanMidImgIconRight = new ImageIcon(getClass().getResource("/assets/pacman-right/2.png"));
        ImageIcon pacmanClosedImgIconRight = new ImageIcon(getClass().getResource("/assets/pacman-right/3.png"));

        ImageIcon pacmanOpenImgIconLeft = new ImageIcon(getClass().getResource("/assets/pacman-left/1.png"));
        ImageIcon pacmanMidImgIconLeft = new ImageIcon(getClass().getResource("/assets/pacman-left/2.png"));
        ImageIcon pacmanClosedImgIconLeft = new ImageIcon(getClass().getResource("/assets/pacman-left/3.png"));

        ImageIcon pacmanOpenImgIconUp = new ImageIcon(getClass().getResource("/assets/pacman-up/1.png"));
        ImageIcon pacmanMidImgIconUp = new ImageIcon(getClass().getResource("/assets/pacman-up/2.png"));
        ImageIcon pacmanClosedImgIconUp = new ImageIcon(getClass().getResource("/assets/pacman-up/3.png"));

        ImageIcon pacmanOpenImgIconDown = new ImageIcon(getClass().getResource("/assets/pacman-down/1.png"));
        ImageIcon pacmanMidImgIconDown = new ImageIcon(getClass().getResource("/assets/pacman-down/2.png"));
        ImageIcon pacmanClosedImgIconDown = new ImageIcon(getClass().getResource("/assets/pacman-down/3.png"));

        ImageIcon blinkyImgIcon = new ImageIcon(getClass().getResource("/assets/ghosts/blinky.png"));
        ImageIcon clydeImgIcon = new ImageIcon(getClass().getResource("/assets/ghosts/clyde.png"));
        ImageIcon inkyImgIcon = new ImageIcon(getClass().getResource("/assets/ghosts/inky.png"));
        ImageIcon pinkyImgIcon = new ImageIcon(getClass().getResource("/assets/ghosts/pinky.png"));
        ImageIcon blueImgIcon = new ImageIcon(getClass().getResource("/assets/ghosts/blue_ghost.png"));

        Image pacmanOpenImgRightOg = pacmanOpenImgIconRight.getImage();
        Image pacmanMidImgRightOg = pacmanMidImgIconRight.getImage();
        Image pacmanClosedImgRightOg = pacmanClosedImgIconRight.getImage();

        Image pacmanOpenImgLeftOg = pacmanOpenImgIconLeft.getImage();
        Image pacmanMidImgLeftOg = pacmanMidImgIconLeft.getImage();
        Image pacmanClosedImgLeftOg = pacmanClosedImgIconLeft.getImage();

        Image pacmanOpenImgUpOg = pacmanOpenImgIconUp.getImage();
        Image pacmanMidImgUpOg = pacmanMidImgIconUp.getImage();
        Image pacmanClosedImgUpOg = pacmanClosedImgIconUp.getImage();

        Image pacmanOpenImgDownOg = pacmanOpenImgIconDown.getImage();
        Image pacmanMidImgDownOg = pacmanMidImgIconDown.getImage();
        Image pacmanClosedImgDownOg = pacmanClosedImgIconDown.getImage();

        Image blinkyImgOg = blinkyImgIcon.getImage();
        Image clydeImgOg = clydeImgIcon.getImage();
        Image inkyImgOg = inkyImgIcon.getImage();
        Image pinkyImgOg = pinkyImgIcon.getImage();
        Image blueImgOg = blueImgIcon.getImage();

        pacmanOpenImgRight = pacmanOpenImgRightOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanMidImgRight = pacmanMidImgRightOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanClosedImgRight = pacmanClosedImgRightOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

        pacmanOpenImgLeft = pacmanOpenImgLeftOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanMidImgLeft = pacmanMidImgLeftOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanClosedImgLeft = pacmanClosedImgLeftOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

        pacmanOpenImgUp = pacmanOpenImgUpOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanMidImgUp = pacmanMidImgUpOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanClosedImgUp = pacmanClosedImgUpOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

        pacmanOpenImgDown = pacmanOpenImgDownOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanMidImgDown = pacmanMidImgDownOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pacmanClosedImgDown = pacmanClosedImgDownOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);

        blinkyImg = blinkyImgOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        clydeImg = clydeImgOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        inkyImg = inkyImgOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        pinkyImg = pinkyImgOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        blueImg = blueImgOg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setIcon(null);
        setText("");

        if (value instanceof GameField) {
            gameField = (GameField) value;

            switch (gameField) {
                case WALL: {
                    setBackground(Color.BLUE);
                    break;
                }

                case EMPTY, DOT, POWERUP: {
                    setBackground(Color.BLACK);
                    break;
                }

                case PLAYER: {
                    setBackground(Color.BLACK);
                    switch (gameController.getPacmanFrame()) {
                        case 0: {
                            switch (gameController.getDirection()) {
                                case RIGHT: {
                                    setIcon(new ImageIcon(pacmanOpenImgRight));
                                    break;
                                }

                                case LEFT: {
                                    setIcon(new ImageIcon(pacmanOpenImgLeft));
                                    break;
                                }

                                case UP: {
                                    setIcon(new ImageIcon(pacmanOpenImgUp));
                                    break;
                                }

                                case DOWN: {
                                    setIcon(new ImageIcon(pacmanOpenImgDown));
                                    break;
                                }
                            }
                            break;
                        }

                        case 1: {
                            switch (gameController.getDirection()) {
                                case RIGHT: {
                                    setIcon(new ImageIcon(pacmanMidImgRight));
                                    break;
                                }

                                case LEFT: {
                                    setIcon(new ImageIcon(pacmanMidImgLeft));
                                    break;
                                }

                                case UP: {
                                    setIcon(new ImageIcon(pacmanMidImgUp));
                                    break;
                                }

                                case DOWN: {
                                    setIcon(new ImageIcon(pacmanMidImgDown));
                                    break;
                                }
                            }
                            break;
                        }

                        case 2: {
                            switch (gameController.getDirection()) {
                                case RIGHT: {
                                    setIcon(new ImageIcon(pacmanClosedImgRight));
                                    break;
                                }

                                case LEFT: {
                                    setIcon(new ImageIcon(pacmanClosedImgLeft));
                                    break;
                                }

                                case UP: {
                                    setIcon(new ImageIcon(pacmanClosedImgUp));
                                    break;
                                }

                                case DOWN: {
                                    setIcon(new ImageIcon(pacmanClosedImgDown));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

                case ENEMY: {
                    Map<GhostName, Point> enemyPos = gameController.getEnemysLocation();

                    if (enemyPos != null) {
                        for (Map.Entry<GhostName, Point> enemy : enemyPos.entrySet()) {
                            if (enemy.getValue().x == row && enemy.getValue().y == column) {
                                if (gameController.getKillPowerUp()) {
                                    setIcon(new ImageIcon(blueImg));
                                } else {
                                    switch (enemy.getKey()) {
                                        case BLINKY: {
                                            setIcon(new ImageIcon(blinkyImg));
                                            break;
                                        }

                                        case CLYDE: {
                                            setIcon(new ImageIcon(clydeImg));
                                            break;
                                        }

                                        case INKY: {
                                            setIcon(new ImageIcon(inkyImg));
                                            break;
                                        }

                                        case PINKY: {
                                            setIcon(new ImageIcon(pinkyImg));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (gameField) {
            case DOT: {
                g.setColor(Color.WHITE);
                g.fillOval(getWidth() / 2 - 2, getHeight() / 2 - 2, 4, 4);
                break;
            }

            case POWERUP: {
                if (gameController.getPowerUpFrame())
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.BLACK);
                g.fillOval(getWidth() / 2 - 8, getHeight() / 2 - 8, 16, 16);
            }
        }
    }
}
