package renderers;

import controllers.GameController;
import enums.GameField;
import enums.GhostName;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Map;

public class TableCell extends JLabel implements TableCellRenderer {
    private GameField gameField;

    private ImageIcon pacmanOpenImgRight;
    private ImageIcon pacmanMidImgRight;
    private ImageIcon pacmanClosedImgRight;

    private ImageIcon pacmanOpenImgLeft;
    private ImageIcon pacmanMidImgLeft;
    private ImageIcon pacmanClosedImgLeft;

    private ImageIcon pacmanOpenImgUp;
    private ImageIcon pacmanMidImgUp;
    private ImageIcon pacmanClosedImgUp;

    private ImageIcon pacmanOpenImgDown;
    private ImageIcon pacmanMidImgDown;
    private ImageIcon pacmanClosedImgDown;

    private ImageIcon blinkyImg;
    private ImageIcon clydeImg;
    private ImageIcon inkyImg;
    private ImageIcon pinkyImg;
    private ImageIcon blueImg;

    private int cellSize;


    private GameController gameController;

    public TableCell(GameController gameController) {

        this.gameController = gameController;

        cellSize = gameController.getCellSize();

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

        pacmanOpenImgRight = new ImageIcon (pacmanOpenImgRightOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanMidImgRight = new ImageIcon(pacmanMidImgRightOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanClosedImgRight = new ImageIcon(pacmanClosedImgRightOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));

        pacmanOpenImgLeft = new ImageIcon(pacmanOpenImgLeftOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanMidImgLeft = new ImageIcon(pacmanMidImgLeftOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanClosedImgLeft = new ImageIcon(pacmanClosedImgLeftOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));

        pacmanOpenImgUp = new ImageIcon(pacmanOpenImgUpOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanMidImgUp = new ImageIcon(pacmanMidImgUpOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanClosedImgUp = new ImageIcon(pacmanClosedImgUpOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));

        pacmanOpenImgDown = new ImageIcon(pacmanOpenImgDownOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanMidImgDown = new ImageIcon(pacmanMidImgDownOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pacmanClosedImgDown = new ImageIcon(pacmanClosedImgDownOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));

        blinkyImg = new ImageIcon(blinkyImgOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        clydeImg = new ImageIcon(clydeImgOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        inkyImg = new ImageIcon(inkyImgOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        pinkyImg = new ImageIcon(pinkyImgOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
        blueImg = new ImageIcon(blueImgOg.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT));
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
                                    setIcon(pacmanOpenImgRight);
                                    break;
                                }

                                case LEFT: {
                                    setIcon(pacmanOpenImgLeft);
                                    break;
                                }

                                case UP: {
                                    setIcon(pacmanOpenImgUp);
                                    break;
                                }

                                case DOWN: {
                                    setIcon(pacmanOpenImgDown);
                                    break;
                                }
                            }
                            break;
                        }

                        case 1: {
                            switch (gameController.getDirection()) {
                                case RIGHT: {
                                    setIcon(pacmanMidImgRight);
                                    break;
                                }

                                case LEFT: {
                                    setIcon(pacmanMidImgLeft);
                                    break;
                                }

                                case UP: {
                                    setIcon(pacmanMidImgUp);
                                    break;
                                }

                                case DOWN: {
                                    setIcon(pacmanMidImgDown);
                                    break;
                                }
                            }
                            break;
                        }

                        case 2: {
                            switch (gameController.getDirection()) {
                                case RIGHT: {
                                    setIcon(pacmanClosedImgRight);
                                    break;
                                }

                                case LEFT: {
                                    setIcon(pacmanClosedImgLeft);
                                    break;
                                }

                                case UP: {
                                    setIcon(pacmanClosedImgUp);
                                    break;
                                }

                                case DOWN: {
                                    setIcon(pacmanClosedImgDown);
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
                                    setIcon(blueImg);
                                } else {
                                    switch (enemy.getKey()) {
                                        case BLINKY: {
                                            setIcon(blinkyImg);
                                            break;
                                        }

                                        case CLYDE: {
                                            setIcon(clydeImg);
                                            break;
                                        }

                                        case INKY: {
                                            setIcon(inkyImg);
                                            break;
                                        }

                                        case PINKY: {
                                            setIcon(pinkyImg);
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
                int dotWidth = cellSize / 5;
                int dotHeight = cellSize / 5;
                g.fillOval(getWidth() / 2 - (dotWidth/2), getHeight() / 2 - (dotHeight/2), dotWidth, dotHeight);
                break;
            }

            case POWERUP: {
                if (gameController.getPowerUpFrame())
                    g.setColor(Color.YELLOW);
                else
                    g.setColor(Color.BLACK);
                int powerUpWidth = (cellSize / 2) + 2;
                int powerUpHeight = (cellSize / 2) + 2;
                g.fillOval(getWidth() / 2 - (powerUpWidth/2), getHeight() / 2 - (powerUpHeight/2), powerUpWidth, powerUpHeight);
            }
        }
    }
}
