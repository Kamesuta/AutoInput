package net.teamfruit.autoinput;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;

public class JavaScriptManager {
	public static final JavaScriptManager instance = new JavaScriptManager();

	private final ScriptEngineManager manager;
	private ScriptEngine javascript;
	private final JFrame frame;

	public PrintStream console;

	private Bindings rootBindings;
	private Bindings userBindings;

	public JavaScriptManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}

		this.manager = new ScriptEngineManager(ClassLoader.getSystemClassLoader());
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
		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(.5);

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
				int mousebutton = e.getButton()-101;
				if (mousebutton==-98)
					mousebutton = -99;
				else if (mousebutton==-99)
					mousebutton = -98;
				keycode.setText("Keycode: "+mousebutton);
				e.consume();
			}
		});

		final JTextArea tarea = new JTextArea();
		final JTextArea consolearea = new JTextArea();
		final DefaultCaret caret = (DefaultCaret) consolearea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		consolearea.setEditable(false);
		final OutputStream consoleout = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				consolearea.append(String.valueOf((char) b));
			}
		};
		this.console = new PrintStream(consoleout);

		try {
			final String js = IOUtils.toString(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("autoinput", "js/default.js")).getInputStream(), Charsets.UTF_8);
			this.javascript.put("jsmanager", this);
			this.javascript.eval(js);
			this.rootBindings = this.javascript.getBindings(ScriptContext.ENGINE_SCOPE);
		} catch (IOException|ScriptException e) {
			e.printStackTrace();
		}
		update.addActionListener(e -> {
			consolearea.setText("");
			try {
				onDispose();
				final Bindings bindings = new SimpleBindings(Maps.newHashMap(this.rootBindings));
				this.javascript.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
				this.javascript.eval(tarea.getText());
				update.setText("Update ✓");
				this.console.println("Script Successfully Updated");
			} catch (final ScriptException e1) {
				update.setText("Update ✘");
				this.console.println("Invalid Script");
				e1.printStackTrace(this.console);
			} catch (final Exception e1) {
				e1.printStackTrace(this.console);
			}
		});
		splitPane.add(new JScrollPane(tarea), JSplitPane.LEFT);
		splitPane.add(new JScrollPane(consolearea), JSplitPane.RIGHT);
		panel.add(splitPane, BorderLayout.CENTER);
		buttons.add(update, BorderLayout.CENTER);
		buttons.add(keycode, BorderLayout.EAST);
		panel.add(buttons, BorderLayout.SOUTH);
		this.frame.setSize(800, 800);
		this.frame.add(panel);
	}

	public void keyPress(final int key, final boolean pressed) {
		ClientHandler.INSTANCE.pressQueue.offer(Pair.of(key, !pressed));
	}

	public void keyPress(final int key) {
		KeyBinding.onTick(key);
	}

	public void enableGUI() {
		this.frame.setVisible(true);
	}

	public void onDispose() {
		if (this.javascript instanceof Invocable) {
			final Invocable invocable = (Invocable) this.javascript;
			try {
				invocable.invokeFunction("onDispose");
			} catch (final NoSuchMethodException e) {
			} catch (final Exception e) {
				e.printStackTrace(this.console);
			}
		}
	}

	public void onTick() {
		if (this.javascript instanceof Invocable) {
			final Invocable invocable = (Invocable) this.javascript;
			try {
				invocable.invokeFunction("onTick");
			} catch (final NoSuchMethodException e) {
			} catch (final Exception e) {
				e.printStackTrace(this.console);
			}
		}
	}

	public void onToggle(final boolean keyInput) {
		if (this.javascript instanceof Invocable) {
			final Invocable invocable = (Invocable) this.javascript;
			try {
				invocable.invokeFunction(keyInput ? "onEnable" : "onDisable");
			} catch (final NoSuchMethodException e) {
			} catch (final Exception e) {
				e.printStackTrace(this.console);
			}
		}
	}
}
