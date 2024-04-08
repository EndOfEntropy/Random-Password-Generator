package gui;

public class Notes {
/**
 **Approach:
 * ASCII	decimal value				character
 * [0-9] 	48-57						0123456789
 * [A-Z] 	65-90						ABCDEFGHIJKLMNOPQRSTUVWXYZ
 * [a-z]	97-122						abcdefghijklmnopqrstuvwxyz
 * special	33-47 58-64 91-96 123-126	!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~
 * special characters like " or \ will require a leading \ when between brackets "" in Java
 * 33-126 ASCII values form a contiguous/sequential range
 * Generate a random number between 33-126 (ASCII value) or add 33 to a number generated between 0-93
 * Total number of characters: 26*2 alpha + 10 digits + 32 symbols = 94
 * 
 * 
 **Combination of Strings was selected as it is easier to implement
 * 
 * 
 **Swing Gui - Layouts:
 * Layout manager = Defines the natural layout for the components within a container
 * 
 * BorderLayout = 	A BorderLayout places components in five areas: NORTH, SOUTH, WEST, EAST and CENTER
 * 					All extra space is placed in the center area.
 * 
 * FlowLayout =	places components in a row, sized at their preferred size. If the horizontal space in the container is too small,
 * 				the FlowLayout class uses the next available row.
 * 
 * BoxLayout =	places the components in a stacked manner to put them either vertically or horizontally. 
 * 				It provides flexibility over FlowLayout.
 * 
 * GriLayout = 	places components in a grid of cells. Each component takes all the available space within its cell,
 * 				an each cell is the same size.
 *
 *
 **Enhancements to GUI:
 * Create filler panels to create a line break as JLabel "Password" and "Score" are too short.
 * Improve GUI visuals color theme
 * 
 **Enhancements to password scoring:
 * Comp. Score for Repeat Characters (case insensitive)
 * Flat. Sequential letters (3+)
 * Flat. Sequential numbers (3+)
 * Flat. Sequential symbols (3+)
 * 
 * 
 * 
 */
}
