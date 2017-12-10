package net.teamfruit.autoinput;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.minecraft.client.settings.KeyBinding;

public class JavaScriptManager {
	public static final JavaScriptManager instance = new JavaScriptManager();

	private final ScriptEngineManager manager;
	private final ScriptEngine javascript;
	private final JFrame frame;

	public JavaScriptManager() {
		this.manager = new ScriptEngineManager();
		this.javascript = this.manager.getEngineByName("JavaScript");
		if (this.javascript==null) {
			this.frame = new JFrame("JavaScript is not supported");
			this.frame.add(new JLabel("JavaScript is not supported"));
			this.frame.pack();
			return;
		}

		this.frame = new JFrame("JavaScript Editor");
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel buttons = new JPanel(new BorderLayout());
		final JButton update = new JButton("Update");
		final JButton keycode = new JButton("Keycode");

		keycode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				keycode.setText("Keycode: "+e.getKeyCode());
				e.consume();
			}
		});
		keycode.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				keycode.setText("Keycode: "+(e.getButton()-100));
				e.consume();
			}
		});

		final JTextArea tarea = new JTextArea();
		update.addActionListener(e -> {
			try {
				this.javascript.eval(
						"function type(code) {JavaScriptManager.instance.keyPress(code)};"
								+"function press(code) {JavaScriptManager.instance.keyPress(code, true)}"
								+"function release(code) {JavaScriptManager.instance.keyPress(code, false)};"
								+"\n"
								+tarea.getText());
				update.setText("Update ✓");
			} catch (final ScriptException e1) {
				update.setText("Update ✘");
			}
		});
		panel.add(tarea, BorderLayout.CENTER);
		buttons.add(update, BorderLayout.CENTER);
		buttons.add(keycode, BorderLayout.EAST);
		panel.add(buttons, BorderLayout.SOUTH);
		this.frame.setSize(800, 800);
		this.frame.add(panel);
	}

	public void keyPress(final int key, final boolean state) {
		KeyBinding.setKeyBindState(key, state);
	}

	public void keyPress(final int key) {
		KeyBinding.onTick(key);
	}

	public void enableGUI() {
		this.frame.setVisible(true);
	}

	public void onTick() {
		if (this.javascript instanceof Invocable) {
			final Invocable invocable = (Invocable) this.javascript;
			try {
				invocable.invokeFunction("onTick");
			} catch (NoSuchMethodException|ScriptException e) {
			}
		}
	}

	public void onEnable() {
		if (this.javascript instanceof Invocable) {
			final Invocable invocable = (Invocable) this.javascript;
			try {
				invocable.invokeFunction("onEnable");
			} catch (NoSuchMethodException|ScriptException e) {
			}
		}
	}
}
