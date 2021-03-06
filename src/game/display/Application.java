package game.display;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import game.drawer.Map;

public class Application extends JFrame {

	public Application() {
		JMenuBar menu_bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem about = getAboutGame();
		JMenuItem exit = getExitLabelMenu();

		menu.add(about);
		menu.add(exit);

		menu.add(new JSeparator());
		menu_bar.add(menu);

		setJMenuBar(menu_bar);
		add(new Map());

		setTitle("Space Invaders");

		CreateWindowGame();

	}

	private JMenuItem getAboutGame() {
		JMenuItem about = new JMenuItem("About");

		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Developed by Barreiros", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		return about;
	}

	private JMenuItem getExitLabelMenu() {
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		return exit;
	}

	private void CreateWindowGame() {
		setSize(Game.getWidth(), Game.getHeight());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Application app = new Application();
				app.setVisible(true);
			}
		});
	}
}