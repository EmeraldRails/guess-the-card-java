package hyperdev.util.card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import hyperdev.GuessTheCard;


public class Card {
	public final class VisualCard extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7039341520373831077L;
		private Image image;
		private int i = 0;
		Card card;
		List<String> matrix = new ArrayList<String>();
		private VisualCard(Card c) {
			card = c;
			char[][] m = c.getValue().getMatrix();
			boolean lff = false;
			for(char[] ca : m) {
				matrix.add("");
				for(char cx : ca) {
					if(!lff) {
						String s = "";
						if(cx == '%')
							s = c.getSuit().getVis();
						if(cx == '\0')
							s = " ";
						if(cx == '\\') {
							int l = matrix.size() -1;
							matrix.set(l, matrix.get(l) + " ");
							lff = true;
							continue;
						}
						if(s == "")
							s = cx + "";
						int l = matrix.size() -1;
						matrix.set(l, matrix.get(l) + s);
						
					} else {
						if(i == 0) {
							i = Integer.parseInt(cx + "", 10);
						} else {
							i = (i * 10) + Integer.parseInt(cx + "", 10);
							lff = false;
						}
						int l = matrix.size() -1;
						matrix.set(l, matrix.get(l) + " ");
					}
				}
			}
			try {
				image = ImageIO.read(new File(GuessTheCard.dataDir, "base.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setPreferredSize(new Dimension(215, 350));
		}
		@Override
		protected void paintComponent(Graphics gs) {
			Graphics2D  g = (Graphics2D) gs;
			g.drawImage(image, 0, 0, null);
			g.setColor(card.getSuit().getColor());
			g.setFont(new Font(Font.SANS_SERIF, 1, 16));
			g.drawString(card.getRawValue() + card.getSuit().getVis(), 7, 20);
			g.drawString(card.getRawValue() + card.getSuit().getVis(), 190, 340);
			if(i == 0)
				i = 16;
			g.setFont(new Font(Font.MONOSPACED, 0, i));
			g.setColor(card.getSuit().getColor());
			int y = 150;
			for(String s : matrix) 
				g.drawString(s, 100, y += g.getFontMetrics().getHeight());
		}

	}

	public enum Value {
		TWO("2", 0),
		THREE("3", 1),
		FOUR("4", 2),
		FIVE("5", 3),
		SIX("6", 4),
		SEVEN("7", 5),
		EIGHT("8", 6),
		NINE("9", 7),
		TEN("10", 8),
		JACK("J", 9),
		QUEEN("Q", 10),
		KING("K", 11),
		ACE("A", 12);
		private String val;
		private char[][] matrix;
		private Value(String value, int index) {
			val = value;
			matrix = valueMatrix[index];
		}
		public String getValue() {
			return val;
		}
		public char[][] getMatrix() {
			return matrix;
		}
		public final char[][][] valueMatrix = new char[][][] {
			{
				{
					'\0', '\0', '\0'
				},
				{
					'\0', '%', '\0'
				},
				{
					'\0', '\0', '%'
				}
			},
			{
				{
					'%', '\0', '\0'
				},
				{
					'\0', '%', '\0'
				},
				{
					'\0', '\0', '%'
				}
			},
			{
				{
					'%', '\0', '%'
				},
				{
					'\0', '\0', '\0'
				},
				{
					'%', '\0', '%'
				}
			},
			{
				{
					'%', '\0', '%'
				},
				{
					'\0', '%', '\0'
				},
				{
					'%', '\0', '%'
				}
			},
			{
				{
					'%', '\0', '%'
				},
				{
					'%', '\0', '%'
				},
				{
					'%', '\0', '%'
				}
			},
			{
				{
					'%', '\0', '%'
				},
				{
					'%', '%', '%'
				}, 
				{
					'%', '\0', '%'
				}
			},
			{
				{
					'%', '%', '%'
				},
				{
					'%', '\0', '%'
				},
				{
					'%', '%', '%'
				}
			},
			{
				{
					'%', '%', '%'
				},
				{
					'%', '%', '%'
				},
				{
					'%', '%', '%'
				}
			},
			{
				{
					'\0', '%', '\0'
				},
				{
					'%', '%', '%'
				},
				{
					'%', '%', '%'
				},
				{
					'%', '%', '%'
				}
			},
			{

				{
					'J'
				},
				{
					'\\', '3', '6'
				} 
				
			},
			{

				{
					'Q'
				},				
				{
					'\\', '3', '6'
				}
			},
			{
				{
					'K'
				},
				{
					'\\', '3', '6'
				},
			},
			{
				{
					'%'
				},
				{
					'\\', '3', '6'
				}
			}
		};
	}
	public enum Suit {
		SPADES("spades", "♠", false),
		HEARTS("hearts", "♥", true),
		CLUBS("clubs", "♣", false),
		DIAMONDS("diamonds", "♦", true);
		private String id;
		private String vis;
		private Color color;
		private Suit(String identifier, String visual, boolean red) {
			id = identifier;
			vis = visual;
			if(red)
				color = Color.red;
			else
				color = Color.black;
		}
		public Color getColor() {
			
			return color;
		}
		public String getID() {
			return id;
		}
		public String getVis() {
			return vis;
		}
	}

	/**
	 * An {@link hyperdev.util.card.Card.Value Value} that the card is.
	 */
	protected transient Value value;
	/**
	 * An {@link hyperdev.util.card.Card.Suit Suit} that the card is.
	 */
	protected transient Suit suit;
	
	public Card(Suit s, Value v) {
		value = v;
		suit = s;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Value getValue() {
		return value;
	}
	public String getRawSuit() {
		return suit.getID();
	}
	public String getRawValue() {
		return value.getValue();
	}
	public static Card generateCard() {
		Random random = new Random();
		Value val = Value.values()[random.nextInt(13)];
		Suit suit = Suit.values()[random.nextInt(4)];
		return new Card(suit, val);
	}

	public VisualCard draw() {
		return new VisualCard(this);
		
	}
	
}
